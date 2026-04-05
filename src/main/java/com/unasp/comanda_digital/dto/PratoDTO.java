package com.unasp.comanda_digital.dto;

import com.unasp.comanda_digital.model.Prato;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PratoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String fotoUrl;
    private BigDecimal precoVenda;
    private Integer tempoPreparo;
    private Prato.Status status;
    private Long categoriaId;
    private String categoriaNome;

    public static PratoDTO fromEntity(Prato prato) {
        PratoDTO dto = new PratoDTO();
        dto.setId(prato.getId());
        dto.setNome(prato.getNome());
        dto.setDescricao(prato.getDescricao());
        dto.setFotoUrl(prato.getFotoUrl());
        dto.setPrecoVenda(prato.getPrecoVenda());
        dto.setTempoPreparo(prato.getTempoPreparo());
        dto.setStatus(prato.getStatus());
        if (prato.getCategoria() != null) {
            dto.setCategoriaId(prato.getCategoria().getId());
            dto.setCategoriaNome(prato.getCategoria().getNome());
        }
        return dto;
    }
}
