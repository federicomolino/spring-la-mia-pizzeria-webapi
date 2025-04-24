package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.ApiRest;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Pizza;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Services.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/pizza")
public class ApiPizze {

    @Autowired
    private PizzaService pizzaService;

    public ApiPizze(PizzaService pizzaService){
        this.pizzaService = pizzaService;
    }

    //Cerca pizza
    @GetMapping
    public List<Pizza> CercaPizza(@RequestParam(value = "name", required = false) String name){
        return pizzaService.pizze(name);
    }

    //Cancella pizza
    @DeleteMapping("{id}")
    public void CancellaPizza(@PathVariable("id") Integer id){
        pizzaService.CancellaPizza(id);
    }

    //Modifica Pizza
    @PutMapping("{id}")
    public ResponseEntity<Pizza> EditPizza(@PathVariable("id")Integer id, @RequestBody @Valid Pizza pizza) throws IOException {
        pizza.setId(id);
        Pizza updatePizza = pizzaService.EditPizza(pizza,null,null);
        return ResponseEntity.ok(updatePizza);
    }

    //Aggiungi Pizza
    @PostMapping
    public ResponseEntity<Pizza> AddPizza(@RequestBody @Valid Pizza pizza) throws IOException {
        Pizza newPizza = pizzaService.AddPizza(pizza,null,null);
        return ResponseEntity.ok(newPizza);
    }
}
