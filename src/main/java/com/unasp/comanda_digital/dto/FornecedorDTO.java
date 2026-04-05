package com.unasp.comanda_digital.dto;

import com.unasp.comanda_digital.model.Fornecedor;
import lombok.Data;

@Data
public class FornecedorDTO {
    private Long id;
    private String razaoSocial;
    private String cnpj;
    private String telefone;
    private String email;
    private Fornecedor.Status status;

    public static FornecedorDTO fromEntity(Fornecedor fornecedor) {
        FornecedorDTO dto = new FornecedorDTO();
        dto.setId(fornecedor.getId());
        dto.setRazaoSocial(fornecedor.getRazaoSocial());
        dto.setCnpj(fornecedor.getCnpj());
        dto.setTelefone(fornecedor.getTelefone());
        dto.setEmail(fornecedor.getEmail());
        dto.setStatus(fornecedor.getStatus());
        return dto;
    }
}