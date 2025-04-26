package dev.bruno.marketapi.entity.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrderDto(

        @NotNull(message = "Order items cannot be null")
        @Valid @Size(min = 1, message = "Order must contain at least one item")
        List<CreateOrderItemDto> orderItems
) {
}
