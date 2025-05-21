package dev.bruno.marketapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bruno.marketapi.common.ProductConstants;
import dev.bruno.marketapi.controller.ProductController;
import dev.bruno.marketapi.entity.dto.ProductDto;
import dev.bruno.marketapi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void createProduct_WithValidData_ReturnsProductDto() throws Exception {
        ProductDto productDto = ProductConstants.createProductDto();

        when(productService.createProduct(any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(post("/product/create-product")
                        .content(mapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(productDto.name()))
                .andExpect(jsonPath("$.price").value(productDto.price()))
                .andExpect(jsonPath("$.quantity").value(productDto.quantity()));
    }

    @Test
    public void findProduct_ById_ReturnsProductDto() throws Exception {
        ProductDto productDto = ProductConstants.createProductDto();

        when(productService.findProductById(1L)).thenReturn(productDto);

        mockMvc.perform(get("/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(productDto.name()))
                .andExpect(jsonPath("$.price").value(productDto.price()))
                .andExpect(jsonPath("$.quantity").value(productDto.quantity()));
    }

    @Test
    public void findProduct_ByName_ReturnsProductDto() throws Exception {
        ProductDto productDto = ProductConstants.createProductDto();

        when(productService.findProductByName(productDto.name())).thenReturn(productDto);

        mockMvc.perform(get("/product")
                        .param("name", productDto.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(productDto.name()))
                .andExpect(jsonPath("$.price").value(productDto.price()))
                .andExpect(jsonPath("$.quantity").value(productDto.quantity()));
    }

    @Test
    public void updateProduct_ById_ReturnsProductDto() throws Exception {
        ProductDto productDto = ProductConstants.createProductDto();
        ProductDto updatedProductDto = ProductConstants.createUpdatedProductDto();

        when(productService.updateProductById(1L, productDto)).thenReturn(updatedProductDto);

        mockMvc.perform(put("/product/update-product/{id}", 1L)
                        .content(mapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(updatedProductDto.name()))
                .andExpect(jsonPath("$.price").value(updatedProductDto.price()))
                .andExpect(jsonPath("$.quantity").value(updatedProductDto.quantity()));
    }

    @Test
    public void deleteProduct_ById_ReturnsNoContent() throws Exception {
        doNothing().when(productService).deleteProductById(1L);

        mockMvc.perform(delete("/product/delete-product/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
