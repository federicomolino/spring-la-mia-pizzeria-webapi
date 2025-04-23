package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientiRepository extends JpaRepository<Ingrediente, Integer> {

    @Query("SELECT i FROM Ingrediente i WHERE i.ingrediente = :ingrediente")
    List<Ingrediente> findByIngrediente(@Param("ingrediente") String ingrediente);
}
