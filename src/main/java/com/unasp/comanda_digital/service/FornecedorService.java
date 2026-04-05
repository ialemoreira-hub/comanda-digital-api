package com.unasp.comanda_digital.service;

import com.unasp.comanda_digital.dto.FornecedorDTO;
import com.unasp.comanda_digital.model.Fornecedor;
import com.unasp.comanda_digital.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public List<FornecedorDTO> listarTodos() {
        return fornecedorRepository.findAll()
                .stream()
                .map(FornecedorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public FornecedorDTO buscarPorId(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado!"));
        return FornecedorDTO.fromEntity(fornecedor);
    }

    public FornecedorDTO criar(FornecedorDTO dto) {
        if (fornecedorRepository.existsByCnpj(dto.getCnpj())) {
            throw new RuntimeException("CNPJ já cadastrado!");
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setRazaoSocial(dto.getRazaoSocial());
        fornecedor.setCnpj(dto.getCnpj());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setStatus(Fornecedor.Status.ATIVO);

        return FornecedorDTO.fromEntity(fornecedorRepository.save(fornecedor));
    }

    public FornecedorDTO atualizar(Long id, FornecedorDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado!"));

        fornecedor.setRazaoSocial(dto.getRazaoSocial());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setEmail(dto.getEmail());
        if (dto.getStatus() != null) fornecedor.setStatus(dto.getStatus());

        return FornecedorDTO.fromEntity(fornecedorRepository.save(fornecedor));
    }

    public void deletar(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado!"));
        fornecedor.setStatus(Fornecedor.Status.INATIVO);
        fornecedorRepository.save(fornecedor);
    }
}