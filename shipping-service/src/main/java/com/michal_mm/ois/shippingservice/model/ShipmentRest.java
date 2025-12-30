package com.michal_mm.ois.shippingservice.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ShipmentRest(
    UUID shippingId,
    UUID orderId,
    Integer orderTotal,
    Integer shippingCost,
    LocalDate estimatedDelivery,
    String shippingAddress,
    ShippingStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}