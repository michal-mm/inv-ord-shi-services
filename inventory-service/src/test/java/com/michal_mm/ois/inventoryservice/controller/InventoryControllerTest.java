package com.michal_mm.ois.inventoryservice.controller;

import com.michal_mm.ois.inventoryservice.data.ItemEntity;
import com.michal_mm.ois.inventoryservice.data.ItemRepository;
import com.michal_mm.ois.inventoryservice.exception.ItemNotFoundException;
import com.michal_mm.ois.inventoryservice.model.CreateItemRequest;
import com.michal_mm.ois.inventoryservice.model.ItemRest;
import com.michal_mm.ois.inventoryservice.service.InventoryService;
import com.michal_mm.ois.inventoryservice.service.InventoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
class InventoryControllerTest {

    public static final UUID ITEM_ID = UUID.randomUUID();
    public static final String ITEM_NAME = "Junit item name";
    public static final Integer AMOUNT = 111;
    public static final Integer PRICE = 9999;
    private InventoryController inventoryController;

    @Mock
    private ItemRepository repository;

    @Mock
    private InventoryService mockedInventoryService;

    @BeforeEach
    void setUp() {
        try (var _ = MockitoAnnotations.openMocks(this)) {
            inventoryController = new InventoryController(new InventoryServiceImpl(repository));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllItems() {
        // Arrange
        // prepare expected output
        List<ItemRest> expectedListOfItemRest = List.of(getValidItemRest());

        // mock repo calls
        when(repository.findAll()).thenReturn(List.of(getValidItemEntity()));

        // Act
        List<ItemRest> allItems = inventoryController.getAllItems();

        // Assert
        assertEquals(expectedListOfItemRest, allItems);
    }

    @Test
    void getItemById_withExistingItemID() {
        // Arrange
        // prepare expected output
        ItemRest expectedItemRest = getValidItemRest();
        ItemRest notExpectedItemRest = getValidItemRest();
        notExpectedItemRest.setItemName("NOT VALID ITEM");

        // prepare mocked itemEntity response
        // mock repo calls
        when(repository.findItemByItemId(ITEM_ID)).thenReturn(getValidItemEntity());

        // Act
        ItemRest outputItemRest = inventoryController.getItemById(ITEM_ID);

        // Assert
        assertEquals(expectedItemRest, outputItemRest);
        assertNotEquals(notExpectedItemRest, outputItemRest);
    }

    @Test
    void getItemById_ItemIdNotFoundException() {
        // Arrange
        // prepare expected output
        UUID itemId = UUID.randomUUID();

        // prepare mocked inventoryController
        inventoryController = new InventoryController(mockedInventoryService);

        // mock repo calls
        when(inventoryController.getItemById(itemId)).thenThrow(new ItemNotFoundException("Unit testing item not found"));

        // Act & Assert
        assertThrows(ItemNotFoundException.class, () ->
                inventoryController.getItemById(itemId));
        verifyNoInteractions(repository);
    }

    @Test
    void getItemById_repositoryReturnsNull_thenThrowsItemNotFoundException() {
        // Arrange and Act
        when(repository.findItemByItemId(ITEM_ID)).thenReturn(null);

        // Assert
        assertThrows(ItemNotFoundException.class, () ->
                inventoryController.getItemById(ITEM_ID));
    }

    @Test
    void createNewItem() {
        // Arrange
        // prepare expected output
        ItemRest expectedItemRest = getValidItemRest();

        // prepare CreateItemRequest object
        CreateItemRequest createItemRequest = getValidCreateItemRequest();

        // prepare mocked itemEntity to be created
        ItemEntity itemEntity = getValidItemEntity();

        // Act
        when(repository.save(any())).thenReturn(itemEntity);
        ItemRest returnedCreatedItemRest = inventoryController.createNewItem(createItemRequest);

        // Assert
        assertEquals(expectedItemRest, returnedCreatedItemRest);
    }

    @Test
    void updateItemDetails_withUpdateOfPriceAndAmount() {
        // Arrange
        Integer forUpdate = 10000;
        // prepare expected output
        ItemRest expectedItemRest = getValidItemRest();
        expectedItemRest.setAmount(expectedItemRest.getAmount()+forUpdate);
        expectedItemRest.setPrice(expectedItemRest.getPrice()+forUpdate);

        // create ItemEntity object for mocked response
        ItemEntity mockedItemEntity = getValidItemEntity();
        ItemEntity mockedItemEntityWithUpdates = getValidItemEntity();
        mockedItemEntityWithUpdates.setAmount(mockedItemEntityWithUpdates.getAmount()+forUpdate);
        mockedItemEntityWithUpdates.setPrice(mockedItemEntityWithUpdates.getPrice()+forUpdate);

        // optional price and amount
        Integer amount = AMOUNT+forUpdate;
        Integer price = PRICE+forUpdate;

        // Act
        when(repository.findItemByItemId(ITEM_ID)).thenReturn(mockedItemEntity);
        when(repository.save(any())).thenReturn(mockedItemEntityWithUpdates);
        ItemRest returnedUpdatedItemRest = inventoryController.updateItemDetails(ITEM_ID, price, amount);

        // Assert
        assertEquals(expectedItemRest, returnedUpdatedItemRest);
        assertNotEquals(expectedItemRest, getValidItemRest());
    }

    @Test
    void updateItemDetails_withUpdateOfPriceOnlyAndNullAsAmount() {
        // Arrange
        Integer forUpdate = 10000;
        // prepare expected output
        ItemRest expectedItemRest = getValidItemRest();
        expectedItemRest.setAmount(expectedItemRest.getAmount());
        expectedItemRest.setPrice(expectedItemRest.getPrice()+forUpdate);

        // create ItemEntity object for mocked response
        ItemEntity mockedItemEntity = getValidItemEntity();
        ItemEntity mockedItemEntityWithUpdates = getValidItemEntity();
        mockedItemEntityWithUpdates.setAmount(mockedItemEntityWithUpdates.getAmount());
        mockedItemEntityWithUpdates.setPrice(mockedItemEntityWithUpdates.getPrice()+forUpdate);

        // optional price and amount
        Integer price = PRICE+forUpdate;

        // Act
        when(repository.findItemByItemId(ITEM_ID)).thenReturn(mockedItemEntity);
        when(repository.save(any())).thenReturn(mockedItemEntityWithUpdates);
        ItemRest returnedUpdatedItemRest = inventoryController.updateItemDetails(ITEM_ID, price, null);

        // Assert
        assertEquals(expectedItemRest, returnedUpdatedItemRest);
        assertNotEquals(expectedItemRest, getValidItemRest());
    }

    @Test
    void updateItemDetails_withInvalidItemId_throwsItemNotFoundException () {
        // Arrange & Act
        when(repository.findItemByItemId(ITEM_ID)).thenReturn(null);

        // Assert
        assertThrows(ItemNotFoundException.class, () ->
                inventoryController.updateItemDetails(ITEM_ID,
                        null,
                        null));
    }

    private static ItemEntity getValidItemEntity() {
        return new ItemEntity(ITEM_ID, ITEM_NAME, AMOUNT, PRICE);
    }

    private static ItemRest getValidItemRest() {
        return new ItemRest(ITEM_ID, ITEM_NAME, AMOUNT, PRICE);
    }

    private static CreateItemRequest getValidCreateItemRequest() {
        return new CreateItemRequest(ITEM_NAME, AMOUNT, PRICE);
    }
}