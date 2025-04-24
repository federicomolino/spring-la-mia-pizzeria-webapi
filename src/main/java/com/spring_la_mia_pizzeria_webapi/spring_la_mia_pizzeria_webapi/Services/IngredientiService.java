package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Services;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Ingrediente;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.IngredientiRepository;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.Pizze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientiService {

    @Autowired
    private IngredientiRepository ingredientiRepository;

    @Autowired
    private Pizze pizzaRepository;

    //Lista Ingredienti
    public List<Ingrediente> showIngredienti(){
        return ingredientiRepository.findAll();
    }

    //Aggiunta Ingredienti
    public Ingrediente AddIngredienti (Ingrediente ingredienteInput){
        List<Ingrediente> ingrediente = ingredientiRepository.findByIngrediente(ingredienteInput.getIngrediente());

        if (!ingrediente.isEmpty()){
           throw new IllegalArgumentException("Nome ingrendiente già presente");
        }
        ingredientiRepository.save(ingredienteInput);
        return ingredienteInput;
    }

    //eliminazione
    public void DeleteIngrediente(@RequestParam("id")Integer id_ingrediente){
        Optional<Ingrediente> IngredienteID = ingredientiRepository.findById(id_ingrediente);
        if (IngredienteID.isEmpty()){
            throw new IllegalArgumentException("Id passato non valido");
        }
        ingredientiRepository.deleteById(id_ingrediente);
    }
}
