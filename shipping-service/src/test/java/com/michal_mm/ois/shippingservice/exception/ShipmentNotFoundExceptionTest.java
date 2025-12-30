package com.michal_mm.ois.shippingservice.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ShipmentNotFoundExceptionTest {

    @Test
    void constructor_WithMessage_ShouldSetMessage() {
        // Given
        String expectedMessage = "Shipment not found with ID: 123";

        // When
        ShipmentNotFoundException exception = new ShipmentNotFoundException(expectedMessage);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void constructor_WithNullMessage_ShouldAcceptNull() {
        // When
        ShipmentNotFoundException exception = new ShipmentNotFoundException(null);

        // Then
        assertNull(exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void constructor_WithEmptyMessage_ShouldAcceptEmptyString() {
        // Given
        String emptyMessage = "";

        // When
        ShipmentNotFoundException exception = new ShipmentNotFoundException(emptyMessage);

        // Then
        assertEquals(emptyMessage, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void exception_ShouldBeThrowable() {
        // Given
        ShipmentNotFoundException exception = new ShipmentNotFoundException("Test message");

        // When & Then
        assertThrows(ShipmentNotFoundException.class, () -> {
            throw exception;
        });
    }

    @Test
    void exception_ShouldHaveCorrectInheritance() {
        // Given
        ShipmentNotFoundException exception = new ShipmentNotFoundException("Test message");

        // Then
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }
}