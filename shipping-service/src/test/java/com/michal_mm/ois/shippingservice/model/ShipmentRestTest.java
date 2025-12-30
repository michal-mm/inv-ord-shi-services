package com.michal_mm.ois.shippingservice.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ShipmentRestTest {

    @Test
    void constructor_ShouldCreateRecordWithAllFields() {
        // Given
        UUID shippingId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Integer orderTotal = 1000;
        Integer shippingCost = 500;
        LocalDate estimatedDelivery = LocalDate.now().plusDays(5);
        String shippingAddress = "123 Test St, Test City, TC 12345";
        ShippingStatus status = ShippingStatus.PENDING;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // When
        ShipmentRest shipment = new ShipmentRest(
                shippingId, orderId, orderTotal, shippingCost,
                estimatedDelivery, shippingAddress, status, createdAt, updatedAt
        );

        // Then
        assertEquals(shippingId, shipment.shippingId());
        assertEquals(orderId, shipment.orderId());
        assertEquals(orderTotal, shipment.orderTotal());
        assertEquals(shippingCost, shipment.shippingCost());
        assertEquals(estimatedDelivery, shipment.estimatedDelivery());
        assertEquals(shippingAddress, shipment.shippingAddress());
        assertEquals(status, shipment.status());
        assertEquals(createdAt, shipment.createdAt());
        assertEquals(updatedAt, shipment.updatedAt());
    }

    @Test
    void constructor_WithNullValues_ShouldAcceptNulls() {
        // When
        ShipmentRest shipment = new ShipmentRest(
                null, null, null, null, null, null, null, null, null
        );

        // Then
        assertNull(shipment.shippingId());
        assertNull(shipment.orderId());
        assertNull(shipment.orderTotal());
        assertNull(shipment.shippingCost());
        assertNull(shipment.estimatedDelivery());
        assertNull(shipment.shippingAddress());
        assertNull(shipment.status());
        assertNull(shipment.createdAt());
        assertNull(shipment.updatedAt());
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        // Given
        UUID shippingId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        LocalDate estimatedDelivery = LocalDate.now().plusDays(5);
        LocalDateTime timestamp = LocalDateTime.now();

        ShipmentRest shipment1 = new ShipmentRest(
                shippingId, orderId, 1000, 500, estimatedDelivery,
                "Address 1", ShippingStatus.PENDING, timestamp, timestamp
        );
        ShipmentRest shipment2 = new ShipmentRest(
                shippingId, orderId, 1000, 500, estimatedDelivery,
                "Address 1", ShippingStatus.PENDING, timestamp, timestamp
        );
        ShipmentRest shipment3 = new ShipmentRest(
                shippingId, orderId, 2000, 500, estimatedDelivery,
                "Address 1", ShippingStatus.PENDING, timestamp, timestamp
        );

        // Then
        assertEquals(shipment1, shipment2);
        assertNotEquals(shipment1, shipment3);
        assertNotEquals(shipment1, null);
        assertNotEquals(shipment1, "not a shipment");
    }

    @Test
    void hashCode_ShouldBeConsistent() {
        // Given
        UUID shippingId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        LocalDate estimatedDelivery = LocalDate.now().plusDays(5);
        LocalDateTime timestamp = LocalDateTime.now();

        ShipmentRest shipment1 = new ShipmentRest(
                shippingId, orderId, 1000, 500, estimatedDelivery,
                "Address 1", ShippingStatus.PENDING, timestamp, timestamp
        );
        ShipmentRest shipment2 = new ShipmentRest(
                shippingId, orderId, 1000, 500, estimatedDelivery,
                "Address 1", ShippingStatus.PENDING, timestamp, timestamp
        );

        // Then
        assertEquals(shipment1.hashCode(), shipment2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        UUID shippingId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        ShipmentRest shipment = new ShipmentRest(
                shippingId, orderId, 1000, 500, LocalDate.now().plusDays(5),
                "Test Address", ShippingStatus.PENDING, LocalDateTime.now(), LocalDateTime.now()
        );

        // When
        String toString = shipment.toString();

        // Then
        assertTrue(toString.contains(shippingId.toString()));
        assertTrue(toString.contains(orderId.toString()));
        assertTrue(toString.contains("1000"));
        assertTrue(toString.contains("500"));
        assertTrue(toString.contains("Test Address"));
        assertTrue(toString.contains("PENDING"));
    }
}