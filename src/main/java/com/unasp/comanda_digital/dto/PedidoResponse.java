package com.unasp.comanda_digital.dto;

import com.unasp.comanda_digital.model.Pedido.StatusPedido;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoResponse {
    private Long id;
    private String clienteNome;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private String enderecoEntrega;
    private String observacoes;
    private String motivoCancelamento;
    private List<PedidoItemResponse> itens;
    private LocalDateTime createdAt;

    @Data
    public static class PedidoItemResponse {
        private Long id;
        private String pratoNome;
        private Integer quantidade;
        private BigDecimal precoUnitario;
        private BigDecimal subtotal;
        private String observacoes;
    }
}