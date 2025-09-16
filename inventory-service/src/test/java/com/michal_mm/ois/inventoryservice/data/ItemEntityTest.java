package com.michal_mm.ois.inventoryservice.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ItemEntityTest {

    private static ItemEntity itemEntityDefaultConstructor;
    private static ItemEntity itemEntityFullyInitialized;

    private static final UUID uuid = UUID.randomUUID();
    private static final String itemName = "Unit Test item name";
    private static final Integer amount = 555;
    private static final Integer price = 9999;

    @BeforeAll
    static void setUp() {
        itemEntityDefaultConstructor = new ItemEntity();
        itemEntityFullyInitialized = new ItemEntity(uuid,
                itemName,
                amount,
                price);
    }

    @Test
    void testNotNullItemEntityObjects() {
        assertNotNull(itemEntityDefaultConstructor);
        assertNotNull(itemEntityFullyInitialized);
    }

    @Test
    void getItemId() {
        assertEquals(uuid.toString(), itemEntityFullyInitialized.getItemId().toString());
    }

    @Test
    void setItemId() {
        itemEntityDefaultConstructor.setItemId(uuid);
        assertEquals(uuid.toString(), itemEntityDefaultConstructor.getItemId().toString());
    }

    @Test
    void getItemName() {
        assertEquals(itemName, itemEntityFullyInitialized.getItemName());
    }

    @Test
    void setItemName() {
        itemEntityDefaultConstructor.setItemName(itemName);
        assertEquals(itemName, itemEntityDefaultConstructor.getItemName());
    }

    @Test
    void getAmount() {
        assertEquals(amount, itemEntityFullyInitialized.getAmount());
    }

    @Test
    void setAmount() {
        itemEntityDefaultConstructor.setAmount(amount);
        assertEquals(amount, itemEntityDefaultConstructor.getAmount());
    }

    @Test
    void getPrice() {
        assertEquals(price, itemEntityFullyInitialized.getPrice());
    }

    @Test
    void setPrice() {
        itemEntityDefaultConstructor.setPrice(price);
        assertEquals(price, itemEntityDefaultConstructor.getPrice());
    }
}