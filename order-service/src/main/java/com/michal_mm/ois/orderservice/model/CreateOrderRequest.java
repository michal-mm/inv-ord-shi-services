package com.michal_mm.ois.orderservice.model;

import java.util.UUID;

// TODO - input validation rules missing!
public record CreateOrderRequest(
	UUID itemId,
	String orderName,
	Integer quantity
) {}
