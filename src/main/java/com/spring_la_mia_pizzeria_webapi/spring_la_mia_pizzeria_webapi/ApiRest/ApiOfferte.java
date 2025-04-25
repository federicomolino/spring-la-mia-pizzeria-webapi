package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.ApiRest;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.OffertaSpecial;
import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Services.OfferteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/offerta")
public class ApiOfferte {

    @Autowired
    private OfferteService offerteService;

    @PostMapping("{id}")
    public ResponseEntity<OffertaSpecial> creaOfferta(@PathVariable("id") Integer id, @RequestBody @Valid OffertaSpecial offertaSpecial){
       OffertaSpecial newOffertaSpecial =  offerteService.creaOfferta(offertaSpecial, id);
       return ResponseEntity.ok(newOffertaSpecial);
    }

    @DeleteMapping("{id}")
    public void cancellaOfferta(@PathVariable("id") Long id){
        offerteService.cancellaOfferta(id);
    }

    @PutMapping("edit/{id}")
    public OffertaSpecial offertaSpecial(@PathVariable("id") Long id, @RequestBody @Valid OffertaSpecial offertaSpecial){
        return offerteService.modificaOfferta(id, offertaSpecial);
    }

}
