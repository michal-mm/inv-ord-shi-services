package com.michal_mm.ois.orderservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class OrderGlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String orderNotFoundHandler (OrderNotFoundException e) {
        StringBuilder strBuilder = new StringBuilder()
                .append("Order ID not found ")
                .append("[").append(e.getClass()).append("]: ")
                .append(e.getMessage());
        logger.warn(strBuilder.toString());
        return strBuilder.toString();
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String itemNotFoundHandler (HttpClientErrorException.NotFound e) {
        logger.warn(e.getMessage());
        return e.getMessage();
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
