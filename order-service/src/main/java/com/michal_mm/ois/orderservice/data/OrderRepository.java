package com.michal_mm.ois.orderservice.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID>{

	OrderEntity findOrderById(UUID orderId);
	
}
