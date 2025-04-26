package dev.bruno.marketapi.entity.dto;

import dev.bruno.marketapi.type.PaymentMethod;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentDto(
        @NotNull Long id,
        @NotNull Long orderId,
        @NotNull PaymentMethod paymentMethod,
        @NotNull BigDecimal amountPaid
) {
}
