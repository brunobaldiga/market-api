package dev.bruno.marketapi.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemDto(
        @NotNull Long productId,
        @NotNull String productName,
        @Positive Integer quantity,
        @Positive BigDecimal priceAtPurchase,
        @Positive BigDecimal total
        ) {
}
