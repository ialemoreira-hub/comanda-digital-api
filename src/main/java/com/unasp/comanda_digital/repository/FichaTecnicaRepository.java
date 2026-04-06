package com.unasp.comanda_digital.repository;

import com.unasp.comanda_digital.model.FichaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Long> {
    Optional<FichaTecnica> findByPratoId(Long pratoId);
    boolean existsByPratoId(Long pratoId);
}