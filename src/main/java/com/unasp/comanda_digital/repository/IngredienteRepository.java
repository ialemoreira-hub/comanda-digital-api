package com.unasp.comanda_digital.repository;

import com.unasp.comanda_digital.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    List<Ingrediente> findByStatus(Ingrediente.Status status);
    boolean existsBySku(String sku);
}