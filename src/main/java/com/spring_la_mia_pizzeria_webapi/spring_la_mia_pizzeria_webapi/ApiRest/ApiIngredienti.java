package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.ApiRest;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Ingrediente;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Services.IngredientiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/ingrediente")
public class ApiIngredienti {

    @Autowired
    private IngredientiService ingredientiService;

    @GetMapping
    public List<Ingrediente> showIngredienti(){
        return ingredientiService.showIngredienti();
    }

    //Aggiunta ingrediente
    @PostMapping
    public ResponseEntity<Ingrediente> AddIngrediente(@RequestBody @Valid Ingrediente ingrediente){
        Ingrediente newIngrediente = ingredientiService.AddIngredienti(ingrediente);
        return ResponseEntity.ok(newIngrediente);
    }

    @DeleteMapping("{id}")
    public void CancellaIngrediente(@PathVariable("id") Integer id){
        ingredientiService.DeleteIngrediente(id);
    }
}
