package dev.bruno.marketapi.entity.dto;

import dev.bruno.marketapi.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductDto(
        @NotBlank String name,
        @Positive BigDecimal price,
        @Positive Integer quantity
) {

    public Product toProduct() {
        return new Product(name, price, quantity);
    }
}
