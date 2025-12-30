package com.michal_mm.ois.shippingservice.exception;

public class DuplicateShipmentException extends RuntimeException {
    public DuplicateShipmentException(String message) {
        super(message);
    }
}