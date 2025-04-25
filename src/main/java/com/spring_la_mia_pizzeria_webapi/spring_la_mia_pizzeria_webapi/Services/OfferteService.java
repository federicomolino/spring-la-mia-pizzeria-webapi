package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Services;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.OffertaSpecial;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.Pizza;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.OfferteSpecialiRepository;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Repository.Pizze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OfferteService {

    @Autowired
    private OfferteSpecialiRepository offerteSpecialiRepository;

    @Autowired
    private Pizze pizzaRepository;

    //Vista offerte
    public boolean isOffertaScaduta(OffertaSpecial offerta) {
        return offerta.getFineOfferta().isBefore(LocalDate.now());
    }

    public List<OffertaSpecial> showOfferte(Pizza pizza){
        List<OffertaSpecial> offerta = offerteSpecialiRepository.findByPizza(pizza);

        //verifico se ci sono offerte scadute
        for (OffertaSpecial offertaSpecial : offerta){
            offertaSpecial.setScaduta(isOffertaScaduta(offertaSpecial));
        }

        return offerta;
    }

    //Crea Offerta
    public OffertaSpecial creaOfferta(OffertaSpecial offertaSpecial, Integer id){
        //recupero id pizza
        Pizza p = pizzaRepository.findById(id).get();
        if (offertaSpecial.getInizioOfferta().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("La data non può essere inferiore ad oggi");
        } else if (offertaSpecial.getFineOfferta().isBefore(offertaSpecial.getInizioOfferta())) {
            throw new IllegalArgumentException("La data non può inferiore alla data di inizio");
        }
        if (offertaSpecial.getTitoloOfferta().length()>30){
            throw new IllegalArgumentException("Lunghezza massimo di 30 caratteri");
        }
        //Associo la pizza all'offerta
        offertaSpecial.setPizza(p);
        //forzo a creare nuova entità
        offertaSpecial.setId(null);
        //Salvo
        return offerteSpecialiRepository.save(offertaSpecial);
    }

    //Cancella Singola offerta
    public void cancellaOfferta(Long id){
        //Cancello in base id
        offerteSpecialiRepository.deleteById(id);
    }

    //Modifica Offerta
    public OffertaSpecial modificaOfferta(Long id, OffertaSpecial offertaForm){
        //Mi recupero l'offerta
        OffertaSpecial offertaEsistente = offerteSpecialiRepository.findById(id).get();
        if (offertaForm.getInizioOfferta().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("La data non può essere inferiore ad oggi");
        } else if (offertaForm.getFineOfferta().isBefore(offertaForm.getInizioOfferta())) {
            throw new IllegalArgumentException("La data non può inferiore alla data di inizio");
        }
        if (offertaForm.getTitoloOfferta().length()>30){
            throw new IllegalArgumentException("Lunghezza massimo di 30 caratteri");
        }
        //salviamo i nuovi dati
        offertaEsistente.setInizioOfferta(offertaForm.getInizioOfferta());
        offertaEsistente.setFineOfferta(offertaForm.getFineOfferta());
        offertaEsistente.setTitoloOfferta(offertaForm.getTitoloOfferta());
        return offerteSpecialiRepository.save(offertaEsistente);
    }
}
