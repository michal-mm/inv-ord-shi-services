package com.michal_mm.ois.orderservice.controller;

import com.michal_mm.ois.orderservice.data.OrderEntity;
import com.michal_mm.ois.orderservice.data.OrderRepository;
import com.michal_mm.ois.orderservice.exception.NotEnoughItemsInInventoryException;
import com.michal_mm.ois.orderservice.exception.OrderNotFoundException;
import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.ItemRestDTO;
import com.michal_mm.ois.orderservice.model.OrderRest;
import com.michal_mm.ois.orderservice.service.OrderServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersUriSpec;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings({"rawtypes", "unchecked"})
@SpringBootTest(classes = {OrderControllerTest.class})
public class OrderControllerTest {

    public static final UUID ORDER_ID = UUID.randomUUID();
    public static final UUID ITEM_ID = UUID.randomUUID();
    public static final String ITEM_NAME = "Unit test item name";
    public static final String ORDER_NAME = "Unit test order name";
    public static final Integer QUANTITY = 125;
    public static final Integer ITEM_PRICE = 5;

    private OrderController orderController;

	@Mock
	private OrderRepository orderRepository;

    @Mock
    private RestClient restClient;

	@BeforeEach
	void setUp() {
        try (var _ = MockitoAnnotations.openMocks(this)) {
            orderController = new OrderController(new OrderServiceImpl(orderRepository, restClient));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	@Test
	public void testGetAllOrders_simpleGETScenario_returnsValidOrderRest() {
		// Arrange 
		OrderEntity orderEntity = getValidOrderEntity();
		
		// Act
		when(orderRepository.findAll()).thenReturn(List.of(orderEntity));
	
		List<OrderRest> orders = orderController.getAllOrders();
		
		// Assert
		assertEquals(1, orders.size());
		assertEquals(ORDER_ID, orders.getFirst().getOrderId());
	}



    @Test
	public void testGetOrderById_withValidOrderId_returnsValidOrderRest() {
		// Arrange
		OrderEntity orderEntity = getValidOrderEntity();
		
		// Act
		when(orderRepository.findOrderById(ORDER_ID)).thenReturn(orderEntity);
		OrderRest orderRest = orderController.getOrderById(ORDER_ID);
		
		// Assert
        assertThat(orderRest, allOf(
                notNullValue(),
                hasProperty("orderId", equalTo(orderEntity.getId())),
                hasProperty("itemId", equalTo(orderEntity.getItemId())),
                hasProperty("orderName", equalTo(orderEntity.getOrderName())),
                hasProperty("itemName", equalTo(orderEntity.getItemName()))
        ));
	}

    @Test
    public void testGetOrderById_withInvalidOrderId_handlesException() {
        // Arrange
        when(orderRepository.findOrderById(ORDER_ID)).thenReturn(null);

        // Act
        assertThrows(OrderNotFoundException.class,
                () -> orderController.getOrderById(ORDER_ID));
    }
	
	@Test
	public void testCreateOrder_withValidCreateOrderRequest_returnsValidOrderRest() {
		// Arrange
        CreateOrderRequest createOrderRequest = getValidCreateOrderRequest();
		OrderEntity orderEntity = getValidOrderEntity();
        ItemRestDTO itemRestDTOExpected = getValidItemRestDTO();
        var responseEntity = ResponseEntity.of(Optional.of(itemRestDTOExpected));
		
		// Act
		when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        mockSuccessfulGetCallToInventoryService(responseEntity);
        mockSuccessfulPatchCallToInventoryService(responseEntity);

		OrderRest savedRest = orderController.createOrder(createOrderRequest);
		
		// Assert
        verify(orderRepository).save(argThat(entity ->
                entity.getItemId().equals(createOrderRequest.itemId()) &&
                        entity.getOrderName().equals(createOrderRequest.orderName()) &&
                        entity.getQuantity().equals(createOrderRequest.quantity())
        ));

		assertNotNull(savedRest);
		assertEquals(ITEM_ID.toString(), savedRest.getItemId().toString());
		assertEquals(ORDER_NAME, savedRest.getOrderName());
        assertEquals(ITEM_NAME, savedRest.getItemName());
		assertEquals(ORDER_ID.toString(), savedRest.getOrderId().toString());
	}

    @Test
    public void testCreateOrder_withInvalidItemId_throwsHttpClientErrorExceptionNotFound() {
        // Arrange
        CreateOrderRequest createOrderRequest = getValidCreateOrderRequest();
        OrderEntity orderEntity = getValidOrderEntity();

        // we set this mock to prove that order was not saved because of an exception
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        mockUnsuccessfulCallToInventoryService_ItemNotFound();

        // Assert
        assertThrows(HttpClientErrorException.NotFound.class, () ->
                orderController.createOrder(createOrderRequest));
        verifyNoInteractions(orderRepository);
    }

    @Test
    public void testCreateOrder_withTooManyItemsRequested_throwsNotEnoughItemsInInventoryException() {
        // Arrange
        CreateOrderRequest createOrderRequest = getValidCreateOrderRequest();
        OrderEntity orderEntity = getValidOrderEntity();
        ItemRestDTO mockedItemRestDTO = getValidItemRestDTOWithZeroAmount();
        // set quantity to zero to cause problem with order placement
//        mockedOrderRest.setQuantity(0);
        var responseEntityMocked = ResponseEntity.of(Optional.of(mockedItemRestDTO));

        mockSuccessfulGetCallToInventoryService(responseEntityMocked);
        mockSuccessfulPatchCallToInventoryService(responseEntityMocked);

        // we set this mock to prove that order was not saved because of an exception
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        // Act & Assert
        assertThrows(NotEnoughItemsInInventoryException.class, () ->
                orderController.createOrder(createOrderRequest));
        verifyNoInteractions(orderRepository);
    }

    private void mockUnsuccessfulCallToInventoryService_ItemNotFound() {
        RequestHeadersUriSpec reqHeaders = Mockito.mock(RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(reqHeaders);
        when(reqHeaders.uri(any(), (Object) any())).thenReturn(reqHeaders);
        when(reqHeaders.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toEntity(ItemRestDTO.class)).thenThrow(HttpClientErrorException.NotFound.class);
    }

    private void mockSuccessfulGetCallToInventoryService(ResponseEntity responseEntity) {
        RequestHeadersUriSpec reqHeaders = mock(RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(reqHeaders);
        when(reqHeaders.uri(any(), (Object) any())).thenReturn(reqHeaders);
        when(reqHeaders.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toEntity(ItemRestDTO.class)).thenReturn(responseEntity);
    }

    private void mockSuccessfulPatchCallToInventoryService(ResponseEntity responseEntity) {
        RestClient.RequestBodyUriSpec requestBodyUriSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.RequestBodySpec requestBodySpec = mock(RestClient.RequestBodySpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(restClient.patch()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(), (Object) (any()))).thenReturn(requestBodySpec);
        when(requestBodyUriSpec.uri((URI) any())).thenReturn(requestBodySpec);
        when(requestBodySpec.attribute(any(), any())).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toEntity(ItemRestDTO.class)).thenReturn(responseEntity);
    }

    @NotNull
    private static CreateOrderRequest getValidCreateOrderRequest() {
        return new CreateOrderRequest(ITEM_ID, ORDER_NAME, QUANTITY);
    }

    @NotNull
    private static OrderEntity getValidOrderEntity() {
        return new OrderEntity(ORDER_ID, ITEM_ID,
                ITEM_NAME,
                ORDER_NAME,
                QUANTITY,
                ITEM_PRICE);
    }

    private static ItemRestDTO getValidItemRestDTO() {
        return new ItemRestDTO(ITEM_ID, ITEM_NAME, QUANTITY, ITEM_PRICE);
    }

    private static ItemRestDTO getValidItemRestDTOWithZeroAmount() {
        return new ItemRestDTO(ITEM_ID, ITEM_NAME, 0, ITEM_PRICE);
    }
}
