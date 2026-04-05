package com.unasp.comanda_digital.repository;

import com.unasp.comanda_digital.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByStatusOrderByOrdem(Categoria.Status status);
}