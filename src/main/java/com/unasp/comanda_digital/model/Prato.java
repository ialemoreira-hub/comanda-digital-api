package com.unasp.comanda_digital.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "prato")
public class Prato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "preco_venda", nullable = false)
    private BigDecimal precoVenda;

    @Column(name = "tempo_preparo_min")
    private Integer tempoPreparo;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status {
        ATIVO, INATIVO, PAUSADO
    }
}