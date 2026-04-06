package com.unasp.comanda_digital.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "ficha_tecnica")
public class FichaTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "prato_id", nullable = false, unique = true)
    private Prato prato;

    private Integer rendimento = 1;

    @Column(name = "modo_preparo", columnDefinition = "TEXT")
    private String modoPreparo;

    @OneToMany(mappedBy = "fichaTecnica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FichaTecnicaItem> itens;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}