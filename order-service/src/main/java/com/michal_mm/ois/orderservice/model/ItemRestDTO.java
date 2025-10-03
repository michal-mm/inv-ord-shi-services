package com.michal_mm.ois.orderservice.model;

import java.util.UUID;

public record ItemRestDTO(UUID itemId, String itemName, Integer amount, Integer price) {
}
