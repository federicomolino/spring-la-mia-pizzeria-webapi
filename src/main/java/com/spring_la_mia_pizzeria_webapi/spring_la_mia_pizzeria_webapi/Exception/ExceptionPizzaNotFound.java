package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceptionPizzaNotFound extends RuntimeException{
    public ExceptionPizzaNotFound(String message) {
        super(message);
    }
}
