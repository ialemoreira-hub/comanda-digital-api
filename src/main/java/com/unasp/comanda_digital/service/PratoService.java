package com.unasp.comanda_digital.service;

import com.unasp.comanda_digital.dto.PratoDTO;
import com.unasp.comanda_digital.model.Categoria;
import com.unasp.comanda_digital.model.Prato;
import com.unasp.comanda_digital.repository.CategoriaRepository;
import com.unasp.comanda_digital.repository.PratoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PratoService {

    private final PratoRepository pratoRepository;
    private final CategoriaRepository categoriaRepository;

    public List<PratoDTO> listarCardapio(Long categoriaId) {
        if (categoriaId != null) {
            return pratoRepository.findByCategoriaIdAndStatus(categoriaId, Prato.Status.ATIVO)
                    .stream().map(PratoDTO::fromEntity).collect(Collectors.toList());
        }
        return pratoRepository.findByStatus(Prato.Status.ATIVO)
                .stream().map(PratoDTO::fromEntity).collect(Collectors.toList());
    }

    public Page<PratoDTO> listarAdmin(Pageable pageable) {
        return pratoRepository.findAll(pageable).map(PratoDTO::fromEntity);
    }

    public PratoDTO buscarPorId(Long id) {
        Prato prato = pratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prato não encontrado!"));
        return PratoDTO.fromEntity(prato);
    }

    public PratoDTO criar(PratoDTO dto) {
        Prato prato = new Prato();
        prato.setNome(dto.getNome());
        prato.setDescricao(dto.getDescricao());
        prato.setFotoUrl(dto.getFotoUrl());
        prato.setPrecoVenda(dto.getPrecoVenda());
        prato.setTempoPreparo(dto.getTempoPreparo());
        prato.setStatus(dto.getStatus() != null ? dto.getStatus() : Prato.Status.ATIVO);

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
            prato.setCategoria(categoria);
        }

        return PratoDTO.fromEntity(pratoRepository.save(prato));
    }

    public PratoDTO atualizar(Long id, PratoDTO dto) {
        Prato prato = pratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prato não encontrado!"));
        prato.setNome(dto.getNome());
        prato.setDescricao(dto.getDescricao());
        prato.setFotoUrl(dto.getFotoUrl());
        prato.setPrecoVenda(dto.getPrecoVenda());
        prato.setTempoPreparo(dto.getTempoPreparo());
        if (dto.getStatus() != null) prato.setStatus(dto.getStatus());

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
            prato.setCategoria(categoria);
        }

        return PratoDTO.fromEntity(pratoRepository.save(prato));
    }

    public void deletar(Long id) {
        Prato prato = pratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prato não encontrado!"));
        prato.setStatus(Prato.Status.INATIVO);
        pratoRepository.save(prato);
    }
}