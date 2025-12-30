package com.michal_mm.ois.shippingservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.michal_mm.ois.shippingservice.data.ShippingEntity;
import com.michal_mm.ois.shippingservice.data.ShippingRepository;
import com.michal_mm.ois.shippingservice.exception.DuplicateShipmentException;
import com.michal_mm.ois.shippingservice.exception.ShipmentNotFoundException;
import com.michal_mm.ois.shippingservice.model.CreateShipmentRequest;
import com.michal_mm.ois.shippingservice.model.ShipmentRest;
import com.michal_mm.ois.shippingservice.model.ShippingStatus;

@Service
public class ShippingServiceImpl implements ShippingService {

    private static final Integer MIN_SHIPPING_COST = 500; // $5.00 in cents
    private static final Double SHIPPING_RATE = 0.10; // 10% of order total
    private static final Integer DELIVERY_DAYS = 5;

    private final ShippingRepository shippingRepository;

    public ShippingServiceImpl(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @Override
    public List<ShipmentRest> getAllShipments() {
        return shippingRepository.findAll().stream()
                .map(this::shippingEntityToShipmentRest)
                .toList();
    }

    @Override
    public ShipmentRest getShipmentById(UUID shippingId) {
        ShippingEntity shippingEntity = shippingRepository.findShippingByShippingId(shippingId);

        String msg = "Shipment not found: " + shippingId;
        validateShippingEntityNotNull(shippingEntity, msg);

        return shippingEntityToShipmentRest(shippingEntity);
    }

    private void validateShippingEntityNotNull(ShippingEntity shippingEntity, String message) {
        if (shippingEntity == null) {
            throw new ShipmentNotFoundException(message);
        }
    }

    @Override
    public ShipmentRest getShipmentByOrderId(UUID orderId) {
        ShippingEntity shippingEntity = shippingRepository.findShippingByOrderId(orderId);

        String msg = "Shipment not found for order: " + orderId;
        validateShippingEntityNotNull(shippingEntity, msg);

        return shippingEntityToShipmentRest(shippingEntity);
    }

    @Override
    public ShipmentRest createShipment(CreateShipmentRequest createShipmentRequest) {
        // Check if shipment already exists for this order
        ShippingEntity existingShipment = shippingRepository.findShippingByOrderId(createShipmentRequest.orderId());
        if (existingShipment != null) {
            throw new DuplicateShipmentException("Shipment already exists for order: " + createShipmentRequest.orderId());
        }

        LocalDateTime now = LocalDateTime.now();
        UUID shippingId = UUID.randomUUID();
        Integer shippingCost = calculateShippingCost(createShipmentRequest.orderTotal());
        LocalDate estimatedDelivery = calculateEstimatedDelivery();

        ShippingEntity shippingEntity = new ShippingEntity(
                shippingId,
                createShipmentRequest.orderId(),
                createShipmentRequest.orderTotal(),
                shippingCost,
                estimatedDelivery,
                createShipmentRequest.shippingAddress(),
                ShippingStatus.PENDING,
                now,
                now
        );

        return shippingEntityToShipmentRest(shippingRepository.save(shippingEntity));
    }

    @Override
    public ShipmentRest updateShipmentStatus(UUID shippingId, ShippingStatus status) {
        ShippingEntity shippingEntity = shippingRepository.findShippingByShippingId(shippingId);

        String msg = "Shipment not found: " + shippingId;
        validateShippingEntityNotNull(shippingEntity, msg);

        shippingEntity.setStatus(status);
        shippingEntity.setUpdatedAt(LocalDateTime.now());

        return shippingEntityToShipmentRest(shippingRepository.save(shippingEntity));
    }

    private Integer calculateShippingCost(Integer orderTotal) {
        Integer calculatedCost = (int) Math.round(orderTotal * SHIPPING_RATE);
        return Math.max(calculatedCost, MIN_SHIPPING_COST);
    }

    private LocalDate calculateEstimatedDelivery() {
        // Simple calculation: 5 business days from now
        return LocalDate.now().plusDays(DELIVERY_DAYS);
    }

    private ShipmentRest shippingEntityToShipmentRest(ShippingEntity shippingEntity) {
        Objects.requireNonNull(shippingEntity);

        return new ShipmentRest(
                shippingEntity.getShippingId(),
                shippingEntity.getOrderId(),
                shippingEntity.getOrderTotal(),
                shippingEntity.getShippingCost(),
                shippingEntity.getEstimatedDelivery(),
                shippingEntity.getShippingAddress(),
                shippingEntity.getStatus(),
                shippingEntity.getCreatedAt(),
                shippingEntity.getUpdatedAt()
        );
    }
}