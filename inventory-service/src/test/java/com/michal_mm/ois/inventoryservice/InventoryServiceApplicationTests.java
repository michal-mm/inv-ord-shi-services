package com.michal_mm.ois.inventoryservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal_mm.ois.inventoryservice.controller.InventoryController;
import com.michal_mm.ois.inventoryservice.exception.ItemNotFoundException;
import com.michal_mm.ois.inventoryservice.model.CreateItemRequest;
import com.michal_mm.ois.inventoryservice.model.ItemRest;
import com.michal_mm.ois.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(InventoryController.class)
class InventoryServiceApplicationTests {

    public static final UUID ITEM_ID = UUID.randomUUID();
    public static final String ITEM_NAME = "Junit item name";
    public static final Integer AMOUNT = 111;
    public static final Integer PRICE = 9999;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private InventoryService mockedInventoryService;


    @Test
    void getAllItems_thenReturnJsonArray() throws Exception {
        // Arrange
        // prepare expected output & mock service calls
        when(mockedInventoryService.getAllItems()).thenReturn(List.of(getValidItemRest()));

        mvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].itemName", is(ITEM_NAME)));
    }

    @Test
    void getItemById_itemNotFound_getExceptionHandler() throws Exception {
        // Arrange
        // mock service call
        when(mockedInventoryService.getItemById(ITEM_ID)).thenThrow(new ItemNotFoundException("Junit ItemNotFoundException"));

        mvc.perform(get("/items/" + ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testNotExistingUri() throws Exception {
        mvc.perform(get("/not-existing-uri")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testCreateItem_withSuccessfulResponse() throws Exception {
        // Arrange
        ItemRest itemRest = getValidItemRest();
        CreateItemRequest createItemRequest = getValidCreateItemRequest();

        when(mockedInventoryService.createItem(createItemRequest))
                .thenReturn(itemRest);

        // Assert & Act
        mvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(object2JsonStr(createItemRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateItem_withSuccessfulResponse() throws Exception {
        // Arrange
        int additional = 100;
        ItemRest expectedItemRest = getValidItemRest();
        expectedItemRest.setPrice(PRICE+additional);
        expectedItemRest.setAmount(AMOUNT+additional);

        Optional<Integer> price = Optional.of(PRICE+additional);
        Optional<Integer> amount = Optional.of(AMOUNT+additional);

        when(mockedInventoryService.updateItem(ITEM_ID, price, amount))
                .thenReturn(expectedItemRest);

        mvc.perform(patch("/items/" + ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .requestAttr("price", price)
                .requestAttr("amount", amount))
                .andExpect(status().isOk());
    }

    private static ItemRest getValidItemRest() {
        return new ItemRest(ITEM_ID, ITEM_NAME, AMOUNT, PRICE);
    }

    private static CreateItemRequest getValidCreateItemRequest() {
        return new CreateItemRequest(ITEM_NAME, AMOUNT, PRICE);
    }

    private String object2JsonStr(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
