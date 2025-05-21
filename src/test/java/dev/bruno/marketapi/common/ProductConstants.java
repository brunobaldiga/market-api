package dev.bruno.marketapi.common;

import dev.bruno.marketapi.entity.Product;
import dev.bruno.marketapi.entity.dto.ProductDto;

import java.math.BigDecimal;

public class ProductConstants {

    public static ProductDto createProductDto() {
        return new ProductDto(1L, "Notebook", BigDecimal.valueOf(99.99), 1);
    }

    public static ProductDto createUpdatedProductDto() {
        return new ProductDto(1L, "Notebook X", BigDecimal.valueOf(199.99), 10);
    }

    public static Product createUpdatedProduct() {
        return new Product(1L, "Notebook X", BigDecimal.valueOf(199.99), 10);
    }

    public static Product createProduct() {
        return new Product(1L, "Notebook", BigDecimal.valueOf(99.99), 1);
    }
}
