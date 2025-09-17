package com.michal_mm.ois.inventoryservice.exception;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException(String s) {
        super(s);
    }
}
