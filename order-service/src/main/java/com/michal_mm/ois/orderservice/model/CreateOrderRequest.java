package com.michal_mm.ois.orderservice.model;

import java.util.UUID;

public record CreateOrderRequest(
	UUID itemId,
	String orderName,
	Integer quantity
) {}
