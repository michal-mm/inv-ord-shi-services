package com.michal_mm.ois.inventoryservice.model;

import org.junit.jupiter.api.BeforeEach;
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

    private static final UUID defaultUuid = UUID.randomUUID();
    private static final String defaultItemName = "DEFAULT";
    private static final Integer defaultAmount = -100;
    private static final Integer defaultPrice = -9999;

    @BeforeEach
    public void setUp() {
        itemRestDefaultConstructor = new ItemRest(defaultUuid, 
            defaultItemName, 
            defaultAmount, 
            defaultPrice);
        itemRestFullConstructor = new ItemRest(uuid,
                itemName,
                amount,
                price);
    }

    private ItemRest getNewItemRest(ItemRest oldItem, UUID newUuid) {
        return new ItemRest(newUuid, oldItem.itemName(), oldItem.amount(), oldItem.price());
    }

    private ItemRest getNewItemRest(ItemRest oldItem, String newName) {
        return new ItemRest(oldItem.itemId(), newName, oldItem.amount(), oldItem.price());
    }

    private ItemRest getNewItemRestWithAmount(ItemRest oldItem, Integer newAmount) {
        return new ItemRest(oldItem.itemId(), oldItem.itemName(), newAmount, oldItem.price());
    }

    private ItemRest getNewItemRestWithPrice(ItemRest oldItem, Integer newPrice) {
        return new ItemRest(oldItem.itemId(), oldItem.itemName(), oldItem.amount(), newPrice);
    }

    @Test
    void notNullItemRestObjects() {
        assertNotNull(itemRestDefaultConstructor);
        assertNotNull(itemRestFullConstructor);
    }

    @Test
    void getItemId() {
        assertEquals(uuid.toString(), itemRestFullConstructor.itemId().toString());
    }

    @Test
    void setItemId() {
        var updatedItemRestDefaultConstructor = getNewItemRest(itemRestDefaultConstructor, uuid);
        assertEquals(uuid.toString(), updatedItemRestDefaultConstructor.itemId().toString());
    }

    @Test
    void getItemName() {
        assertEquals(itemName, itemRestFullConstructor.itemName());
    }

    @Test
    void setItemName() {
        var updatedItemRestDefaultConstructor = getNewItemRest(itemRestDefaultConstructor, itemName);
        assertEquals(itemName, updatedItemRestDefaultConstructor.itemName());
    }

    @Test
    void getAmount() {
        assertEquals(amount, itemRestFullConstructor.amount());
    }

    @Test
    void setAmount() {
        var updatedItemRestDefaultConstructor = getNewItemRestWithAmount(itemRestDefaultConstructor, amount);
        assertEquals(amount, updatedItemRestDefaultConstructor.amount());
    }

    @Test
    void getPrice() {
        assertEquals(price, itemRestFullConstructor.price());
    }

    @Test
    void setPrice() {
        var updatedItemRestDefaultConstructor = getNewItemRestWithPrice(itemRestDefaultConstructor, price);
        assertEquals(price, updatedItemRestDefaultConstructor.price());
    }

    @Test
    void testToString() {
        String expectedToString = "ItemRest[" +
                "itemId=" + uuid +
                ", itemName=" + itemName +
                ", amount=" + amount +
                ", price=" + price +
                ']';

        assertEquals(expectedToString, itemRestFullConstructor.toString());
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    void testEquals() {
        ItemRest anotherFullyInitializiedItemRest = itemRestFullConstructor;
        ItemRest anotherNotInitializedItemRest = itemRestDefaultConstructor;
        assertEquals(itemRestFullConstructor, anotherFullyInitializiedItemRest);
        assertEquals(itemRestDefaultConstructor, anotherNotInitializedItemRest);
        assertNotEquals(itemRestFullConstructor, itemRestDefaultConstructor);
        assertNotEquals(null, itemRestFullConstructor);
        // keep it this way to test the coverage in ItemRest
        assertFalse(itemRestFullConstructor.equals("Not an item rest object"));
        // keep it this way to test the coverage in ItemRest
        assertFalse(itemRestFullConstructor.equals(null));

        assertNotEquals("Not an item rest object", itemRestFullConstructor);
    }

    @Test
    void testEqualsStepByStep() {
        assertNotEquals(itemRestFullConstructor, itemRestDefaultConstructor);
        var updatedItemRestDefaultConstructor = getNewItemRest(itemRestDefaultConstructor, uuid);
        assertNotEquals(itemRestFullConstructor, updatedItemRestDefaultConstructor);
        updatedItemRestDefaultConstructor = getNewItemRest(updatedItemRestDefaultConstructor, itemName);
        assertNotEquals(itemRestFullConstructor, updatedItemRestDefaultConstructor);
        updatedItemRestDefaultConstructor = getNewItemRestWithAmount(updatedItemRestDefaultConstructor, amount);
        assertNotEquals(itemRestFullConstructor, updatedItemRestDefaultConstructor);
        updatedItemRestDefaultConstructor = getNewItemRestWithPrice(updatedItemRestDefaultConstructor, price);
        assertEquals(itemRestFullConstructor, updatedItemRestDefaultConstructor);
    }
}