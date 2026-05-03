package com.unasp.comanda_digital.controller;

import com.unasp.comanda_digital.model.*;
import com.unasp.comanda_digital.model.PedidoCompra.StatusCompra;
import com.unasp.comanda_digital.model.EstoqueMovimentacao.TipoMovimentacao;
import com.unasp.comanda_digital.model.EstoqueMovimentacao.MotivoMovimentacao;
import com.unasp.comanda_digital.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/compras")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
public class CompraController {

    private final PedidoCompraRepository compraRepository;
    private final FornecedorRepository fornecedorRepository;
    private final IngredienteRepository ingredienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstoqueMovimentacaoRepository estoqueRepository;

    @GetMapping
    public List<Map<String, Object>> listar() {
        return compraRepository.findAll().stream()
                .sorted(Comparator.comparing(PedidoCompra::getCreatedAt).reversed())
                .map(this::toMap)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Map<String, Object> buscar(@PathVariable Long id) {
        return toMap(compraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra não encontrada")));
    }

    @PostMapping
    public Map<String, Object> criar(@RequestBody Map<String, Object> body, Authentication auth) {
        var fornecedor = fornecedorRepository.findById(Long.valueOf(body.get("fornecedorId").toString()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));

        var usuario = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        PedidoCompra compra = new PedidoCompra();
        compra.setFornecedor(fornecedor);
        compra.setUsuario(usuario);
        compra.setStatus(StatusCompra.RASCUNHO);

        List<Map<String, Object>> itensBody = (List<Map<String, Object>>) body.get("itens");
        List<PedidoCompraItem> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (var itemBody : itensBody) {
            var ingrediente = ingredienteRepository.findById(Long.valueOf(itemBody.get("ingredienteId").toString()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

            BigDecimal qtd = new BigDecimal(itemBody.get("quantidade").toString());
            BigDecimal preco = new BigDecimal(itemBody.get("precoUnitario").toString());
            BigDecimal subtotal = qtd.multiply(preco);

            PedidoCompraItem item = new PedidoCompraItem();
            item.setPedidoCompra(compra);
            item.setIngrediente(ingrediente);
            item.setQuantidade(qtd);
            item.setPrecoUnitario(preco);
            item.setSubtotal(subtotal);
            itens.add(item);
            total = total.add(subtotal);
        }

        compra.setItens(itens);
        compra.setValorTotal(total);
        return toMap(compraRepository.save(compra));
    }

    @PatchMapping("/{id}/receber")
    public Map<String, Object> receber(@PathVariable Long id, Authentication auth) {
        PedidoCompra compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra não encontrada"));

        if (compra.getStatus() != StatusCompra.ENVIADO && compra.getStatus() != StatusCompra.RASCUNHO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compra não pode ser recebida neste status");
        }

        var usuario = usuarioRepository.findByEmail(auth.getName()).get();

        for (PedidoCompraItem item : compra.getItens()) {
            EstoqueMovimentacao mov = new EstoqueMovimentacao();
            mov.setIngrediente(item.getIngrediente());
            mov.setTipo(TipoMovimentacao.ENTRADA);
            mov.setMotivo(MotivoMovimentacao.COMPRA);
            mov.setQuantidade(item.getQuantidade());
            mov.setCustoUnitario(item.getPrecoUnitario());
            mov.setUsuario(usuario);
            estoqueRepository.save(mov);

            // Atualiza custo do ingrediente
            item.getIngrediente().setCustoUnitario(item.getPrecoUnitario());
            ingredienteRepository.save(item.getIngrediente());
        }

        compra.setStatus(StatusCompra.RECEBIDO);
        return toMap(compraRepository.save(compra));
    }

    @PatchMapping("/{id}/status")
    public Map<String, Object> atualizarStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        PedidoCompra compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra não encontrada"));
        compra.setStatus(StatusCompra.valueOf(body.get("status")));
        return toMap(compraRepository.save(compra));
    }

    private Map<String, Object> toMap(PedidoCompra compra) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", compra.getId());
        m.put("fornecedorNome", compra.getFornecedor() != null ? compra.getFornecedor().getRazaoSocial() : "-");
        m.put("fornecedorId", compra.getFornecedor() != null ? compra.getFornecedor().getId() : null);
        m.put("status", compra.getStatus());
        m.put("valorTotal", compra.getValorTotal());
        m.put("createdAt", compra.getCreatedAt());
        m.put("usuario", compra.getUsuario() != null ? compra.getUsuario().getNome() : "-");
        if (compra.getItens() != null) {
            m.put("itens", compra.getItens().stream().map(item -> {
                Map<String, Object> i = new HashMap<>();
                i.put("id", item.getId());
                i.put("ingredienteNome", item.getIngrediente().getNome());
                i.put("ingredienteId", item.getIngrediente().getId());
                i.put("quantidade", item.getQuantidade());
                i.put("precoUnitario", item.getPrecoUnitario());
                i.put("subtotal", item.getSubtotal());
                return i;
            }).collect(Collectors.toList()));
        }
        return m;
    }
}