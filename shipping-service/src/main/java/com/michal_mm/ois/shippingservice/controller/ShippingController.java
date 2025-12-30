package com.michal_mm.ois.shippingservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.michal_mm.ois.shippingservice.model.CreateShipmentRequest;
import com.michal_mm.ois.shippingservice.model.ShipmentRest;
import com.michal_mm.ois.shippingservice.model.UpdateShipmentStatusRequest;
import com.michal_mm.ois.shippingservice.service.ShippingService;

@RestController
@RequestMapping("/shipments")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping
    public List<ShipmentRest> getAllShipments() {
        return shippingService.getAllShipments();
    }

    @GetMapping("/{shippingId}")
    public ShipmentRest getShipmentById(@PathVariable UUID shippingId) {
        return shippingService.getShipmentById(shippingId);
    }

    @GetMapping("/order/{orderId}")
    public ShipmentRest getShipmentByOrderId(@PathVariable UUID orderId) {
        return shippingService.getShipmentByOrderId(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShipmentRest createShipment(@RequestBody CreateShipmentRequest createShipmentRequest) {
        return shippingService.createShipment(createShipmentRequest);
    }

    @PatchMapping("/{shippingId}/status")
    public ShipmentRest updateShipmentStatus(@PathVariable UUID shippingId,
                                           @RequestBody UpdateShipmentStatusRequest updateRequest) {
        return shippingService.updateShipmentStatus(shippingId, updateRequest.status());
    }
}