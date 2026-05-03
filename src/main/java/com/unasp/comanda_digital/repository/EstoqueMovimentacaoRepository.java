package com.unasp.comanda_digital.repository;

import com.unasp.comanda_digital.model.EstoqueMovimentacao;
import com.unasp.comanda_digital.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface EstoqueMovimentacaoRepository extends JpaRepository<EstoqueMovimentacao, Long> {

    @Query("SELECT COALESCE(SUM(CASE WHEN m.tipo = 'ENTRADA' THEN m.quantidade ELSE -m.quantidade END), 0) " +
            "FROM EstoqueMovimentacao m WHERE m.ingrediente = :ingrediente")
    BigDecimal calcularSaldo(@Param("ingrediente") Ingrediente ingrediente);
}