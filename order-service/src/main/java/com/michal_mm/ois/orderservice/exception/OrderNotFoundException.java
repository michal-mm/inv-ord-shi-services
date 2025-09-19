package com.michal_mm.ois.orderservice.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException (String msg) {
        super(msg);
    }
}
