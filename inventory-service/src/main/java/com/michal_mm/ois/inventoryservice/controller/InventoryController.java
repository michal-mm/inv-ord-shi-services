package com.michal_mm.ois.inventoryservice.controller;

import com.michal_mm.ois.inventoryservice.model.CreateItemRequest;
import com.michal_mm.ois.inventoryservice.model.ItemRest;
import com.michal_mm.ois.inventoryservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<ItemRest> getAllItems() {
        return inventoryService.getAllItems();
    }

    @GetMapping("/{itemId}")
    public ItemRest getItemById(@PathVariable UUID itemId) {
        return inventoryService.getItemById(itemId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRest createNewItem(@RequestBody CreateItemRequest createItemRequest) {
        return inventoryService.createItem(createItemRequest);
    }

    @PatchMapping("/{itemId}")
    public ItemRest updateItemDetails(@PathVariable UUID itemId,
                                      @RequestParam("price") Optional<Integer> price,
                                      @RequestParam("amount") Optional<Integer> amount) {

        return inventoryService.updateItem(itemId, amount, price);
    }
}
