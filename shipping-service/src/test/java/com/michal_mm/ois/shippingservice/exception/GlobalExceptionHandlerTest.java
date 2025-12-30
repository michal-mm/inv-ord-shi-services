package com.michal_mm.ois.shippingservice.exception;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    private ShipmentNotFoundException shipmentNotFoundException;
    private DuplicateShipmentException duplicateShipmentException;
    private RuntimeException genericException;

    @BeforeEach
    void setUp() {
        shipmentNotFoundException = new ShipmentNotFoundException("Shipment not found with ID: 123");
        duplicateShipmentException = new DuplicateShipmentException("Shipment already exists for order: 456");
        genericException = new RuntimeException("Something went wrong");
    }

    @Test
    void handleShipmentNotFoundException_ShouldReturnNotFoundResponse() {
        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleShipmentNotFoundException(shipmentNotFoundException);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        
        Map<String, Object> body = response.getBody();
        assertEquals(404, body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertEquals("Shipment not found with ID: 123", body.get("message"));
        assertNotNull(body.get("timestamp"));
        assertTrue(body.get("timestamp") instanceof LocalDateTime);
    }

    @Test
    void handleShipmentNotFoundException_WithNullMessage_ShouldReturnNotFoundResponse() {
        // Given
        ShipmentNotFoundException exceptionWithNullMessage = new ShipmentNotFoundException(null);

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleShipmentNotFoundException(exceptionWithNullMessage);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        
        Map<String, Object> body = response.getBody();
        assertEquals(404, body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertNull(body.get("message"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void handleDuplicateShipmentException_ShouldReturnConflictResponse() {
        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleDuplicateShipmentException(duplicateShipmentException);

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        
        Map<String, Object> body = response.getBody();
        assertEquals(409, body.get("status"));
        assertEquals("Conflict", body.get("error"));
        assertEquals("Shipment already exists for order: 456", body.get("message"));
        assertNotNull(body.get("timestamp"));
        assertTrue(body.get("timestamp") instanceof LocalDateTime);
    }

    @Test
    void handleDuplicateShipmentException_WithEmptyMessage_ShouldReturnConflictResponse() {
        // Given
        DuplicateShipmentException exceptionWithEmptyMessage = new DuplicateShipmentException("");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleDuplicateShipmentException(exceptionWithEmptyMessage);

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        
        Map<String, Object> body = response.getBody();
        assertEquals(409, body.get("status"));
        assertEquals("Conflict", body.get("error"));
        assertEquals("", body.get("message"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerErrorResponse() {
        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleGenericException(genericException);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        
        Map<String, Object> body = response.getBody();
        assertEquals(500, body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertEquals("An unexpected error occurred", body.get("message"));
        assertNotNull(body.get("timestamp"));
        assertTrue(body.get("timestamp") instanceof LocalDateTime);
    }

    @Test
    void handleGenericException_WithNullPointerException_ShouldReturnInternalServerErrorResponse() {
        // Given
        NullPointerException nullPointerException = new NullPointerException("Null pointer encountered");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleGenericException(nullPointerException);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        
        Map<String, Object> body = response.getBody();
        assertEquals(500, body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertEquals("An unexpected error occurred", body.get("message"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void handleGenericException_WithIllegalArgumentException_ShouldReturnInternalServerErrorResponse() {
        // Given
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Invalid argument provided");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleGenericException(illegalArgumentException);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        
        Map<String, Object> body = response.getBody();
        assertEquals(500, body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertEquals("An unexpected error occurred", body.get("message"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void allExceptionHandlers_ShouldHaveConsistentResponseStructure() {
        // When
        ResponseEntity<Map<String, Object>> notFoundResponse = globalExceptionHandler.handleShipmentNotFoundException(shipmentNotFoundException);
        ResponseEntity<Map<String, Object>> conflictResponse = globalExceptionHandler.handleDuplicateShipmentException(duplicateShipmentException);
        ResponseEntity<Map<String, Object>> serverErrorResponse = globalExceptionHandler.handleGenericException(genericException);

        // Then - All responses should have the same structure
        assertResponseStructure(notFoundResponse.getBody());
        assertResponseStructure(conflictResponse.getBody());
        assertResponseStructure(serverErrorResponse.getBody());
    }

    @Test
    void exceptionHandlers_ShouldHaveUniqueTimestamps() throws InterruptedException {
        // When - Call handlers with small delay to ensure different timestamps
        ResponseEntity<Map<String, Object>> response1 = globalExceptionHandler.handleShipmentNotFoundException(shipmentNotFoundException);
        Thread.sleep(1); // Ensure different millisecond
        ResponseEntity<Map<String, Object>> response2 = globalExceptionHandler.handleDuplicateShipmentException(duplicateShipmentException);

        // Then
        LocalDateTime timestamp1 = (LocalDateTime) response1.getBody().get("timestamp");
        LocalDateTime timestamp2 = (LocalDateTime) response2.getBody().get("timestamp");
        
        assertNotEquals(timestamp1, timestamp2);
    }

    private void assertResponseStructure(Map<String, Object> responseBody) {
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("timestamp"));
        assertTrue(responseBody.containsKey("status"));
        assertTrue(responseBody.containsKey("error"));
        assertTrue(responseBody.containsKey("message"));
        
        // Verify types
        assertTrue(responseBody.get("timestamp") instanceof LocalDateTime);
        assertTrue(responseBody.get("status") instanceof Integer);
        assertTrue(responseBody.get("error") instanceof String);
        // message can be String or null
    }
}