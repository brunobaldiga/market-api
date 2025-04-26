package dev.bruno.marketapi.service;

import dev.bruno.marketapi.entity.Product;
import dev.bruno.marketapi.entity.dto.ProductDto;
import dev.bruno.marketapi.exception.DuplicateProductException;
import dev.bruno.marketapi.exception.EntityNotFoundException;
import dev.bruno.marketapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto createProduct(ProductDto productDto) {
        if (productRepository.existsByName(productDto.name())) {
            throw new DuplicateProductException(productDto.name());
        }
        Product savedProduct = productRepository.save(productDto.toProduct());

        return savedProduct.toDto();
    }

    public ProductDto findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id)
        );

        return product.toDto();
    }
}
