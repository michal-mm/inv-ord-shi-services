package com.michal_mm.ois.inventoryservice.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateItemRequestTest {

    private static CreateItemRequest criDefaultConstructor;
    private static CreateItemRequest criFullConstructor;

    private static final String itemName = "Unit test item name";
    private static final Integer amount = 555;
    private static final Integer price = 9999;

    private static final String defaultItemName = "DEFAULT Unit test item name";
    private static final Integer defaultAmount = 111;
    private static final Integer defaultPrice = 3333;


    @BeforeAll
    public static void setUp() {
        criDefaultConstructor = new CreateItemRequest();
        criFullConstructor = new CreateItemRequest(itemName, amount, price);
    }

    @Test
    void notNullObjects() {
        assertNotNull(criDefaultConstructor);
        assertNotNull(criFullConstructor);
    }

    @Test
    void getItemName() {
        assertEquals(itemName, criFullConstructor.getItemName());
    }

    @Test
    void setItemName() {
        criDefaultConstructor.setItemName(defaultItemName);
        assertEquals(defaultItemName, criDefaultConstructor.getItemName());
    }

    @Test
    void getAmount() {
        assertEquals(amount, criFullConstructor.getAmount());
    }

    @Test
    void setAmount() {
        criDefaultConstructor.setAmount(defaultAmount);
        assertEquals(defaultAmount, criDefaultConstructor.getAmount());
    }

    @Test
    void getPrice() {
        assertEquals(price, criFullConstructor.getPrice());
    }

    @Test
    void setPrice() {
        criDefaultConstructor.setPrice(defaultPrice);
        assertEquals(defaultPrice, criDefaultConstructor.getPrice());
    }

    @Test
    void testToString() {
        String expectedToString = "CreateItemRequest{" +
                "itemName='" + itemName + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';

        assertEquals(expectedToString, criFullConstructor.toString());
    }
}