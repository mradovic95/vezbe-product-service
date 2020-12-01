package com.raf.productservice.mapper;

import com.raf.productservice.domain.Product;
import com.raf.productservice.dto.ProductCreateDto;
import com.raf.productservice.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto productToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    public Product productCreateDtoToProduct(ProductCreateDto productCreateDto) {
        Product product = new Product();
        product.setTitle(productCreateDto.getTitle());
        product.setDescription(productCreateDto.getDescription());
        product.setPrice(productCreateDto.getPrice());
        return product;
    }
}
