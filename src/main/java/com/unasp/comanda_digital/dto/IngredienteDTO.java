package com.unasp.comanda_digital.dto;

import com.unasp.comanda_digital.model.Ingrediente;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class IngredienteDTO {
    private Long id;
    private String nome;
    private String sku;
    private Ingrediente.Unidade unidadePadrao;
    private BigDecimal estoqueMinimo;
    private BigDecimal custoUnitario;
    private Ingrediente.Status status;

    public static IngredienteDTO fromEntity(Ingrediente ingrediente) {
        IngredienteDTO dto = new IngredienteDTO();
        dto.setId(ingrediente.getId());
        dto.setNome(ingrediente.getNome());
        dto.setSku(ingrediente.getSku());
        dto.setUnidadePadrao(ingrediente.getUnidadePadrao());
        dto.setEstoqueMinimo(ingrediente.getEstoqueMinimo());
        dto.setCustoUnitario(ingrediente.getCustoUnitario());
        dto.setStatus(ingrediente.getStatus());
        return dto;
    }
}