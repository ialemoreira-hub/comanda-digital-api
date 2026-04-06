package com.unasp.comanda_digital.controller;

import com.unasp.comanda_digital.dto.FichaTecnicaDTO;
import com.unasp.comanda_digital.service.FichaTecnicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/pratos/{pratoId}/ficha")
@RequiredArgsConstructor
public class FichaTecnicaController {

    private final FichaTecnicaService fichaTecnicaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<FichaTecnicaDTO> buscar(@PathVariable Long pratoId) {
        return ResponseEntity.ok(fichaTecnicaService.buscarPorPrato(pratoId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<FichaTecnicaDTO> salvar(
            @PathVariable Long pratoId,
            @RequestBody FichaTecnicaDTO dto) {
        return ResponseEntity.ok(fichaTecnicaService.salvar(pratoId, dto));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<FichaTecnicaDTO> atualizar(
            @PathVariable Long pratoId,
            @RequestBody FichaTecnicaDTO dto) {
        return ResponseEntity.ok(fichaTecnicaService.salvar(pratoId, dto));
    }
}