package com.unasp.comanda_digital.service;

import com.unasp.comanda_digital.dto.PedidoRequest;
import com.unasp.comanda_digital.dto.PedidoResponse;
import com.unasp.comanda_digital.dto.PedidoResponse.PedidoItemResponse;
import com.unasp.comanda_digital.model.*;
import com.unasp.comanda_digital.model.Pedido.StatusPedido;
import com.unasp.comanda_digital.model.EstoqueMovimentacao.TipoMovimentacao;
import com.unasp.comanda_digital.model.EstoqueMovimentacao.MotivoMovimentacao;
import com.unasp.comanda_digital.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PratoRepository pratoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final IngredienteRepository ingredienteRepository;
    private final EstoqueMovimentacaoRepository estoqueRepository;

    @Transactional
    public PedidoResponse criarPedido(PedidoRequest request, String emailCliente) {
        Usuario cliente = usuarioRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        // Valida estoque antes de criar
        for (var item : request.getItens()) {
            Prato prato = pratoRepository.findById(item.getPratoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prato não encontrado"));

            fichaTecnicaRepository.findByPratoId(prato.getId()).ifPresent(ficha -> {
                for (FichaTecnicaItem ftItem : ficha.getItens()) {
                    BigDecimal saldo = estoqueRepository.calcularSaldo(ftItem.getIngrediente());
                    BigDecimal necessario = ftItem.getQuantidade()
                            .multiply(BigDecimal.valueOf(item.getQuantidade()));
                    if (saldo.compareTo(necessario) < 0) {
                        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                                "Estoque insuficiente: " + ftItem.getIngrediente().getNome());
                    }
                }
            });
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEnderecoEntrega(request.getEnderecoEntrega());
        pedido.setObservacoes(request.getObservacoes());
        pedido.setStatus(StatusPedido.RECEBIDO);

        List<PedidoItem> itens = request.getItens().stream().map(itemReq -> {
            Prato prato = pratoRepository.findById(itemReq.getPratoId()).get();
            PedidoItem item = new PedidoItem();
            item.setPedido(pedido);
            item.setPrato(prato);
            item.setQuantidade(itemReq.getQuantidade());
            item.setPrecoUnitario(prato.getPrecoVenda());
            item.setObservacoes(itemReq.getObservacoes());
            return item;
        }).collect(Collectors.toList());

        pedido.setItens(itens);
        pedido.setValorTotal(itens.stream()
                .map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return toResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponse atualizarStatus(Long id, StatusPedido novoStatus, String emailUsuario) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        // Baixa estoque ao confirmar
        if (novoStatus == StatusPedido.CONFIRMADO) {
            Usuario usuario = usuarioRepository.findByEmail(emailUsuario).get();
            baixarEstoque(pedido, usuario);
        }

        pedido.setStatus(novoStatus);
        pedido.setUpdatedAt(LocalDateTime.now());
        return toResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponse cancelarPedido(Long id, String motivo, String emailUsuario) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        if (pedido.getStatus() == StatusPedido.CANCELADO || pedido.getStatus() == StatusPedido.FINALIZADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não pode ser cancelado");
        }

        // Estorna estoque se já foi confirmado
        if (pedido.getStatus() != StatusPedido.RECEBIDO) {
            Usuario usuario = usuarioRepository.findByEmail(emailUsuario).get();
            estornarEstoque(pedido, usuario);
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setMotivoCancelamento(motivo);
        pedido.setUpdatedAt(LocalDateTime.now());
        return toResponse(pedidoRepository.save(pedido));
    }

    public List<PedidoResponse> listarPedidosDoCliente(String emailCliente) {
        Usuario cliente = usuarioRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        return pedidoRepository.findByClienteId(cliente.getId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<PedidoResponse> listarTodos() {
        return pedidoRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public PedidoResponse buscarPorId(Long id) {
        return toResponse(pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado")));
    }

    private void baixarEstoque(Pedido pedido, Usuario usuario) {
        for (PedidoItem item : pedido.getItens()) {
            fichaTecnicaRepository.findByPratoId(item.getPrato().getId()).ifPresent(ficha -> {
                for (FichaTecnicaItem ftItem : ficha.getItens()) {
                    EstoqueMovimentacao mov = new EstoqueMovimentacao();
                    mov.setIngrediente(ftItem.getIngrediente());
                    mov.setTipo(TipoMovimentacao.SAIDA);
                    mov.setMotivo(MotivoMovimentacao.VENDA);
                    mov.setQuantidade(ftItem.getQuantidade()
                            .multiply(BigDecimal.valueOf(item.getQuantidade())));
                    mov.setPedido(pedido);
                    mov.setUsuario(usuario);
                    estoqueRepository.save(mov);
                }
            });
        }
    }

    private void estornarEstoque(Pedido pedido, Usuario usuario) {
        for (PedidoItem item : pedido.getItens()) {
            fichaTecnicaRepository.findByPratoId(item.getPrato().getId()).ifPresent(ficha -> {
                for (FichaTecnicaItem ftItem : ficha.getItens()) {
                    EstoqueMovimentacao mov = new EstoqueMovimentacao();
                    mov.setIngrediente(ftItem.getIngrediente());
                    mov.setTipo(TipoMovimentacao.ESTORNO);
                    mov.setMotivo(MotivoMovimentacao.ESTORNO);
                    mov.setQuantidade(ftItem.getQuantidade()
                            .multiply(BigDecimal.valueOf(item.getQuantidade())));
                    mov.setPedido(pedido);
                    mov.setUsuario(usuario);
                    estoqueRepository.save(mov);
                }
            });
        }
    }

    private PedidoResponse toResponse(Pedido pedido) {
        PedidoResponse response = new PedidoResponse();
        response.setId(pedido.getId());
        response.setClienteNome(pedido.getCliente().getNome());
        response.setStatus(pedido.getStatus());
        response.setValorTotal(pedido.getValorTotal());
        response.setEnderecoEntrega(pedido.getEnderecoEntrega());
        response.setObservacoes(pedido.getObservacoes());
        response.setMotivoCancelamento(pedido.getMotivoCancelamento());
        response.setCreatedAt(pedido.getCreatedAt());

        if (pedido.getItens() != null) {
            response.setItens(pedido.getItens().stream().map(item -> {
                PedidoItemResponse itemResp = new PedidoItemResponse();
                itemResp.setId(item.getId());
                itemResp.setPratoNome(item.getPrato().getNome());
                itemResp.setQuantidade(item.getQuantidade());
                itemResp.setPrecoUnitario(item.getPrecoUnitario());
                itemResp.setSubtotal(item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())));
                itemResp.setObservacoes(item.getObservacoes());
                return itemResp;
            }).collect(Collectors.toList()));
        }

        return response;
    }
}