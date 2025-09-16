package com.michal_mm.ois.inventoryservice.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ItemRestTest {

    private static ItemRest itemRestDefaultConstructor;
    private static ItemRest itemRestFullConstructor;

    private static final UUID uuid = UUID.randomUUID();
    private static final String itemName = "Unit Test item name";
    private static final Integer amount = 555;
    private static final Integer price = 9999;

    @BeforeAll
    public static void setUp() {
        itemRestDefaultConstructor = new ItemRest();
        itemRestFullConstructor = new ItemRest(uuid,
                itemName,
                amount,
                price);
    }

    @Test
    void notNullItemRestObjects() {
        assertNotNull(itemRestDefaultConstructor);
        assertNotNull(itemRestFullConstructor);
    }

    @Test
    void getItemId() {
        assertEquals(uuid.toString(), itemRestFullConstructor.getItemId().toString());
    }

    @Test
    void setItemId() {
        itemRestDefaultConstructor.setItemId(uuid);
        assertEquals(uuid.toString(), itemRestDefaultConstructor.getItemId().toString());
    }

    @Test
    void getItemName() {
        assertEquals(itemName, itemRestFullConstructor.getItemName());
    }

    @Test
    void setItemName() {
        itemRestDefaultConstructor.setItemName(itemName);
        assertEquals(itemName, itemRestDefaultConstructor.getItemName());
    }

    @Test
    void getAmount() {
        assertEquals(amount, itemRestFullConstructor.getAmount());
    }

    @Test
    void setAmount() {
        itemRestDefaultConstructor.setAmount(amount);
        assertEquals(amount, itemRestDefaultConstructor.getAmount());
    }

    @Test
    void getPrice() {
        assertEquals(price, itemRestFullConstructor.getPrice());
    }

    @Test
    void setPrice() {
        itemRestDefaultConstructor.setPrice(price);
        assertEquals(price, itemRestDefaultConstructor.getPrice());
    }

    @Test
    void testToString() {
        String expectedToString = "ItemRest{" +
                "itemId=" + uuid +
                ", itemName='" + itemName + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';

        assertEquals(expectedToString, itemRestFullConstructor.toString());
    }
}