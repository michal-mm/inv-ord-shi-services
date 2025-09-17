package com.michal_mm.ois.inventoryservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InventoryGlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String itemNotFoundHandler(ItemNotFoundException e) {
        StringBuilder strBuilder = new StringBuilder()
                .append("Item ID not found in the Inventory ")
                .append("[").append(e.getClass()).append("]: ")
                .append(e.getMessage());
        logger.warn(strBuilder.toString());
        return strBuilder.toString();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String anyExceptionHandler(Exception e) {
        StringBuilder strBuilder = new StringBuilder()
                .append("Received OTHER unhandled exception")
                .append("[").append(e.getClass()).append("]: ")
                .append(e.getMessage());
        logger.warn(strBuilder.toString());
        return strBuilder.toString();
    }
}
