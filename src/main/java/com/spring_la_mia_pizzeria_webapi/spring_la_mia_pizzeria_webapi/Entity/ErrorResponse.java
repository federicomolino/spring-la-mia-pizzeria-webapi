package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity;

import java.time.LocalDate;

public class ErrorResponse {

    private String messsage;
    private String path;
    private LocalDate timeStamp;

    public ErrorResponse(String messsage, String path){
        this.messsage = messsage;
        this.path = path;
        this.timeStamp = LocalDate.now();
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }
}
