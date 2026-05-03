package com.unasp.comanda_digital.controller;

import com.unasp.comanda_digital.model.EstoqueMovimentacao;
import com.unasp.comanda_digital.model.EstoqueMovimentacao.MotivoMovimentacao;
import com.unasp.comanda_digital.model.EstoqueMovimentacao.TipoMovimentacao;
import com.unasp.comanda_digital.repository.EstoqueMovimentacaoRepository;
import com.unasp.comanda_digital.repository.IngredienteRepository;
import com.unasp.comanda_digital.repository.UsuarioRepository;
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
@RequestMapping("/api/admin/estoque")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
public class EstoqueController {

    private final IngredienteRepository ingredienteRepository;
    private final EstoqueMovimentacaoRepository estoqueRepository;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/saldo")
    public List<Map<String, Object>> saldo() {
        return ingredienteRepository.findAll().stream().map(i -> {
            BigDecimal saldo = estoqueRepository.calcularSaldo(i);
            Map<String, Object> m = new HashMap<>();
            m.put("id", i.getId());
            m.put("nome", i.getNome());
            m.put("saldo", saldo);
            m.put("estoqueMinimo", i.getEstoqueMinimo());
            m.put("unidade", i.getUnidadePadrao());
            m.put("critico", saldo.compareTo(i.getEstoqueMinimo()) < 0);
            return m;
        }).collect(Collectors.toList());
    }

    @GetMapping("/alertas")
    public List<Map<String, Object>> alertas() {
        return ingredienteRepository.findAll().stream()
                .filter(i -> estoqueRepository.calcularSaldo(i).compareTo(i.getEstoqueMinimo()) < 0)
                .map(i -> {
                    BigDecimal saldo = estoqueRepository.calcularSaldo(i);
                    Map<String, Object> m = new HashMap<>();
                    m.put("nome", i.getNome());
                    m.put("saldo", saldo);
                    m.put("estoqueMinimo", i.getEstoqueMinimo());
                    m.put("unidade", i.getUnidadePadrao());
                    return m;
                }).collect(Collectors.toList());
    }

    @PostMapping("/movimentacao")
    public Map<String, Object> registrarSaida(
            @RequestBody Map<String, Object> body,
            Authentication authentication) {

        Long ingredienteId = Long.valueOf(body.get("ingredienteId").toString());
        BigDecimal quantidade = new BigDecimal(body.get("quantidade").toString());
        String motivo = body.get("motivo").toString();

        var ingrediente = ingredienteRepository.findById(ingredienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente não encontrado"));

        var usuario = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        EstoqueMovimentacao mov = new EstoqueMovimentacao();
        mov.setIngrediente(ingrediente);
        mov.setTipo(TipoMovimentacao.SAIDA);
        mov.setMotivo(MotivoMovimentacao.valueOf(motivo));
        mov.setQuantidade(quantidade);
        mov.setUsuario(usuario);

        estoqueRepository.save(mov);

        Map<String, Object> result = new HashMap<>();
        result.put("mensagem", "Movimentação registrada com sucesso!");
        result.put("novoSaldo", estoqueRepository.calcularSaldo(ingrediente));
        return result;
    }

    @GetMapping("/movimentacoes")
    public List<Map<String, Object>> movimentacoes() {
        return estoqueRepository.findAll().stream()
                .sorted(Comparator.comparing(EstoqueMovimentacao::getCreatedAt).reversed())
                .map(m -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", m.getId());
                    map.put("ingrediente", m.getIngrediente().getNome());
                    map.put("tipo", m.getTipo());
                    map.put("quantidade", m.getQuantidade());
                    map.put("motivo", m.getMotivo());
                    map.put("data", m.getCreatedAt());
                    map.put("usuario", m.getUsuario() != null ? m.getUsuario().getNome() : "-");
                    return map;
                }).collect(Collectors.toList());
    }
}