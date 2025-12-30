package com.michal_mm.ois.shippingservice.model;

import java.util.UUID;

public record CreateShipmentRequest(
    UUID orderId,
    Integer orderTotal,
    String shippingAddress
) {}