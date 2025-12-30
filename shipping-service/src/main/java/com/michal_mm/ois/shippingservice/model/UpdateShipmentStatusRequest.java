package com.michal_mm.ois.shippingservice.model;

public record UpdateShipmentStatusRequest(
    ShippingStatus status
) {}