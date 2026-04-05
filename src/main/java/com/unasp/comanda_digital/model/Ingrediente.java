package com.unasp.comanda_digital.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ingrediente")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String sku;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_padrao", nullable = false)
    private Unidade unidadePadrao;

    @Column(name = "estoque_minimo")
    private BigDecimal estoqueMinimo = BigDecimal.ZERO;

    @Column(name = "custo_unitario")
    private BigDecimal custoUnitario = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Unidade {
        G, ML, UN, KG, L
    }

    public enum Status {
        ATIVO, INATIVO
    }
}