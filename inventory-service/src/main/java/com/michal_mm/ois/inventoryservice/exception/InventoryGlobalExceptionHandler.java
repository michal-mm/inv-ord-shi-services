package com.michal_mm.ois.inventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InventoryGlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String anyExceptionHandler(Exception e) {
        System.out.println("EXCEPTION: " + e.getMessage());
        return "GLOBAL Received an exception " + e.getMessage();
    }
}
