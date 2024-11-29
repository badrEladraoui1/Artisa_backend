package com.artisa.artisa.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

public class ArtisaException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public ArtisaException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ArtisaException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }
}