package com.michal_mm.ois.orderservice.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateOrderRequestTest {

    private static final UUID itemId = UUID.randomUUID();
    private static final String orderName = "Junit Order name";
    private static final Integer quantity = 44;

    @Test
    void testRecordCreation() {
        CreateOrderRequest request = new CreateOrderRequest(itemId, orderName, quantity);
        assertNotNull(request);
    }

    @Test
    void testItemId() {
        CreateOrderRequest request = new CreateOrderRequest(itemId, orderName, quantity);
        assertEquals(itemId, request.itemId());
    }

    @Test
    void testOrderName() {
        CreateOrderRequest request = new CreateOrderRequest(itemId, orderName, quantity);
        assertEquals(orderName, request.orderName());
    }

    @Test
    void testQuantity() {
        CreateOrderRequest request = new CreateOrderRequest(itemId, orderName, quantity);
        assertEquals(quantity, request.quantity());
    }

    @Test
    void testEqualsAndHashCode() {
        CreateOrderRequest request1 = new CreateOrderRequest(itemId, orderName, quantity);
        CreateOrderRequest request2 = new CreateOrderRequest(itemId, orderName, quantity);
        CreateOrderRequest request3 = new CreateOrderRequest(UUID.randomUUID(), "Different", 999);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1, request3);
    }

    @Test
    void testToString() {
        CreateOrderRequest request = new CreateOrderRequest(itemId, orderName, quantity);
        String toString = request.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("CreateOrderRequest"));
        assertTrue(toString.contains(itemId.toString()));
        assertTrue(toString.contains(orderName));
        assertTrue(toString.contains(quantity.toString()));
    }
}