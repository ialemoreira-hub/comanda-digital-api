package com.unasp.comanda_digital.dto;

import com.unasp.comanda_digital.model.FichaTecnica;
import lombok.Data;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FichaTecnicaDTO {
    private Long id;
    private Long pratoId;
    private String pratoNome;
    private Integer rendimento;
    private String modoPreparo;
    private List<FichaTecnicaItemDTO> itens;
    private BigDecimal custoTotal;
    private BigDecimal precoVenda;
    private BigDecimal foodCost;
    private String alertaFoodCost;

    public static FichaTecnicaDTO fromEntity(FichaTecnica ficha) {
        FichaTecnicaDTO dto = new FichaTecnicaDTO();
        dto.setId(ficha.getId());
        dto.setPratoId(ficha.getPrato().getId());
        dto.setPratoNome(ficha.getPrato().getNome());
        dto.setRendimento(ficha.getRendimento());
        dto.setModoPreparo(ficha.getModoPreparo());

        List<FichaTecnicaItemDTO> itens = ficha.getItens().stream()
                .map(FichaTecnicaItemDTO::fromEntity)
                .collect(Collectors.toList());
        dto.setItens(itens);

        // custo total = soma dos custos / rendimento
        BigDecimal somaCustos = itens.stream()
                .map(FichaTecnicaItemDTO::getCustoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal custoTotal = somaCustos.divide(
                new BigDecimal(ficha.getRendimento()), 4, RoundingMode.HALF_UP);
        dto.setCustoTotal(custoTotal);

        // food cost = (custo / preco_venda) x 100
        BigDecimal precoVenda = ficha.getPrato().getPrecoVenda();
        dto.setPrecoVenda(precoVenda);

        if (precoVenda != null && precoVenda.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal foodCost = custoTotal
                    .divide(precoVenda, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100))
                    .setScale(2, RoundingMode.HALF_UP);
            dto.setFoodCost(foodCost);

            // alerta de food cost
            if (foodCost.compareTo(new BigDecimal("30")) <= 0) {
                dto.setAlertaFoodCost("VERDE");
            } else if (foodCost.compareTo(new BigDecimal("35")) <= 0) {
                dto.setAlertaFoodCost("AMARELO");
            } else {
                dto.setAlertaFoodCost("VERMELHO");
            }
        }

        return dto;
    }
}