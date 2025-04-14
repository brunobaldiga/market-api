package dev.bruno.marketapi.entity.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrderDto(
        @NotNull @Valid @Size(min = 1) List<CreateOrderItemDto> orderItems
) {
}
