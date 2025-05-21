package dev.bruno.marketapi.service;

import dev.bruno.marketapi.entity.Product;
import dev.bruno.marketapi.entity.dto.ProductDto;
import dev.bruno.marketapi.exception.DuplicateProductException;
import dev.bruno.marketapi.exception.EntityNotFoundException;
import dev.bruno.marketapi.repository.ProductRepository;
import jakarta.transaction.Transactional;
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
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id)
        ).toDto();
    }

    public ProductDto findProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(name)
        ).toDto();
    }

    @Transactional
    public ProductDto updateProductById(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id)
        );

        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setQuantity(productDto.quantity());

        return product.toDto();
    }

    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id)
        );

        productRepository.delete(product);
    }
}
