package dev.bruno.marketapi.unit;

import dev.bruno.marketapi.common.OrderConstants;
import dev.bruno.marketapi.common.ProductConstants;
import dev.bruno.marketapi.entity.Order;
import dev.bruno.marketapi.entity.dto.OrderDto;
import dev.bruno.marketapi.exception.EntityNotFoundException;
import dev.bruno.marketapi.repository.OrderRepository;
import dev.bruno.marketapi.repository.ProductRepository;
import dev.bruno.marketapi.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void createOrder_WithValidData_ReturnsOrderDto() {
        when(orderRepository.save(any(Order.class))).thenReturn(
                OrderConstants.createOrder(false, List.of(OrderConstants.createOrderItem()))
        );

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(ProductConstants.createProduct()));

        OrderDto orderDtoResult = orderService.createOrder(OrderConstants.createCreateOrderDto());
        OrderDto orderDto = OrderConstants.createOrderDto1();

        assertThat(orderDtoResult.id()).isEqualTo(orderDto.id());
        assertThat(orderDtoResult.change()).isEqualTo(orderDto.change());
        assertThat(orderDtoResult.total()).isEqualTo(orderDto.total());
        assertThat(orderDtoResult.orderItems().get(0)).isEqualTo(orderDto.orderItems().get(0));
    }


    @Test
    public void createOrder_WithInvalidData_ReturnsEntityNotFoundException() {
        when(productRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> orderService.createOrder(OrderConstants.createInvalidOrderDto()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void findOrder_ByExistingId_ReturnOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(
                OrderConstants.createOrder(false, List.of(OrderConstants.createOrderItem()))
        ));

        OrderDto orderDtoResult = orderService.findOrderById(1L);
        OrderDto orderDto = OrderConstants.createOrderDto1();

        assertThat(orderDtoResult.id()).isEqualTo(orderDto.id());
        assertThat(orderDtoResult.change()).isEqualTo(orderDto.change());
        assertThat(orderDtoResult.total()).isEqualTo(orderDto.total());
        assertThat(orderDtoResult.orderItems().get(0)).isEqualTo(orderDto.orderItems().get(0));
    }

    @Test
    public void findOrder_ByInvalidId_ReturnOrder() {
        when(orderRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> orderService.findOrderById(1L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void deleteOrder_ById_ReturnOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(
                OrderConstants.createOrder(false, List.of(OrderConstants.createOrderItem()))
        ));

        assertThatCode(() -> orderService.deleteOrderById(1L)).doesNotThrowAnyException();
    }

    @Test
    public void deleteOrder_ByInvalidId_ReturnOrder() {
        when(orderRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> orderService.deleteOrderById(1L)).isInstanceOf(EntityNotFoundException.class);
    }


}
