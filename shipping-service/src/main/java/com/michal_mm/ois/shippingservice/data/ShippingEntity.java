package com.michal_mm.ois.shippingservice.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.michal_mm.ois.shippingservice.model.ShippingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="shipments")
@Entity
public class ShippingEntity {

    @Id
    @Column(name="shipping_id")
    private UUID shippingId;

    @Column(name="order_id")
    private UUID orderId;

    @Column(name="order_total")
    private Integer orderTotal;

    @Column(name="shipping_cost")
    private Integer shippingCost;

    @Column(name="estimated_delivery")
    private LocalDate estimatedDelivery;

    @Column(name="shipping_address")
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private ShippingStatus status;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public ShippingEntity() {}

    public ShippingEntity(UUID shippingId, UUID orderId, Integer orderTotal, Integer shippingCost,
                         LocalDate estimatedDelivery, String shippingAddress, ShippingStatus status,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.shippingId = shippingId;
        this.orderId = orderId;
        this.orderTotal = orderTotal;
        this.shippingCost = shippingCost;
        this.estimatedDelivery = estimatedDelivery;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getShippingId() {
        return shippingId;
    }

    public void setShippingId(UUID shippingId) {
        this.shippingId = shippingId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Integer shippingCost) {
        this.shippingCost = shippingCost;
    }

    public LocalDate getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(LocalDate estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ShippingStatus getStatus() {
        return status;
    }

    public void setStatus(ShippingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ShippingEntity{" +
                "shippingId=" + shippingId +
                ", orderId=" + orderId +
                ", orderTotal=" + orderTotal +
                ", shippingCost=" + shippingCost +
                ", estimatedDelivery=" + estimatedDelivery +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}