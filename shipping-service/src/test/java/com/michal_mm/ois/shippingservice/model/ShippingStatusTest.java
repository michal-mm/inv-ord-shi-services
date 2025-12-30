package com.michal_mm.ois.shippingservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ShippingStatusTest {

    @Test
    void enum_ShouldHaveAllExpectedValues() {
        // When
        ShippingStatus[] values = ShippingStatus.values();

        // Then
        assertEquals(4, values.length);
        assertEquals(ShippingStatus.PENDING, values[0]);
        assertEquals(ShippingStatus.PROCESSING, values[1]);
        assertEquals(ShippingStatus.SHIPPED, values[2]);
        assertEquals(ShippingStatus.DELIVERED, values[3]);
    }

    @Test
    void valueOf_ShouldReturnCorrectEnumValues() {
        // When & Then
        assertEquals(ShippingStatus.PENDING, ShippingStatus.valueOf("PENDING"));
        assertEquals(ShippingStatus.PROCESSING, ShippingStatus.valueOf("PROCESSING"));
        assertEquals(ShippingStatus.SHIPPED, ShippingStatus.valueOf("SHIPPED"));
        assertEquals(ShippingStatus.DELIVERED, ShippingStatus.valueOf("DELIVERED"));
    }

    @Test
    void valueOf_WithInvalidValue_ShouldThrowException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            ShippingStatus.valueOf("INVALID_STATUS");
        });
    }

    @Test
    void valueOf_WithNullValue_ShouldThrowException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            ShippingStatus.valueOf(null);
        });
    }

    @Test
    void toString_ShouldReturnEnumName() {
        // When & Then
        assertEquals("PENDING", ShippingStatus.PENDING.toString());
        assertEquals("PROCESSING", ShippingStatus.PROCESSING.toString());
        assertEquals("SHIPPED", ShippingStatus.SHIPPED.toString());
        assertEquals("DELIVERED", ShippingStatus.DELIVERED.toString());
    }

    @Test
    void name_ShouldReturnEnumName() {
        // When & Then
        assertEquals("PENDING", ShippingStatus.PENDING.name());
        assertEquals("PROCESSING", ShippingStatus.PROCESSING.name());
        assertEquals("SHIPPED", ShippingStatus.SHIPPED.name());
        assertEquals("DELIVERED", ShippingStatus.DELIVERED.name());
    }

    @Test
    void ordinal_ShouldReturnCorrectOrder() {
        // When & Then
        assertEquals(0, ShippingStatus.PENDING.ordinal());
        assertEquals(1, ShippingStatus.PROCESSING.ordinal());
        assertEquals(2, ShippingStatus.SHIPPED.ordinal());
        assertEquals(3, ShippingStatus.DELIVERED.ordinal());
    }

    @Test
    void compareTo_ShouldCompareByOrdinal() {
        // When & Then
        assertTrue(ShippingStatus.PENDING.compareTo(ShippingStatus.PROCESSING) < 0);
        assertTrue(ShippingStatus.PROCESSING.compareTo(ShippingStatus.SHIPPED) < 0);
        assertTrue(ShippingStatus.SHIPPED.compareTo(ShippingStatus.DELIVERED) < 0);
        assertTrue(ShippingStatus.DELIVERED.compareTo(ShippingStatus.PENDING) > 0);
        assertEquals(0, ShippingStatus.PENDING.compareTo(ShippingStatus.PENDING));
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        // When & Then
        assertEquals(ShippingStatus.PENDING, ShippingStatus.PENDING);
        assertNotEquals(ShippingStatus.PENDING, ShippingStatus.PROCESSING);
        assertNotEquals(ShippingStatus.PENDING, null);
        assertNotEquals(ShippingStatus.PENDING, "PENDING");
    }

    @Test
    void hashCode_ShouldBeConsistent() {
        // When & Then
        assertEquals(ShippingStatus.PENDING.hashCode(), ShippingStatus.PENDING.hashCode());
        assertNotEquals(ShippingStatus.PENDING.hashCode(), ShippingStatus.PROCESSING.hashCode());
    }
}