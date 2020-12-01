package com.raf.productservice.service;

import com.raf.productservice.dto.ProductCreateDto;
import com.raf.productservice.dto.ProductDto;
import com.raf.productservice.dto.ProductUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDto> findAll(Pageable pageable);

    ProductDto findById(Long id);

    ProductDto add(ProductCreateDto productCreateDto);

    ProductDto update(Long id, ProductUpdateDto productUpdateDto);

    void deleteById(Long id);
}
