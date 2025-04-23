package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Controllers;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.OffertaSpecial;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Pizza;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.IngredientiRepository;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.OfferteSpecialiRepository;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.Pizze;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Services.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PizzaController {

    //root a pagina autenticazione
    @GetMapping("/")
    public String PageLogin(){
        return "redirect:/pizza";
    }

    @Autowired
    private Pizze pizzaRepository; //pizzaRepository è l'oggetto

    @Autowired
    private IngredientiRepository ingredientiRepository;

    @Autowired
    private OfferteSpecialiRepository offerteSpecialiRepository;

    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/pizza")
    public String index(@RequestParam(name = "name" , required = false) String name, Model model){
        List<Pizza> pizze = pizzaService.pizze(name);
        if (pizze.isEmpty()){
            model.addAttribute("errorMessage", "Pizza non trovata");
        }
        model.addAttribute("list", pizze);
        return "pizza/index";
    }

    @GetMapping("/pizza/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        Optional<Pizza> pizza = pizzaRepository.findById(id);
        if (pizza.isPresent()){
            //Mi prendo l'id della pizza che fa riferimento all'offerta e poi lo passo al front
            Pizza pizza1 = pizza.get();

            List<OffertaSpecial> offerte = offerteSpecialiRepository.findByPizza(pizza1);
            model.addAttribute("offertaSpeciali", offerte);
            model.addAttribute("pizza",pizza1);
            return "pizza/show";
        }
        return "pizza/error";
    }

    //Aggiungere Pizza
    @GetMapping("/addPizza")
    public String ShowPageRegistrer(Model model){
        //passimo la lista di tutti gli ingredienti
        model.addAttribute("list", ingredientiRepository.findAll());
        model.addAttribute("formAdd", new Pizza());
        return "pizza/addPizza";
    }

    @PostMapping("/addPizza")
    public String addPizza(@Valid @ModelAttribute("formAdd") Pizza pizzaForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes, @RequestParam(value = "ingrediente", required = false)
                           List<Integer> IngredientiID, @RequestParam(value = "image", required = false) MultipartFile Image,
                           Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("list", ingredientiRepository.findAll());
            return "pizza/addPizza";
        }
        try {
            pizzaService.AddPizza(pizzaForm, IngredientiID, Image);
            redirectAttributes.addFlashAttribute("success", "Pizza creata con successo");
            return "redirect:/pizza";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("name", "messageError", e.getMessage());
            model.addAttribute("list", ingredientiRepository.findAll());
            return "pizza/addPizza";
        }
    }

    //Modifica pizza
    @GetMapping("/pizza/editPizza/{id}")
    public String ShowEditPizza(@PathVariable("id") Integer id, Model model){
        Pizza pizza = pizzaRepository.findById(id).get();
        model.addAttribute("pizza", pizza);
        model.addAttribute("formAdd", pizzaRepository.findById(id).get());
        model.addAttribute("list", ingredientiRepository.findAll());
        //Per vedere quelli che hanno già il check
        model.addAttribute("ingrendienteCheck", pizza.getIngredienti());
        return"pizza/editPizza";
    }

    @PostMapping("/pizza/editPizza/{id}")
    public String EditPizza(@Valid @ModelAttribute("formAdd") Pizza pizzaForm,
                            Model model, BindingResult bindingResult,
                            @RequestParam(value = "ingrediente", required = false) List<Integer> IngredientiID,
                            @RequestParam(value = "image", required = false) MultipartFile Image) throws IOException {
        if (bindingResult.hasErrors()){
            model.addAttribute("pizza", pizzaForm);
            return "pizza/editPizza";
        }
        pizzaService.EditPizza(pizzaForm, IngredientiID, Image);
        return "redirect:/pizza";
    }

    //Cancella pizza
    @PostMapping("pizza/delete/{id}")
    public String DeletePizza(@PathVariable("id") Integer id){
        pizzaService.CancellaPizza(id);
        return "redirect:/pizza";
    }
}
