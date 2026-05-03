package com.unasp.comanda_digital.controller;

import com.unasp.comanda_digital.dto.PedidoRequest;
import com.unasp.comanda_digital.dto.PedidoResponse;
import com.unasp.comanda_digital.model.Pedido;
import com.unasp.comanda_digital.model.Pedido.StatusPedido;
import com.unasp.comanda_digital.repository.PedidoRepository;
import com.unasp.comanda_digital.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    @PostMapping("/api/pedidos")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN','GERENTE')")
    public ResponseEntity<PedidoResponse> criarPedido(
            @RequestBody PedidoRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(pedidoService.criarPedido(request, authentication.getName()));
    }

    @GetMapping("/api/pedidos/meus")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN','GERENTE')")
    public ResponseEntity<List<PedidoResponse>> meusPedidos(Authentication authentication) {
        return ResponseEntity.ok(pedidoService.listarPedidosDoCliente(authentication.getName()));
    }

    @GetMapping("/api/pedidos/{id}/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PedidoResponse> statusPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @GetMapping("/api/admin/pedidos")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','COZINHEIRO')")
    public ResponseEntity<List<PedidoResponse>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @PatchMapping("/api/admin/pedidos/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','COZINHEIRO')")
    public ResponseEntity<PedidoResponse> atualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            Authentication authentication) {
        StatusPedido novoStatus = StatusPedido.valueOf(body.get("status"));
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, novoStatus, authentication.getName()));
    }

    @PatchMapping("/api/admin/pedidos/{id}/cancelar")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<PedidoResponse> cancelarPedido(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            Authentication authentication) {
        return ResponseEntity.ok(pedidoService.cancelarPedido(id, body.get("motivo"), authentication.getName()));
    }

    @PatchMapping("/api/pedidos/{id}/confirmar-recebimento")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN','GERENTE')")
    public ResponseEntity<PedidoResponse> confirmarRecebimento(
            @PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, StatusPedido.FINALIZADO, authentication.getName()));
    }

    @PatchMapping("/api/pedidos/{id}/feedback")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN','GERENTE')")
    public ResponseEntity<PedidoResponse> feedback(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            Authentication authentication) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        String feedback = body.get("feedback");
        String obsAtual = pedido.getObservacoes() != null ? pedido.getObservacoes() : "";
        pedido.setObservacoes(obsAtual + " | " + feedback);
        pedidoRepository.save(pedido);

        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }
}