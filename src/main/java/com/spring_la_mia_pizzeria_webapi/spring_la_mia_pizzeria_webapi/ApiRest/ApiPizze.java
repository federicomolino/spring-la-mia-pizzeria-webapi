package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.ApiRest;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Pizza;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Services.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    @GetMapping
    //ResponseEntity<?> permette di restituire o una lista di pizze o un messaggio d'errore
    public ResponseEntity<?> cercaPizza(@RequestParam(value = "name", required = false) String name){
        List<Pizza> pizza = pizzaService.pizze(name);
        if (pizza.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("Nessuna Pizza trovata");
        }
        return ResponseEntity.ok(pizza);
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
