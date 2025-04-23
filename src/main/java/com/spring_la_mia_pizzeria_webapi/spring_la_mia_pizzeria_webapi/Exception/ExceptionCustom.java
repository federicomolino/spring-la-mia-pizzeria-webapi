package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Exception;

public class ExceptionCustom extends RuntimeException{
    private String Error;

    public ExceptionCustom(String message) {
        super(message);
        this.Error = Error;
    }

    public String getError(){
        return Error;
    }
}
