package com.unasp.comanda_digital.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequest {
    private String enderecoEntrega;
    private String observacoes;
    private List<PedidoItemRequest> itens;
}