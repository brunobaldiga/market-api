package dev.bruno.marketapi.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        @NotNull Long id,

        @NotNull @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdAt,

        @NotNull BigDecimal change,
        @Positive BigDecimal total,
        @NotNull @Size(min = 1) List<OrderItemDto> orderItems,
        @NotNull List<PaymentDto> payments
) {}
