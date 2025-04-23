package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Exception;

import com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> ErroreGenerico(Exception ex, HttpServletRequest request){
        ErrorResponse errore = new ErrorResponse(
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errore,HttpStatus.NOT_FOUND);
    }
}
