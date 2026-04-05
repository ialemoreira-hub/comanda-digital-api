package com.unasp.comanda_digital.service;

import com.unasp.comanda_digital.dto.CategoriaDTO;
import com.unasp.comanda_digital.model.Categoria;
import com.unasp.comanda_digital.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CategoriaDTO> listarAtivas() {
        return categoriaRepository.findByStatusOrderByOrdem(Categoria.Status.ATIVO)
                .stream()
                .map(CategoriaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
        return CategoriaDTO.fromEntity(categoria);
    }

    public CategoriaDTO criar(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        categoria.setOrdem(dto.getOrdem());
        categoria.setStatus(dto.getStatus() != null ? dto.getStatus() : Categoria.Status.ATIVO);
        return CategoriaDTO.fromEntity(categoriaRepository.save(categoria));
    }

    public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        categoria.setOrdem(dto.getOrdem());
        if (dto.getStatus() != null) categoria.setStatus(dto.getStatus());
        return CategoriaDTO.fromEntity(categoriaRepository.save(categoria));
    }

    public void deletar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
        categoria.setStatus(Categoria.Status.INATIVO);
        categoriaRepository.save(categoria);
    }
}