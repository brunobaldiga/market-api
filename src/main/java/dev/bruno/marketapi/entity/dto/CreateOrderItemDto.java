package dev.bruno.marketapi.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderItemDto(
        @NotNull Long productId,
        @Positive Integer quantity
) {
}
