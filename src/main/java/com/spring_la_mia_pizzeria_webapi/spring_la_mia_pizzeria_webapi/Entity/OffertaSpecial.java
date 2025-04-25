package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class OffertaSpecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inizioOfferta;

    @NotNull
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    private LocalDate fineOfferta;

    @NotNull
    private String titoloOfferta;

    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    @JsonBackReference
    private Pizza pizza;

    private boolean isScaduta;

    public boolean isScaduta() {
        return isScaduta;
    }

    public void setScaduta(boolean scaduta) {
        isScaduta = scaduta;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInizioOfferta() {
        return inizioOfferta;
    }

    public void setInizioOfferta(LocalDate inizioOfferta) {
        this.inizioOfferta = inizioOfferta;
    }

    public LocalDate getFineOfferta() {
        return fineOfferta;
    }

    public void setFineOfferta(LocalDate fineOfferta) {
        this.fineOfferta = fineOfferta;
    }

    public String getTitoloOfferta() {
        return titoloOfferta;
    }

    public void setTitoloOfferta(String titoloOfferta) {
        this.titoloOfferta = titoloOfferta;
    }
}

