package com.michal_mm.ois.inventoryservice.service;

import com.michal_mm.ois.inventoryservice.data.ItemEntity;
import com.michal_mm.ois.inventoryservice.data.ItemRepository;
import com.michal_mm.ois.inventoryservice.model.CreateItemRequest;
import com.michal_mm.ois.inventoryservice.model.ItemRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ItemRepository repository;

    public InventoryServiceImpl(){}

    public InventoryServiceImpl(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ItemRest> getAllItems() {
        return repository.findAll().stream()
                .map(this::itemEntity2ItemRest)
                .toList();
    }

    @Override
    public ItemRest getItemById(UUID itemId) {
        ItemEntity itemEntity = repository.findItemByItemId(itemId);
        if (itemEntity == null) {
           System.out.println("Item not found: " + itemId.toString());
           return null;
        }

        return itemEntity2ItemRest(itemEntity);
    }

    @Override
    public ItemRest createItem(CreateItemRequest createItemRequest) {
        return itemEntity2ItemRest(repository.save(createItemRequest2ItemEntity(createItemRequest)));
    }

    @Override
    public ItemRest updateItem(UUID itemId, Optional<Integer> amount, Optional<Integer> price) {
        ItemEntity itemEntity2Update = repository.findItemByItemId(itemId);
        if (itemEntity2Update == null) {
            return null;
        }

        amount.ifPresent(itemEntity2Update::setAmount);
        price.ifPresent(itemEntity2Update::setPrice);

        return itemEntity2ItemRest(repository.save(itemEntity2Update));
    }

    private ItemRest itemEntity2ItemRest(ItemEntity itemEntity) {
        if (itemEntity == null) {
            return null;
        }

        return new ItemRest(itemEntity.getItemId(),
                itemEntity.getItemName(),
                itemEntity.getAmount(),
                itemEntity.getPrice());
    }

    private ItemEntity createItemRequest2ItemEntity(CreateItemRequest createItemRequest) {
        return new ItemEntity(UUID.randomUUID(),
                createItemRequest.getItemName(),
                createItemRequest.getAmount(),
                createItemRequest.getPrice());
    }
}
