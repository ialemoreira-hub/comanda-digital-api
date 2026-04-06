package com.unasp.comanda_digital.service;

import com.unasp.comanda_digital.dto.IngredienteDTO;
import com.unasp.comanda_digital.model.Ingrediente;
import com.unasp.comanda_digital.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    public List<IngredienteDTO> listarTodos() {
        return ingredienteRepository.findAll()
                .stream()
                .map(IngredienteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public IngredienteDTO buscarPorId(Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado!"));
        return IngredienteDTO.fromEntity(ingrediente);
    }

    public IngredienteDTO criar(IngredienteDTO dto) {
        if (dto.getSku() != null && ingredienteRepository.existsBySku(dto.getSku())) {
            throw new RuntimeException("SKU já cadastrado!");
        }

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome(dto.getNome());
        ingrediente.setSku(dto.getSku());
        ingrediente.setUnidadePadrao(dto.getUnidadePadrao());
        ingrediente.setEstoqueMinimo(dto.getEstoqueMinimo());
        ingrediente.setCustoUnitario(dto.getCustoUnitario());
        ingrediente.setStatus(Ingrediente.Status.ATIVO);

        return IngredienteDTO.fromEntity(ingredienteRepository.save(ingrediente));
    }

    public IngredienteDTO atualizar(Long id, IngredienteDTO dto) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado!"));

        ingrediente.setNome(dto.getNome());
        ingrediente.setUnidadePadrao(dto.getUnidadePadrao());
        ingrediente.setEstoqueMinimo(dto.getEstoqueMinimo());
        ingrediente.setCustoUnitario(dto.getCustoUnitario());
        if (dto.getStatus() != null) ingrediente.setStatus(dto.getStatus());

        return IngredienteDTO.fromEntity(ingredienteRepository.save(ingrediente));
    }

    public void deletar(Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado!"));
        ingrediente.setStatus(Ingrediente.Status.INATIVO);
        ingredienteRepository.save(ingrediente);
    }
}