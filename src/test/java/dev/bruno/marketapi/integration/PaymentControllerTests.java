package dev.bruno.marketapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bruno.marketapi.common.OrderConstants;
import dev.bruno.marketapi.common.PaymentConstants;
import dev.bruno.marketapi.controller.PaymentController;
import dev.bruno.marketapi.entity.dto.CreatePaymentDto;
import dev.bruno.marketapi.entity.dto.OrderDto;
import dev.bruno.marketapi.entity.dto.PaymentDto;
import dev.bruno.marketapi.entity.dto.OrderItemDto;
import dev.bruno.marketapi.repository.OrderRepository;
import dev.bruno.marketapi.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({PaymentController.class, OrderRepository.class})
public class PaymentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void processPayment_WithValidData_ReturnsOrderDto() throws Exception {
        PaymentDto paymentDto = PaymentConstants.createPaymentDto();
        OrderDto orderDto = OrderConstants.createOrderDto2(List.of(paymentDto));

        when(paymentService.processPayment(any(CreatePaymentDto.class))).thenReturn(orderDto);

        OrderItemDto item = orderDto.orderItems().get(0);

        mockMvc.perform(post("/payment/charge")
                        .content(objectMapper.writeValueAsString(PaymentConstants.createCreatePaymentDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.change").value(orderDto.change()))
                .andExpect(jsonPath("$.total").value(orderDto.total()))
                .andExpect(jsonPath("$.orderItems[0].productId").value(item.productId()))
                .andExpect(jsonPath("$.orderItems[0].productName").value(item.productName()))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(item.quantity()))
                .andExpect(jsonPath("$.orderItems[0].priceAtPurchase").value(item.priceAtPurchase()))
                .andExpect(jsonPath("$.orderItems[0].total").value(item.total()))
                .andExpect(jsonPath("$.payments[0].id").exists())
                .andExpect(jsonPath("$.payments[0].orderId").value(paymentDto.orderId()))
                .andExpect(jsonPath("$.payments[0].paymentMethod").value(paymentDto.paymentMethod().toString()))
                .andExpect(jsonPath("$.payments[0].amountPaid").value(paymentDto.amountPaid()));
    }

    @Test
    public void findPayment_ById_ReturnsPaymentDto() throws Exception {
        PaymentDto paymentDto = PaymentConstants.createPaymentDto();

        when(paymentService.findPaymentById(any())).thenReturn(paymentDto);

        mockMvc.perform(get("/payment/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.orderId").value(paymentDto.orderId()))
                .andExpect(jsonPath("$.paymentMethod").value(paymentDto.paymentMethod().toString()))
                .andExpect(jsonPath("$.amountPaid").value(paymentDto.amountPaid()));
    }
}
