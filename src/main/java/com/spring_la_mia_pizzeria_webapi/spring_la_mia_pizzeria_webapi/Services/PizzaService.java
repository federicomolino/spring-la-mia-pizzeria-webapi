package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Services;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Ingrediente;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.OffertaSpecial;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Pizza;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Exception.ExceptionPizzaNotFound;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.IngredientiRepository;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.OfferteSpecialiRepository;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.Pizze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private Pizze pizzaRepository;

    @Autowired
    private IngredientiRepository ingredienteRepository;

    @Autowired
    private OfferteSpecialiRepository offerteSpecialiRepository;

    //cerca pizze
    public List<Pizza> pizze (String name){
        List<Pizza> result;
        if (name == null){
            result = pizzaRepository.findAll();
        }else {
            result = pizzaRepository.findByNameContainingIgnoreCase(name);
        }
        //Se non trovo la pizza lancio eccezione
        if (result.isEmpty()){
            throw new ExceptionPizzaNotFound ("Nome Pizza " + name + " non trovata");
        }
        return result;
    }

    //Aggiunta pizza
    public Pizza AddPizza(Pizza pizzaForm, List<Integer> ingredientiID, MultipartFile image)throws IOException{
        if (pizzaRepository.existsByName(pizzaForm.getName())){
            throw new IllegalArgumentException("Nome pizza già presente nel sistema");
        }
        try {
            Double.parseDouble(pizzaForm.getName());
            throw new IllegalArgumentException("Il nome della pizza non può essere un numero");
        } catch (NumberFormatException ignored) {

            if (ingredientiID != null && !ingredientiID.isEmpty()) {
                List<Ingrediente> ingredienti = ingredienteRepository.findAllById(ingredientiID);
                pizzaForm.setIngredienti(ingredienti);
            }

            if (image != null && !image.isEmpty()) {
                String uploadImg = "src/main/resources/static/";
                String fileName = image.getOriginalFilename();
                Path path = Paths.get(uploadImg + fileName);
                Files.createDirectories(path.getParent());
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                pizzaForm.setImgPath("/" + fileName);
            }
        }
        return pizzaRepository.save(pizzaForm);
    }

    //Modifica Pizza
    public Pizza EditPizza (Pizza pizzaForm, List<Integer> IngredienteId, MultipartFile Image) throws IOException{
        //Mi salvo l'oggetto e verifico se viene cambiato il nome
        Pizza p = pizzaRepository.findById(pizzaForm.getId()).get();
        if (!pizzaForm.getName().equals(p.getName()) || (pizzaForm.getName().trim().equals(""))){
            throw new IllegalArgumentException("Il nome non può essere modificato");
        }

        //Se non viene selezioneto nessun ingrendiente creo lista vuota
        if (IngredienteId == null) {
            IngredienteId = new ArrayList<>();
        }
        //Gestione dell'immagine
        if (Image !=null && !Image.isEmpty()){
            String uploadImg = "src/main/resources/static/";
            String fileName = Image.getOriginalFilename();
            Path path = Paths.get(uploadImg + fileName); //diamo il nome dell'immagine caricata al path
            Files.createDirectories(path.getParent()); // crea la cartella se non esiste
            //Prende il contenuto del file caricato, lo cìopia nel path, se già presente lo sovrascrive
            Files.copy(Image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            pizzaForm.setImgPath("/" + fileName);
        }else {
            pizzaForm.setImgPath(p.getImgPath());
        }
        //Recupero gli ingredienti e associo id all'ingrediente
        List<Ingrediente> ingredienti = ingredienteRepository.findAllById(IngredienteId);
        //collego gli ingredienti alla singola pizza
        pizzaForm.setIngredienti(ingredienti);

        //salviamo i nuovi dati
        pizzaForm.setDescription(pizzaForm.getDescription());
        pizzaForm.setPrice(pizzaForm.getPrice());

        pizzaRepository.save(pizzaForm);
        return pizzaRepository.save(pizzaForm);
    }

    //Cancello pizza
    public void CancellaPizza(Integer id){
        //Converto id in long
        Long pizzaId = id.longValue();
        List<OffertaSpecial> offerte = offerteSpecialiRepository.findByIdPizza(pizzaId);
        if (!offerte.isEmpty()){
            //Mi guardo le offerte
            for (OffertaSpecial offerta : offerte){
                //Dissocio le offerte dalla pizza
                offerta.setPizza(null);
            }
            offerteSpecialiRepository.saveAll(offerte);
            offerteSpecialiRepository.deleteAll(offerte);
        }
        //Cancello in base id
        pizzaRepository.deleteById(id);
    }
}

