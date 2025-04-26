package dev.bruno.marketapi.entity.dto;

import dev.bruno.marketapi.entity.Order;
import dev.bruno.marketapi.entity.Payment;
import dev.bruno.marketapi.service.OrderService;
import dev.bruno.marketapi.type.PaymentMethod;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreatePaymentDto(
        @NotNull Long orderId,
        @NotNull PaymentMethod paymentMethod,
        @Nullable String cardInformation,
        @Positive BigDecimal amountPaid
) {
    public Payment toPayment() {
        return new Payment(paymentMethod, cardInformation, amountPaid);
    }

}
