package com.michal_mm.ois.shippingservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class UpdateShipmentStatusRequestTest {

    @Test
    void constructor_ShouldCreateRecordWithStatus() {
        // Given
        ShippingStatus status = ShippingStatus.SHIPPED;

        // When
        UpdateShipmentStatusRequest request = new UpdateShipmentStatusRequest(status);

        // Then
        assertEquals(status, request.status());
    }

    @Test
    void constructor_WithNullStatus_ShouldAcceptNull() {
        // When
        UpdateShipmentStatusRequest request = new UpdateShipmentStatusRequest(null);

        // Then
        assertNull(request.status());
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        // Given
        UpdateShipmentStatusRequest request1 = new UpdateShipmentStatusRequest(ShippingStatus.SHIPPED);
        UpdateShipmentStatusRequest request2 = new UpdateShipmentStatusRequest(ShippingStatus.SHIPPED);
        UpdateShipmentStatusRequest request3 = new UpdateShipmentStatusRequest(ShippingStatus.DELIVERED);

        // Then
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertNotEquals(request1, null);
        assertNotEquals(request1, "not a request");
    }

    @Test
    void hashCode_ShouldBeConsistent() {
        // Given
        UpdateShipmentStatusRequest request1 = new UpdateShipmentStatusRequest(ShippingStatus.SHIPPED);
        UpdateShipmentStatusRequest request2 = new UpdateShipmentStatusRequest(ShippingStatus.SHIPPED);

        // Then
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void toString_ShouldContainStatus() {
        // Given
        UpdateShipmentStatusRequest request = new UpdateShipmentStatusRequest(ShippingStatus.PROCESSING);

        // When
        String toString = request.toString();

        // Then
        assertTrue(toString.contains("PROCESSING"));
    }

    @Test
    void allShippingStatuses_ShouldWorkInRequest() {
        // Test all enum values
        for (ShippingStatus status : ShippingStatus.values()) {
            UpdateShipmentStatusRequest request = new UpdateShipmentStatusRequest(status);
            assertEquals(status, request.status());
        }
    }
}