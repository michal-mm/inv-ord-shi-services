package com.michal_mm.ois.shippingservice.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<ShippingEntity, UUID> {
    
    ShippingEntity findShippingByShippingId(UUID shippingId);
    
    ShippingEntity findShippingByOrderId(UUID orderId);
}