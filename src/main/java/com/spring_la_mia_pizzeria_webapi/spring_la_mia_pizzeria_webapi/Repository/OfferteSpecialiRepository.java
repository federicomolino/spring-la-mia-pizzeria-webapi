package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.OffertaSpecial;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferteSpecialiRepository extends JpaRepository<OffertaSpecial,Long> {

    @Query("select o from OffertaSpecial o where o.pizza.id = :pizza_id")
    List<OffertaSpecial> findByIdPizza(@Param("pizza_id") Long pizzaId);

    List<OffertaSpecial> findByPizza(Pizza pizza);

}
