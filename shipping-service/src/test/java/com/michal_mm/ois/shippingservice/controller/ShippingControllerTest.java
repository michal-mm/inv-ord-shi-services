package com.michal_mm.ois.shippingservice.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.michal_mm.ois.shippingservice.exception.DuplicateShipmentException;
import com.michal_mm.ois.shippingservice.exception.ShipmentNotFoundException;
import com.michal_mm.ois.shippingservice.model.CreateShipmentRequest;
import com.michal_mm.ois.shippingservice.model.ShipmentRest;
import com.michal_mm.ois.shippingservice.model.ShippingStatus;
import com.michal_mm.ois.shippingservice.model.UpdateShipmentStatusRequest;
import com.michal_mm.ois.shippingservice.service.ShippingService;

@ExtendWith(MockitoExtension.class)
class ShippingControllerTest {

    @Mock
    private ShippingService shippingService;

    @InjectMocks
    private ShippingController shippingController;

    private UUID testShippingId;
    private UUID testOrderId;
    private ShipmentRest testShipmentRest;
    private CreateShipmentRequest testCreateRequest;

    @BeforeEach
    void setUp() {
        testShippingId = UUID.randomUUID();
        testOrderId = UUID.randomUUID();
        
        testShipmentRest = new ShipmentRest(
                testShippingId,
                testOrderId,
                1000,
                500,
                LocalDate.now().plusDays(5),
                "123 Test St, Test City, TC 12345",
                ShippingStatus.PENDING,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        testCreateRequest = new CreateShipmentRequest(
                testOrderId,
                1000,
                "123 Test St, Test City, TC 12345"
        );
    }

    @Test
    void getAllShipments_ShouldReturnListOfShipments() {
        // Given
        List<ShipmentRest> shipments = Arrays.asList(testShipmentRest);
        when(shippingService.getAllShipments()).thenReturn(shipments);

        // When
        List<ShipmentRest> result = shippingController.getAllShipments();

        // Then
        assertEquals(1, result.size());
        assertEquals(testShippingId, result.get(0).shippingId());
        assertEquals(testOrderId, result.get(0).orderId());
        assertEquals(1000, result.get(0).orderTotal());
        assertEquals(500, result.get(0).shippingCost());
        assertEquals(ShippingStatus.PENDING, result.get(0).status());
    }

    @Test
    void getShipmentById_WhenExists_ShouldReturnShipment() {
        // Given
        when(shippingService.getShipmentById(testShippingId)).thenReturn(testShipmentRest);

        // When
        ShipmentRest result = shippingController.getShipmentById(testShippingId);

        // Then
        assertEquals(testShippingId, result.shippingId());
        assertEquals(testOrderId, result.orderId());
        assertEquals(1000, result.orderTotal());
        assertEquals(500, result.shippingCost());
        assertEquals(ShippingStatus.PENDING, result.status());
    }

    @Test
    void getShipmentById_WhenNotExists_ShouldThrowException() {
        // Given
        when(shippingService.getShipmentById(testShippingId))
                .thenThrow(new ShipmentNotFoundException("Shipment not found: " + testShippingId));

        // When & Then
        assertThrows(ShipmentNotFoundException.class, 
                () -> shippingController.getShipmentById(testShippingId));
    }

    @Test
    void getShipmentByOrderId_WhenExists_ShouldReturnShipment() {
        // Given
        when(shippingService.getShipmentByOrderId(testOrderId)).thenReturn(testShipmentRest);

        // When
        ShipmentRest result = shippingController.getShipmentByOrderId(testOrderId);

        // Then
        assertEquals(testShippingId, result.shippingId());
        assertEquals(testOrderId, result.orderId());
    }

    @Test
    void getShipmentByOrderId_WhenNotExists_ShouldThrowException() {
        // Given
        when(shippingService.getShipmentByOrderId(testOrderId))
                .thenThrow(new ShipmentNotFoundException("Shipment not found for order: " + testOrderId));

        // When & Then
        assertThrows(ShipmentNotFoundException.class, 
                () -> shippingController.getShipmentByOrderId(testOrderId));
    }

    @Test
    void createShipment_WithValidRequest_ShouldReturnCreatedShipment() {
        // Given
        when(shippingService.createShipment(any(CreateShipmentRequest.class))).thenReturn(testShipmentRest);

        // When
        ShipmentRest result = shippingController.createShipment(testCreateRequest);

        // Then
        assertEquals(testShippingId, result.shippingId());
        assertEquals(testOrderId, result.orderId());
        assertEquals(1000, result.orderTotal());
        assertEquals(500, result.shippingCost());
        assertEquals(ShippingStatus.PENDING, result.status());
    }

    @Test
    void createShipment_WhenDuplicateOrder_ShouldThrowException() {
        // Given
        when(shippingService.createShipment(any(CreateShipmentRequest.class)))
                .thenThrow(new DuplicateShipmentException("Shipment already exists for order: " + testOrderId));

        // When & Then
        assertThrows(DuplicateShipmentException.class, 
                () -> shippingController.createShipment(testCreateRequest));
    }

    @Test
    void updateShipmentStatus_WithValidRequest_ShouldReturnUpdatedShipment() {
        // Given
        ShipmentRest updatedShipment = new ShipmentRest(
                testShippingId,
                testOrderId,
                1000,
                500,
                LocalDate.now().plusDays(5),
                "123 Test St, Test City, TC 12345",
                ShippingStatus.SHIPPED,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        
        UpdateShipmentStatusRequest updateRequest = new UpdateShipmentStatusRequest(ShippingStatus.SHIPPED);
        when(shippingService.updateShipmentStatus(eq(testShippingId), eq(ShippingStatus.SHIPPED)))
                .thenReturn(updatedShipment);

        // When
        ShipmentRest result = shippingController.updateShipmentStatus(testShippingId, updateRequest);

        // Then
        assertEquals(testShippingId, result.shippingId());
        assertEquals(ShippingStatus.SHIPPED, result.status());
    }

    @Test
    void updateShipmentStatus_WhenShipmentNotExists_ShouldThrowException() {
        // Given
        UpdateShipmentStatusRequest updateRequest = new UpdateShipmentStatusRequest(ShippingStatus.SHIPPED);
        when(shippingService.updateShipmentStatus(eq(testShippingId), eq(ShippingStatus.SHIPPED)))
                .thenThrow(new ShipmentNotFoundException("Shipment not found: " + testShippingId));

        // When & Then
        assertThrows(ShipmentNotFoundException.class, 
                () -> shippingController.updateShipmentStatus(testShippingId, updateRequest));
    }
}