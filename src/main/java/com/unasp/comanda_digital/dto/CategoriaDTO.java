package com.unasp.comanda_digital.dto;

import com.unasp.comanda_digital.model.Categoria;
import lombok.Data;

@Data
public class CategoriaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Integer ordem;
    private Categoria.Status status;

    public static CategoriaDTO fromEntity(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        dto.setDescricao(categoria.getDescricao());
        dto.setOrdem(categoria.getOrdem());
        dto.setStatus(categoria.getStatus());
        return dto;
    }
}