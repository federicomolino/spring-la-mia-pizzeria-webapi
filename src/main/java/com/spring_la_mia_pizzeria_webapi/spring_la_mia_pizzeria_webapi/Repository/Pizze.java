package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pizze extends JpaRepository<Pizza,Integer> {
    List<Pizza> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);
}
