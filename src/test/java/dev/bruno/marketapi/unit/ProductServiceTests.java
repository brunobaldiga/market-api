package dev.bruno.marketapi.unit;

import dev.bruno.marketapi.common.ProductConstants;
import dev.bruno.marketapi.entity.Product;
import dev.bruno.marketapi.entity.dto.ProductDto;
import dev.bruno.marketapi.exception.DuplicateProductException;
import dev.bruno.marketapi.repository.ProductRepository;
import dev.bruno.marketapi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void createProduct_WithValidData_ReturnsProductDto() {
        when(productRepository.existsByName(anyString())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(
                ProductConstants.createProduct()
        );

        ProductDto productDtoResult = productService.createProduct(ProductConstants.createProductDto());
        ProductDto productDto = ProductConstants.createProductDto();

        assertThat(productDtoResult.id()).isEqualTo(productDto.id());
        assertThat(productDtoResult.name()).isEqualTo(productDto.name());
        assertThat(productDtoResult.price()).isEqualTo(productDto.price());
        assertThat(productDtoResult.quantity()).isEqualTo(productDto.quantity());
    }

    @Test
    public void createProduct_WithInvalidData_ReturnsProductDto() {
        when(productRepository.existsByName(anyString())).thenReturn(true);

        assertThatThrownBy(
                () -> productService.createProduct(ProductConstants.createProductDto()))
                .isInstanceOf(DuplicateProductException.class);
    }

    @Test
    public void findProduct_ByExistingId_ReturnsProductDto() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(
                ProductConstants.createProduct()
        ));

        ProductDto productDtoResult = productService.findProductById(1L);
        ProductDto productDto = ProductConstants.createProductDto();

        assertThat(productDtoResult.id()).isEqualTo(productDto.id());
        assertThat(productDtoResult.name()).isEqualTo(productDto.name());
        assertThat(productDtoResult.price()).isEqualTo(productDto.price());
        assertThat(productDtoResult.quantity()).isEqualTo(productDto.quantity());
    }

    @Test
    public void findProduct_ByName_ReturnsProductDto() {
        when(productRepository.findByName(anyString())).thenReturn(Optional.of(
                ProductConstants.createProduct()
        ));

        ProductDto productDtoResult = productService.findProductByName("Notebook");
        ProductDto productDto =  ProductConstants.createProductDto();

        assertThat(productDtoResult.id()).isEqualTo(productDto.id());
        assertThat(productDtoResult.name()).isEqualTo(productDto.name());
        assertThat(productDtoResult.price()).isEqualTo(productDto.price());
        assertThat(productDtoResult.quantity()).isEqualTo(productDto.quantity());
    }

    @Test
    public void updateProduct_ByExistingId_ReturnsProductDto() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(
                ProductConstants.createUpdatedProduct()
        ));

        ProductDto productDtoResult = productService.updateProductById(1L, ProductConstants.createUpdatedProductDto());
        ProductDto productDto =  ProductConstants.createUpdatedProductDto();

        assertThat(productDtoResult.id()).isEqualTo(productDto.id());
        assertThat(productDtoResult.name()).isEqualTo(productDto.name());
        assertThat(productDtoResult.price()).isEqualTo(productDto.price());
        assertThat(productDtoResult.quantity()).isEqualTo(productDto.quantity());
    }

    @Test
    public void deleteProduct_ByExistingId_ReturnsProductDto() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(
                ProductConstants.createUpdatedProduct()
        ));

        doNothing().when(productRepository).delete(any(Product.class));

        assertThatCode(() -> productService.deleteProductById(1L)).doesNotThrowAnyException();
    }
}
