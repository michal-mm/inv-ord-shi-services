package com.michal_mm.ois.shippingservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.michal_mm.ois.shippingservice.data.ShippingEntity;
import com.michal_mm.ois.shippingservice.data.ShippingRepository;
import com.michal_mm.ois.shippingservice.exception.DuplicateShipmentException;
import com.michal_mm.ois.shippingservice.exception.ShipmentNotFoundException;
import com.michal_mm.ois.shippingservice.model.CreateShipmentRequest;
import com.michal_mm.ois.shippingservice.model.ShipmentRest;
import com.michal_mm.ois.shippingservice.model.ShippingStatus;

@ExtendWith(MockitoExtension.class)
class ShippingServiceImplTest {

    @Mock
    private ShippingRepository shippingRepository;

    @InjectMocks
    private ShippingServiceImpl shippingService;

    private UUID testShippingId;
    private UUID testOrderId;
    private ShippingEntity testShippingEntity;
    private CreateShipmentRequest testCreateRequest;

    @BeforeEach
    void setUp() {
        testShippingId = UUID.randomUUID();
        testOrderId = UUID.randomUUID();
        
        testShippingEntity = new ShippingEntity(
                testShippingId,
                testOrderId,
                1000, // $10.00
                500,  // $5.00 shipping
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
    void getAllShipments_ShouldReturnAllShipments() {
        // Given
        List<ShippingEntity> entities = Arrays.asList(testShippingEntity);
        when(shippingRepository.findAll()).thenReturn(entities);

        // When
        List<ShipmentRest> result = shippingService.getAllShipments();

        // Then
        assertEquals(1, result.size());
        assertEquals(testShippingId, result.get(0).shippingId());
        assertEquals(testOrderId, result.get(0).orderId());
        verify(shippingRepository).findAll();
    }

    @Test
    void getShipmentById_WhenExists_ShouldReturnShipment() {
        // Given
        when(shippingRepository.findShippingByShippingId(testShippingId)).thenReturn(testShippingEntity);

        // When
        ShipmentRest result = shippingService.getShipmentById(testShippingId);

        // Then
        assertEquals(testShippingId, result.shippingId());
        assertEquals(testOrderId, result.orderId());
        assertEquals(1000, result.orderTotal());
        assertEquals(500, result.shippingCost());
        verify(shippingRepository).findShippingByShippingId(testShippingId);
    }

    @Test
    void getShipmentById_WhenNotExists_ShouldThrowException() {
        // Given
        when(shippingRepository.findShippingByShippingId(testShippingId)).thenReturn(null);

        // When & Then
        assertThrows(ShipmentNotFoundException.class, 
                () -> shippingService.getShipmentById(testShippingId));
        verify(shippingRepository).findShippingByShippingId(testShippingId);
    }

    @Test
    void getShipmentByOrderId_WhenExists_ShouldReturnShipment() {
        // Given
        when(shippingRepository.findShippingByOrderId(testOrderId)).thenReturn(testShippingEntity);

        // When
        ShipmentRest result = shippingService.getShipmentByOrderId(testOrderId);

        // Then
        assertEquals(testShippingId, result.shippingId());
        assertEquals(testOrderId, result.orderId());
        verify(shippingRepository).findShippingByOrderId(testOrderId);
    }

    @Test
    void getShipmentByOrderId_WhenNotExists_ShouldThrowException() {
        // Given
        when(shippingRepository.findShippingByOrderId(testOrderId)).thenReturn(null);

        // When & Then
        assertThrows(ShipmentNotFoundException.class, 
                () -> shippingService.getShipmentByOrderId(testOrderId));
        verify(shippingRepository).findShippingByOrderId(testOrderId);
    }

    @Test
    void createShipment_WhenOrderDoesNotExist_ShouldCreateSuccessfully() {
        // Given
        when(shippingRepository.findShippingByOrderId(testOrderId)).thenReturn(null);
        when(shippingRepository.save(any(ShippingEntity.class))).thenReturn(testShippingEntity);

        // When
        ShipmentRest result = shippingService.createShipment(testCreateRequest);

        // Then
        assertEquals(testShippingId, result.shippingId());
        assertEquals(testOrderId, result.orderId());
        assertEquals(1000, result.orderTotal());
        assertEquals(ShippingStatus.PENDING, result.status());
        verify(shippingRepository).findShippingByOrderId(testOrderId);
        verify(shippingRepository).save(any(ShippingEntity.class));
    }

    @Test
    void createShipment_WhenOrderAlreadyExists_ShouldThrowException() {
        // Given
        when(shippingRepository.findShippingByOrderId(testOrderId)).thenReturn(testShippingEntity);

        // When & Then
        assertThrows(DuplicateShipmentException.class, 
                () -> shippingService.createShipment(testCreateRequest));
        verify(shippingRepository).findShippingByOrderId(testOrderId);
        verify(shippingRepository, never()).save(any());
    }

    @Test
    void createShipment_ShouldCalculateShippingCostCorrectly() {
        // Given - order total of $50.00 (5000 cents)
        CreateShipmentRequest largeOrderRequest = new CreateShipmentRequest(
                testOrderId, 5000, "123 Test St"
        );
        when(shippingRepository.findShippingByOrderId(testOrderId)).thenReturn(null);
        when(shippingRepository.save(any(ShippingEntity.class))).thenAnswer(invocation -> {
            ShippingEntity entity = invocation.getArgument(0);
            return new ShippingEntity(
                    entity.getShippingId(),
                    entity.getOrderId(),
                    entity.getOrderTotal(),
                    entity.getShippingCost(),
                    entity.getEstimatedDelivery(),
                    entity.getShippingAddress(),
                    entity.getStatus(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
            );
        });

        // When
        ShipmentRest result = shippingService.createShipment(largeOrderRequest);

        // Then - should be 10% of 5000 = 500 cents ($5.00)
        assertEquals(500, result.shippingCost());
    }

    @Test
    void createShipment_ShouldApplyMinimumShippingCost() {
        // Given - small order total of $1.00 (100 cents)
        CreateShipmentRequest smallOrderRequest = new CreateShipmentRequest(
                testOrderId, 100, "123 Test St"
        );
        when(shippingRepository.findShippingByOrderId(testOrderId)).thenReturn(null);
        when(shippingRepository.save(any(ShippingEntity.class))).thenAnswer(invocation -> {
            ShippingEntity entity = invocation.getArgument(0);
            return new ShippingEntity(
                    entity.getShippingId(),
                    entity.getOrderId(),
                    entity.getOrderTotal(),
                    entity.getShippingCost(),
                    entity.getEstimatedDelivery(),
                    entity.getShippingAddress(),
                    entity.getStatus(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
            );
        });

        // When
        ShipmentRest result = shippingService.createShipment(smallOrderRequest);

        // Then - should be minimum $5.00 (500 cents), not 10% of 100 = 10 cents
        assertEquals(500, result.shippingCost());
    }

    @Test
    void updateShipmentStatus_WhenExists_ShouldUpdateSuccessfully() {
        // Given
        when(shippingRepository.findShippingByShippingId(testShippingId)).thenReturn(testShippingEntity);
        when(shippingRepository.save(any(ShippingEntity.class))).thenReturn(testShippingEntity);

        // When
        ShipmentRest result = shippingService.updateShipmentStatus(testShippingId, ShippingStatus.SHIPPED);

        // Then
        verify(shippingRepository).findShippingByShippingId(testShippingId);
        verify(shippingRepository).save(any(ShippingEntity.class));
        assertNotNull(result);
    }

    @Test
    void updateShipmentStatus_WhenNotExists_ShouldThrowException() {
        // Given
        when(shippingRepository.findShippingByShippingId(testShippingId)).thenReturn(null);

        // When & Then
        assertThrows(ShipmentNotFoundException.class, 
                () -> shippingService.updateShipmentStatus(testShippingId, ShippingStatus.SHIPPED));
        verify(shippingRepository).findShippingByShippingId(testShippingId);
        verify(shippingRepository, never()).save(any());
    }
}