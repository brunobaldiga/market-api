package dev.bruno.marketapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bruno.marketapi.common.OrderConstants;
import dev.bruno.marketapi.controller.OrderController;
import dev.bruno.marketapi.entity.dto.CreateOrderDto;
import dev.bruno.marketapi.entity.dto.OrderDto;
import dev.bruno.marketapi.entity.dto.OrderItemDto;
import dev.bruno.marketapi.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void createOrder_WithValidData_ReturnsCreated() throws Exception {
        OrderDto orderDto = OrderConstants.createOrderDto1();
        when(orderService.createOrder(any(CreateOrderDto.class))).thenReturn(orderDto);

        mockMvc.perform(post("/order/create-order")
                        .content(mapper.writeValueAsString(OrderConstants.createCreateOrderDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.change").value(orderDto.change()))
                .andExpect(jsonPath("$.total").value(orderDto.total()));

        OrderItemDto item1 = orderDto.orderItems().get(0);
        OrderItemDto item2 = orderDto.orderItems().get(1);

        mockMvc.perform(post("/order/create-order")
                        .content(mapper.writeValueAsString(OrderConstants.createCreateOrderDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderItems[0].productId").value(item1.productId()))
                .andExpect(jsonPath("$.orderItems[0].productName").value(item1.productName()))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(item1.quantity()))
                .andExpect(jsonPath("$.orderItems[0].priceAtPurchase").value(item1.priceAtPurchase()))
                .andExpect(jsonPath("$.orderItems[0].total").value(item1.total()))
                .andExpect(jsonPath("$.orderItems[1].productId").value(item2.productId()))
                .andExpect(jsonPath("$.orderItems[1].productName").value(item2.productName()))
                .andExpect(jsonPath("$.orderItems[1].quantity").value(item2.quantity()))
                .andExpect(jsonPath("$.orderItems[1].priceAtPurchase").value(item2.priceAtPurchase()))
                .andExpect(jsonPath("$.orderItems[1].total").value(item2.total()))
                .andExpect(jsonPath("$.payments").isArray())
                .andExpect(jsonPath("$.payments").isEmpty());
    }

    @Test
    public void createOrder_WithInvalidData_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/order/create-order")
                        .content(mapper.writeValueAsString(OrderConstants.createBlankOrderDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findOrder_ById_ReturnsOrder() throws Exception {
        OrderDto orderDto = OrderConstants.createOrderDto1();
        OrderItemDto item = orderDto.orderItems().get(0);

        when(orderService.findOrderById(1L)).thenReturn(orderDto);

        mockMvc.perform(get("/order/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.change").value(orderDto.change()))
                .andExpect(jsonPath("$.total").value(orderDto.total()))
                .andExpect(jsonPath("$.orderItems[0].productId").value(item.productId()))
                .andExpect(jsonPath("$.orderItems[0].productName").value(item.productName()))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(item.quantity()))
                .andExpect(jsonPath("$.orderItems[0].priceAtPurchase").value(item.priceAtPurchase()))
                .andExpect(jsonPath("$.orderItems[0].total").value(item.total()))
                .andExpect(jsonPath("$.payments").isArray())
                .andExpect(jsonPath("$.payments").isEmpty());
    }

    @Test
    public void deleteOrder_ById_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/order/delete-order/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
