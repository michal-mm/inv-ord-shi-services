package com.michal_mm.ois.shippingservice.model;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CreateShipmentRequestTest {

    @Test
    void constructor_ShouldCreateRecordWithAllFields() {
        // Given
        UUID orderId = UUID.randomUUID();
        Integer orderTotal = 1000;
        String shippingAddress = "123 Test St, Test City, TC 12345";

        // When
        CreateShipmentRequest request = new CreateShipmentRequest(orderId, orderTotal, shippingAddress);

        // Then
        assertEquals(orderId, request.orderId());
        assertEquals(orderTotal, request.orderTotal());
        assertEquals(shippingAddress, request.shippingAddress());
    }

    @Test
    void constructor_WithNullValues_ShouldAcceptNulls() {
        // When
        CreateShipmentRequest request = new CreateShipmentRequest(null, null, null);

        // Then
        assertNull(request.orderId());
        assertNull(request.orderTotal());
        assertNull(request.shippingAddress());
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        // Given
        UUID orderId = UUID.randomUUID();
        CreateShipmentRequest request1 = new CreateShipmentRequest(orderId, 1000, "Address 1");
        CreateShipmentRequest request2 = new CreateShipmentRequest(orderId, 1000, "Address 1");
        CreateShipmentRequest request3 = new CreateShipmentRequest(orderId, 2000, "Address 1");

        // Then
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertNotEquals(request1, null);
        assertNotEquals(request1, "not a request");
    }

    @Test
    void hashCode_ShouldBeConsistent() {
        // Given
        UUID orderId = UUID.randomUUID();
        CreateShipmentRequest request1 = new CreateShipmentRequest(orderId, 1000, "Address 1");
        CreateShipmentRequest request2 = new CreateShipmentRequest(orderId, 1000, "Address 1");

        // Then
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Given
        UUID orderId = UUID.randomUUID();
        CreateShipmentRequest request = new CreateShipmentRequest(orderId, 1000, "Test Address");

        // When
        String toString = request.toString();

        // Then
        assertTrue(toString.contains(orderId.toString()));
        assertTrue(toString.contains("1000"));
        assertTrue(toString.contains("Test Address"));
    }
}