package com.michal_mm.ois.inventoryservice.service;

import com.michal_mm.ois.inventoryservice.model.CreateItemRequest;
import com.michal_mm.ois.inventoryservice.model.ItemRest;

import java.util.List;
import java.util.UUID;


public interface InventoryService {

    List<ItemRest> getAllItems();

    ItemRest getItemById(UUID itemId);

    ItemRest createItem(CreateItemRequest createItemRequest);

    ItemRest updateItem(UUID itemId, Integer amount, Integer price);
}
