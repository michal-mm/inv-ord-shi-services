package com.michal_mm.ois.orderservice.exception;

public class NotEnoughItemsInInventoryException extends RuntimeException {

    public NotEnoughItemsInInventoryException(String msg) {
        super(msg);
    }
}
