package dev.bruno.marketapi.entity.dto;

import dev.bruno.marketapi.entity.Product;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateProductDto(
        @NotBlank String name,
        @NotBlank BigDecimal price,
        @NotBlank Integer quantity
) {

    public Product toProduct() {
        return new Product(name, price, quantity);
    }
}
