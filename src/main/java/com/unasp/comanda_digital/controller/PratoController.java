package com.unasp.comanda_digital.controller;

import com.unasp.comanda_digital.dto.PratoDTO;
import com.unasp.comanda_digital.service.PratoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PratoController {

    private final PratoService pratoService;

    @GetMapping("/api/cardapio")
    public ResponseEntity<List<PratoDTO>> cardapio(@RequestParam(required = false) Long categoriaId) {
        return ResponseEntity.ok(pratoService.listarCardapio(categoriaId));
    }

    @GetMapping("/api/cardapio/{id}")
    public ResponseEntity<PratoDTO> detalhe(@PathVariable Long id) {
        return ResponseEntity.ok(pratoService.buscarPorId(id));
    }

    @GetMapping("/api/admin/pratos")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Page<PratoDTO>> listarAdmin(Pageable pageable) {
        return ResponseEntity.ok(pratoService.listarAdmin(pageable));
    }

    @PostMapping("/api/admin/pratos")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<PratoDTO> criar(@RequestBody PratoDTO dto) {
        return ResponseEntity.ok(pratoService.criar(dto));
    }

    @PutMapping("/api/admin/pratos/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<PratoDTO> atualizar(@PathVariable Long id, @RequestBody PratoDTO dto) {
        return ResponseEntity.ok(pratoService.atualizar(id, dto));
    }

    @DeleteMapping("/api/admin/pratos/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pratoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}