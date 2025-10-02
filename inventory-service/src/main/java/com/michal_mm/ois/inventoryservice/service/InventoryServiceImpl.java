package com.michal_mm.ois.inventoryservice.service;

import com.michal_mm.ois.inventoryservice.data.ItemEntity;
import com.michal_mm.ois.inventoryservice.data.ItemRepository;
import com.michal_mm.ois.inventoryservice.exception.ItemNotFoundException;
import com.michal_mm.ois.inventoryservice.model.CreateItemRequest;
import com.michal_mm.ois.inventoryservice.model.ItemRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private final ItemRepository repository;

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
           throw new ItemNotFoundException("Item not found: " + itemId);
        }

        return itemEntity2ItemRest(itemEntity);
    }

    @Override
    public ItemRest createItem(CreateItemRequest createItemRequest) {
        return itemEntity2ItemRest(repository.save(createItemRequest2ItemEntity(createItemRequest)));
    }

    @Override
    public ItemRest updateItem(UUID itemId, Integer amountRaw, Integer priceRaw) {
        Optional<Integer> amount = Optional.ofNullable(amountRaw);
        Optional<Integer> price = Optional.ofNullable(priceRaw);

        ItemEntity itemEntity2Update = repository.findItemByItemId(itemId);
        if (itemEntity2Update == null) {
            throw new ItemNotFoundException("Item not found: " + itemId);
        }

        amount.ifPresent(itemEntity2Update::setAmount);
        price.ifPresent(itemEntity2Update::setPrice);

        return itemEntity2ItemRest(repository.save(itemEntity2Update));
    }

    private ItemRest itemEntity2ItemRest(ItemEntity itemEntityRaw) {
        ItemEntity itemEntity = Objects.requireNonNull(itemEntityRaw);

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
