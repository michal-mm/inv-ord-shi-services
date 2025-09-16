package com.michal_mm.ois.orderservice.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateOrderRequestTest {

    private static CreateOrderRequest croFullConstructor;
    private static CreateOrderRequest croDefaultConstructor;

    private static final UUID itemId = UUID.randomUUID();
    private static final UUID defaultItemId = UUID.randomUUID();

    private static final String orderName = "Junit Order name";
    private static final String defaultOrderName = "DEFAULT Junit order name";

    private static final Integer quantity = 44;
    private static final Integer defaultQuantity = 777;

    @BeforeAll
    public static void setUp() {
        croDefaultConstructor = new CreateOrderRequest();
        croFullConstructor = new CreateOrderRequest(itemId, orderName, quantity);
    }

    @Test
    void notNullObjects() {
        assertNotNull(croDefaultConstructor);
        assertNotNull(croFullConstructor);
    }


    @Test
    void getItemId() {
        assertEquals(itemId.toString(), croFullConstructor.getItemId().toString());
    }

    @Test
    void setItemId() {
        croDefaultConstructor.setItemId(defaultItemId);
        assertEquals(defaultItemId.toString(), croDefaultConstructor.getItemId().toString());
    }

    @Test
    void getOrderName() {
        assertEquals(orderName, croFullConstructor.getOrderName());
    }

    @Test
    void setOrderName() {
        croDefaultConstructor.setOrderName(defaultOrderName);
        assertEquals(defaultOrderName, croDefaultConstructor.getOrderName());
    }

    @Test
    void getQuantity() {
        assertEquals(quantity, croFullConstructor.getQuantity());
    }

    @Test
    void setQuantity() {
        croDefaultConstructor.setQuantity(defaultQuantity);
        assertEquals(defaultQuantity, croDefaultConstructor.getQuantity());
    }
}