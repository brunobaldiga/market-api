package dev.bruno.marketapi.controller;

import dev.bruno.marketapi.entity.dto.ProductDto;
import dev.bruno.marketapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/product")
@Tag(name = "Product", description = "Endpoints for managing products in the marketplace")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create-product")
    @Operation(
            summary = "Create a new product",
            description = "Creates a new product in the marketplace. Returns the created product with its unique ID."
    )
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto createProductDto) {
        ProductDto productDto = productService.createProduct(createProductDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(productDto.id())
                .toUri();

        return ResponseEntity.created(location).body(productDto);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a product by ID",
            description = "Retrieves the details of a specific product using its unique ID."
    )
    public ResponseEntity<ProductDto> findProductById(@PathVariable Long id) {
        ProductDto productDto = productService.findProductById(id);

        return ResponseEntity.ok().body(productDto);
    }
}
