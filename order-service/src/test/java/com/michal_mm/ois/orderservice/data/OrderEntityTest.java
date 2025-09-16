package com.michal_mm.ois.orderservice.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderEntityTest {

    private static OrderEntity orderEntityFullConstructor;
    private static OrderEntity orderEntityDefaultConstructor;

    private static final UUID orderId = UUID.randomUUID();
    private static final UUID defaultOrderId = UUID.randomUUID();

    private static final UUID itemId = UUID.randomUUID();
    private static final UUID defaultItemId = UUID.randomUUID();

    private static final String orderName = "Unit Test Order Name";
    private static final String defaultOrderName = "DEFAULT Unit Test Order Name";

    private static final Integer quantity = 111;
    private static final Integer defaultQuantity = 222;

    private static final Integer price = 9999;
    private static final Integer defaultPrice = 8888;

    @BeforeAll
    public static void setUp() {
        orderEntityDefaultConstructor = new OrderEntity();
        orderEntityFullConstructor = new OrderEntity(orderId, itemId, orderName, quantity, price);
    }

    @Test
    void notNullObjects() {
        assertNotNull(orderEntityDefaultConstructor);
        assertNotNull(orderEntityFullConstructor);
    }

    @Test
    void getId() {
        assertEquals(orderId.toString(), orderEntityFullConstructor.getId().toString());
    }

    @Test
    void setId() {
        orderEntityDefaultConstructor.setId(defaultOrderId);
        assertEquals(defaultOrderId.toString(), orderEntityDefaultConstructor.getId().toString());
    }

    @Test
    void getItemId() {
        assertEquals(itemId.toString(), orderEntityFullConstructor.getItemId().toString());
    }

    @Test
    void setItemId() {
        orderEntityDefaultConstructor.setItemId(defaultItemId);
        assertEquals(defaultItemId.toString(), orderEntityDefaultConstructor.getItemId().toString());
    }

    @Test
    void getOrderName() {
        assertEquals(orderName, orderEntityFullConstructor.getOrderName());
    }

    @Test
    void setOrderName() {
        orderEntityDefaultConstructor.setOrderName(defaultOrderName);
        assertEquals(defaultOrderName, orderEntityDefaultConstructor.getOrderName());
    }

    @Test
    void getQuantity() {
        assertEquals(quantity, orderEntityFullConstructor.getQuantity());
    }

    @Test
    void setQuantity() {
        orderEntityDefaultConstructor.setQuantity(defaultQuantity);
        assertEquals(defaultQuantity, orderEntityDefaultConstructor.getQuantity());
    }

    @Test
    void getItemPrice() {
        assertEquals(price, orderEntityFullConstructor.getItemPrice());
    }

    @Test
    void setItemPrice() {
        orderEntityDefaultConstructor.setItemPrice(defaultPrice);
        assertEquals(defaultPrice, orderEntityDefaultConstructor.getItemPrice());
    }
}