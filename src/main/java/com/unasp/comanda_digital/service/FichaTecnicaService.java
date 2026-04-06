package com.unasp.comanda_digital.service;

import com.unasp.comanda_digital.dto.FichaTecnicaDTO;
import com.unasp.comanda_digital.dto.FichaTecnicaItemDTO;
import com.unasp.comanda_digital.model.FichaTecnica;
import com.unasp.comanda_digital.model.FichaTecnicaItem;
import com.unasp.comanda_digital.model.Ingrediente;
import com.unasp.comanda_digital.model.Prato;
import com.unasp.comanda_digital.repository.FichaTecnicaRepository;
import com.unasp.comanda_digital.repository.IngredienteRepository;
import com.unasp.comanda_digital.repository.PratoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FichaTecnicaService {

    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final PratoRepository pratoRepository;
    private final IngredienteRepository ingredienteRepository;

    public FichaTecnicaDTO buscarPorPrato(Long pratoId) {
        FichaTecnica ficha = fichaTecnicaRepository.findByPratoId(pratoId)
                .orElseThrow(() -> new RuntimeException("Ficha técnica não encontrada!"));
        return FichaTecnicaDTO.fromEntity(ficha);
    }

    @Transactional
    public FichaTecnicaDTO salvar(Long pratoId, FichaTecnicaDTO dto) {
        Prato prato = pratoRepository.findById(pratoId)
                .orElseThrow(() -> new RuntimeException("Prato não encontrado!"));

        FichaTecnica ficha = fichaTecnicaRepository.findByPratoId(pratoId)
                .orElse(new FichaTecnica());

        ficha.setPrato(prato);
        ficha.setRendimento(dto.getRendimento() != null ? dto.getRendimento() : 1);
        ficha.setModoPreparo(dto.getModoPreparo());

        if (ficha.getItens() == null) {
            ficha.setItens(new ArrayList<>());
        } else {
            ficha.getItens().clear();
        }

        if (dto.getItens() != null) {
            for (FichaTecnicaItemDTO itemDTO : dto.getItens()) {
                Ingrediente ingrediente = ingredienteRepository.findById(itemDTO.getIngredienteId())
                        .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado!"));

                FichaTecnicaItem item = new FichaTecnicaItem();
                item.setFichaTecnica(ficha);
                item.setIngrediente(ingrediente);
                item.setQuantidade(itemDTO.getQuantidade());
                item.setUnidade(itemDTO.getUnidade());
                item.setFatorCorrecao(itemDTO.getFatorCorrecao() != null ?
                        itemDTO.getFatorCorrecao() : java.math.BigDecimal.ONE);

                ficha.getItens().add(item);
            }
        }

        FichaTecnica salva = fichaTecnicaRepository.save(ficha);
        return FichaTecnicaDTO.fromEntity(salva);
    }
}