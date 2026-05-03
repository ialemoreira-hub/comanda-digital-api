package com.unasp.comanda_digital.dto;

import lombok.Data;

@Data
public class PedidoItemRequest {
    private Long pratoId;
    private Integer quantidade;
    private String observacoes;
}