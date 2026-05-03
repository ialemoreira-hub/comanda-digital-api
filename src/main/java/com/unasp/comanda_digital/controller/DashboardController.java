package com.unasp.comanda_digital.controller;

import com.unasp.comanda_digital.repository.*;
import com.unasp.comanda_digital.model.Pedido.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
public class DashboardController {

    private final PedidoRepository pedidoRepository;
    private final IngredienteRepository ingredienteRepository;
    private final EstoqueMovimentacaoRepository estoqueRepository;

    @GetMapping("/resumo")
    public Map<String, Object> resumo() {
        var hoje = LocalDate.now();
        var inicioDia = hoje.atStartOfDay();
        var fimDia = hoje.plusDays(1).atStartOfDay();

        var pedidosHoje = pedidoRepository.findAll().stream()
                .filter(p -> p.getCreatedAt() != null &&
                        !p.getCreatedAt().isBefore(inicioDia) &&
                        p.getCreatedAt().isBefore(fimDia) &&
                        p.getStatus() != StatusPedido.CANCELADO)
                .collect(Collectors.toList());

        BigDecimal faturamento = pedidosHoje.stream()
                .map(p -> p.getValorTotal() != null ? p.getValorTotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalPedidos = pedidosHoje.size();

        BigDecimal ticketMedio = totalPedidos > 0
                ? faturamento.divide(BigDecimal.valueOf(totalPedidos), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        Map<String, Object> result = new HashMap<>();
        result.put("faturamentoDia", faturamento);
        result.put("totalPedidos", totalPedidos);
        result.put("ticketMedio", ticketMedio);
        return result;
    }

    @GetMapping("/top-pratos")
    public List<Map<String, Object>> topPratos() {
        var pedidos = pedidoRepository.findAll().stream()
                .filter(p -> p.getStatus() != StatusPedido.CANCELADO)
                .collect(Collectors.toList());

        Map<String, Integer> contagem = new HashMap<>();
        for (var pedido : pedidos) {
            if (pedido.getItens() != null) {
                for (var item : pedido.getItens()) {
                    String nome = item.getPrato().getNome();
                    contagem.merge(nome, item.getQuantidade(), Integer::sum);
                }
            }
        }

        return contagem.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(e -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("nome", e.getKey());
                    m.put("quantidade", e.getValue());
                    return m;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/alertas-estoque")
    public List<Map<String, Object>> alertasEstoque() {
        return ingredienteRepository.findAll().stream()
                .filter(i -> {
                    BigDecimal saldo = estoqueRepository.calcularSaldo(i);
                    return saldo.compareTo(i.getEstoqueMinimo()) < 0;
                })
                .map(i -> {
                    BigDecimal saldo = estoqueRepository.calcularSaldo(i);
                    Map<String, Object> m = new HashMap<>();
                    m.put("nome", i.getNome());
                    m.put("estoque", saldo);
                    m.put("minimo", i.getEstoqueMinimo());
                    m.put("unidade", i.getUnidadePadrao());
                    return m;
                })
                .collect(Collectors.toList());
    }
}