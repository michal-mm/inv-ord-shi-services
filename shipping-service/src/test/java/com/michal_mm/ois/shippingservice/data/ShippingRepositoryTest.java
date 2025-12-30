package com.michal_mm.ois.shippingservice.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.michal_mm.ois.shippingservice.model.ShippingStatus;

class ShippingRepositoryTest {

    private UUID testShippingId;
    private UUID testOrderId;
    private ShippingEntity testShippingEntity;

    @BeforeEach
    void setUp() {
        testShippingId = UUID.randomUUID();
        testOrderId = UUID.randomUUID();
        
        testShippingEntity = new ShippingEntity(
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
    }

    @Test
    void shippingEntity_ShouldHaveCorrectProperties() {
        // Test that the entity is properly constructed
        assertEquals(testShippingId, testShippingEntity.getShippingId());
        assertEquals(testOrderId, testShippingEntity.getOrderId());
        assertEquals(1000, testShippingEntity.getOrderTotal());
        assertEquals(500, testShippingEntity.getShippingCost());
        assertEquals(ShippingStatus.PENDING, testShippingEntity.getStatus());
        assertNotNull(testShippingEntity.getEstimatedDelivery());
        assertNotNull(testShippingEntity.getShippingAddress());
        assertNotNull(testShippingEntity.getCreatedAt());
        assertNotNull(testShippingEntity.getUpdatedAt());
    }

    @Test
    void shippingEntity_SettersAndGetters_ShouldWork() {
        // Test setters
        UUID newShippingId = UUID.randomUUID();
        UUID newOrderId = UUID.randomUUID();
        
        testShippingEntity.setShippingId(newShippingId);
        testShippingEntity.setOrderId(newOrderId);
        testShippingEntity.setOrderTotal(2000);
        testShippingEntity.setShippingCost(600);
        testShippingEntity.setStatus(ShippingStatus.SHIPPED);
        testShippingEntity.setShippingAddress("456 New St");
        
        LocalDate newDeliveryDate = LocalDate.now().plusDays(3);
        LocalDateTime newDateTime = LocalDateTime.now();
        testShippingEntity.setEstimatedDelivery(newDeliveryDate);
        testShippingEntity.setUpdatedAt(newDateTime);
        
        // Test getters
        assertEquals(newShippingId, testShippingEntity.getShippingId());
        assertEquals(newOrderId, testShippingEntity.getOrderId());
        assertEquals(2000, testShippingEntity.getOrderTotal());
        assertEquals(600, testShippingEntity.getShippingCost());
        assertEquals(ShippingStatus.SHIPPED, testShippingEntity.getStatus());
        assertEquals("456 New St", testShippingEntity.getShippingAddress());
        assertEquals(newDeliveryDate, testShippingEntity.getEstimatedDelivery());
        assertEquals(newDateTime, testShippingEntity.getUpdatedAt());
    }

    @Test
    void shippingEntity_ToString_ShouldContainAllFields() {
        String toString = testShippingEntity.toString();
        
        assertTrue(toString.contains(testShippingId.toString()));
        assertTrue(toString.contains(testOrderId.toString()));
        assertTrue(toString.contains("1000"));
        assertTrue(toString.contains("500"));
        assertTrue(toString.contains("PENDING"));
        assertTrue(toString.contains("123 Test St"));
    }
}