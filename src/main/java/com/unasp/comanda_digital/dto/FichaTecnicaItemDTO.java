package com.unasp.comanda_digital.dto;

import com.unasp.comanda_digital.model.FichaTecnicaItem;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class FichaTecnicaItemDTO {
    private Long id;
    private Long ingredienteId;
    private String ingredienteNome;
    private BigDecimal quantidade;
    private String unidade;
    private BigDecimal fatorCorrecao;
    private BigDecimal custoUnitario;
    private BigDecimal custoTotal;

    public static FichaTecnicaItemDTO fromEntity(FichaTecnicaItem item) {
        FichaTecnicaItemDTO dto = new FichaTecnicaItemDTO();
        dto.setId(item.getId());
        dto.setIngredienteId(item.getIngrediente().getId());
        dto.setIngredienteNome(item.getIngrediente().getNome());
        dto.setQuantidade(item.getQuantidade());
        dto.setUnidade(item.getUnidade());
        dto.setFatorCorrecao(item.getFatorCorrecao());
        dto.setCustoUnitario(item.getIngrediente().getCustoUnitario());

        // custo total = quantidade x fator_correcao x custo_unitario
        BigDecimal custoTotal = item.getQuantidade()
                .multiply(item.getFatorCorrecao())
                .multiply(item.getIngrediente().getCustoUnitario());
        dto.setCustoTotal(custoTotal);

        return dto;
    }
}
