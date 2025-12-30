package com.michal_mm.ois.shippingservice.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DuplicateShipmentExceptionTest {

    @Test
    void constructor_WithMessage_ShouldSetMessage() {
        // Given
        String expectedMessage = "Shipment already exists for order: 456";

        // When
        DuplicateShipmentException exception = new DuplicateShipmentException(expectedMessage);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void constructor_WithNullMessage_ShouldAcceptNull() {
        // When
        DuplicateShipmentException exception = new DuplicateShipmentException(null);

        // Then
        assertNull(exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void constructor_WithEmptyMessage_ShouldAcceptEmptyString() {
        // Given
        String emptyMessage = "";

        // When
        DuplicateShipmentException exception = new DuplicateShipmentException(emptyMessage);

        // Then
        assertEquals(emptyMessage, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void exception_ShouldBeThrowable() {
        // Given
        DuplicateShipmentException exception = new DuplicateShipmentException("Test message");

        // When & Then
        assertThrows(DuplicateShipmentException.class, () -> {
            throw exception;
        });
    }

    @Test
    void exception_ShouldHaveCorrectInheritance() {
        // Given
        DuplicateShipmentException exception = new DuplicateShipmentException("Test message");

        // Then
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    void exception_ShouldBeDifferentFromShipmentNotFoundException() {
        // Given
        DuplicateShipmentException duplicateException = new DuplicateShipmentException("Duplicate");
        ShipmentNotFoundException notFoundException = new ShipmentNotFoundException("Not found");

        // Then
        assertNotEquals(duplicateException.getClass(), notFoundException.getClass());
        // Both exceptions are RuntimeExceptions but different types
        assertTrue(duplicateException instanceof RuntimeException);
        assertTrue(notFoundException instanceof RuntimeException);
        assertNotEquals(duplicateException.getClass().getSimpleName(), notFoundException.getClass().getSimpleName());
    }
}