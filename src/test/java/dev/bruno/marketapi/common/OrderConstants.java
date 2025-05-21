package dev.bruno.marketapi.common;

import dev.bruno.marketapi.entity.Order;
import dev.bruno.marketapi.entity.OrderItem;
import dev.bruno.marketapi.entity.dto.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderConstants {

    public static CreateOrderItemDto createOrderItemDto() {
        return new CreateOrderItemDto(1L, 1);
    }

    public static CreateOrderItemDto createInvalidOrderItemDto() {
        return new CreateOrderItemDto(999L, 1);
    }

    public static CreateOrderDto createCreateOrderDto() {
        return new CreateOrderDto(List.of(createOrderItemDto()));
    }

    public static CreateOrderDto createInvalidOrderDto() {
        return new CreateOrderDto(List.of(createInvalidOrderItemDto()));
    }

    public static CreateOrderDto createBlankOrderDto() {
        return new CreateOrderDto(new ArrayList<>());
    }

    public static OrderItemDto createOrderItemDto1() {
        return new OrderItemDto(1L, "Notebook", 1, BigDecimal.valueOf(99.99), BigDecimal.valueOf(99.99));
    }

    public static OrderItemDto createOrderItemDto2() {
        return new OrderItemDto(2L, "Laptop", 1, BigDecimal.valueOf(60), BigDecimal.valueOf(60));
    }

    public static OrderDto createOrderDto1() {
        return new OrderDto(
                1L,
                LocalDateTime.now(),
                BigDecimal.valueOf(0.01),
                BigDecimal.valueOf(160.0),
                List.of(createOrderItemDto1(), createOrderItemDto2()),
                new ArrayList<>()
        );
    }

    public static OrderItem createOrderItem() {
        return new OrderItem(1L, ProductConstants.createProduct(), 1, BigDecimal.valueOf(99.99), null);
    }

    public static Order createOrder(Boolean isPaid, List<OrderItem> items) {
        return new Order(1L, LocalDateTime.now(), BigDecimal.valueOf(160.0), items, new ArrayList<>(), isPaid, BigDecimal.valueOf(0.01));
    }

    public static OrderDto createOrderDto2(List<PaymentDto> payments) {
        return new OrderDto(1L, LocalDateTime.now(), BigDecimal.valueOf(0.01), BigDecimal.valueOf(1999.99), List.of(createOrderItemDto1()), payments);
    }
}
