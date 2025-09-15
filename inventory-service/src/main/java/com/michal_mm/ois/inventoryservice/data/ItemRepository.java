package com.michal_mm.ois.inventoryservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {
    ItemEntity findItemByItemId(UUID itemId);
}
