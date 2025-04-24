package com.spring_la_mia_pizzeria_webapi.spring_la_mia_pizzeria_webapi.Entity;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String messsage;
    private String path;
    private LocalDateTime timeStamp;

    public ErrorResponse(String messsage, String path){
        this.messsage = messsage;
        this.path = path;
        this.timeStamp = LocalDateTime.now();
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

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
