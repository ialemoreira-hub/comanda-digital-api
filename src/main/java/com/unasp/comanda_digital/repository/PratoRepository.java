package com.unasp.comanda_digital.repository;

import com.unasp.comanda_digital.model.Prato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long> {
    List<Prato> findByStatus(Prato.Status status);
    List<Prato> findByCategoriaIdAndStatus(Long categoriaId, Prato.Status status);
    Page<Prato> findAll(Pageable pageable);
}