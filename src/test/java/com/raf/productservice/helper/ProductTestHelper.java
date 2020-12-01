package com.raf.productservice.helper;

import com.raf.productservice.domain.Product;
import com.raf.productservice.dto.ProductCreateDto;
import com.raf.productservice.dto.ProductDto;
import com.raf.productservice.dto.ProductUpdateDto;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public final class ProductTestHelper {

    private ProductTestHelper() {

    }

    public static Product createTestProduct(String title, String description, BigDecimal price) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }

    public static ProductCreateDto createTestProductCreateDto(String title, String description, BigDecimal price) {
        ProductCreateDto productCreateDto = new ProductCreateDto();
        productCreateDto.setTitle(title);
        productCreateDto.setDescription(description);
        productCreateDto.setPrice(price);
        return productCreateDto;
    }

    public static ProductUpdateDto createTestProductUpdateDto(String title, String description, BigDecimal price) {
        ProductUpdateDto productUpdateDto = new ProductUpdateDto();
        productUpdateDto.setTitle(title);
        productUpdateDto.setDescription(description);
        productUpdateDto.setPrice(price);
        return productUpdateDto;
    }

    public static void assertProductDto(ProductDto productDto, Long id, String title, String description, BigDecimal price) {
        assertThat(productDto.getId()).isEqualTo(id);
        assertThat(productDto.getTitle()).isEqualTo(title);
        assertThat(productDto.getDescription()).isEqualTo(description);
        assertThat(productDto.getPrice()).isEqualTo(price);
    }
}
