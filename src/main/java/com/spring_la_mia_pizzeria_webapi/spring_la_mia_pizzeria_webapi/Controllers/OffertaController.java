package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Controllers;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.OffertaSpecial;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Pizza;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.OfferteSpecialiRepository;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.Pizze;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Services.OfferteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class OffertaController {

    @Autowired
    private OfferteService offerteService;

    @Autowired
    private OfferteSpecialiRepository offerteSpecialiRepository;

    @Autowired
    private Pizze pizzaRepository;

    @GetMapping("/pizza/{id}/offer")
    public String ShowpageOfferta(@PathVariable("id") Integer id, Model model){
        Pizza pizza = pizzaRepository.findById(id).get();
        //Associo la pizza all'offerta
        OffertaSpecial offertaSpecial = new OffertaSpecial();
        offertaSpecial.setPizza(pizza);
        model.addAttribute("pizza", pizza);
        model.addAttribute("formAdd", offertaSpecial);
        return "pizza/AddEditOfferta";
    }



    @PostMapping("/pizza/{id}/offer")
    public String CreateOfferta(@Valid @ModelAttribute("formAdd") OffertaSpecial offertaSpecial,
                                BindingResult bindingResult, Model model, @PathVariable Integer id){

        if (offertaSpecial.getInizioOfferta().isBefore(LocalDate.now())){
            bindingResult.rejectValue("inizioOfferta","inizioOfferta",
                    "La data non può essere inferiore ad oggi");
        } else if (offertaSpecial.getFineOfferta().isBefore(offertaSpecial.getInizioOfferta())) {
            bindingResult.rejectValue("fineOfferta","fineOfferta",
                    "La data non può inferiore alla data di inizio");
        }
        if (offertaSpecial.getTitoloOfferta().length()>30){
            bindingResult.rejectValue("titoloOfferta","titoloOfferta",
                    "Lunghezza massimo di 30 caratteri");
        }

        if (bindingResult.hasErrors()){
            //evitiamo che pizzaid sia null
            Pizza p = pizzaRepository.findById(id).get();
            offertaSpecial.setPizza(p);
            model.addAttribute("formAdd", offertaSpecial);
            model.addAttribute("pizza", p);
            return "pizza/AddEditOfferta";
        }
        offerteService.creaOfferta(offertaSpecial, id);
        model.addAttribute("formAdd", offertaSpecial);
        return "redirect:/pizza/" + offertaSpecial.getPizza().getId();
    }

    //Cancella offerta
    @PostMapping("offerta/delete/{id}")
    public String DeleteOfferta(@PathVariable("id") Long id){
        OffertaSpecial offertaSpecial = offerteSpecialiRepository.findById(id).get();
        Integer idPizza = offertaSpecial.getPizza().getId();
//        //Cancello in base id
        offerteService.cancellaOfferta(id);
        return "redirect:/pizza/" + idPizza;
    }

    //Modifica Offerta
    @GetMapping("/offerta/{id}/offer/edit")
    public String ShowpageEditOfferta(@PathVariable("id") Long idOfferta, Model model){
        //Recupero l'offerta tramite id
        OffertaSpecial offertaSpecial = offerteSpecialiRepository.findById(idOfferta).get();

        model.addAttribute("formAdd", offertaSpecial);
        return "pizza/modificaOfferta";
    }

    @PostMapping("/offerta/{id}/offer/edit")
    public String EditPizzaOfferta(@PathVariable("id") Long id,
                                   @Valid @ModelAttribute("formAdd") OffertaSpecial offertaForm,
                                   Model model, BindingResult bindingResult){

        if (offertaForm.getInizioOfferta().isBefore(LocalDate.now())){
            bindingResult.rejectValue("inizioOfferta","inizioOfferta",
                    "La data non può essere inferiore ad oggi");
        } else if (offertaForm.getFineOfferta().isBefore(offertaForm.getInizioOfferta())) {
            bindingResult.rejectValue("fineOfferta","fineOfferta",
                    "La data non può inferiore alla data di inizio");
        }
        if (offertaForm.getTitoloOfferta().length()>30){
            bindingResult.rejectValue("titoloOfferta","titoloOfferta",
                    "Lunghezza massimo di 30 caratteri");
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("formAdd", offertaForm);
            return "pizza/modificaOfferta";
        }

        OffertaSpecial modificaOfferta = offerteService.modificaOfferta(id, offertaForm);
        Integer idPizza = modificaOfferta.getPizza().getId();
        return "redirect:/pizza/" + idPizza;
    }
}

