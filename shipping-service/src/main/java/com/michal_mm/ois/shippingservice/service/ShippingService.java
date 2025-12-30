package com.michal_mm.ois.shippingservice.service;

import java.util.List;
import java.util.UUID;

import com.michal_mm.ois.shippingservice.model.CreateShipmentRequest;
import com.michal_mm.ois.shippingservice.model.ShipmentRest;
import com.michal_mm.ois.shippingservice.model.ShippingStatus;

public interface ShippingService {
    
    List<ShipmentRest> getAllShipments();
    
    ShipmentRest getShipmentById(UUID shippingId);
    
    ShipmentRest getShipmentByOrderId(UUID orderId);
    
    ShipmentRest createShipment(CreateShipmentRequest createShipmentRequest);
    
    ShipmentRest updateShipmentStatus(UUID shippingId, ShippingStatus status);
}