package com.raf.productservice.service.impl;

import com.raf.productservice.domain.Product;
import com.raf.productservice.dto.ProductCreateDto;
import com.raf.productservice.dto.ProductDto;
import com.raf.productservice.dto.ProductUpdateDto;
import com.raf.productservice.exception.NotFoundException;
import com.raf.productservice.mapper.ProductMapper;
import com.raf.productservice.repository.ProductRepository;
import com.raf.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::productToProductDto);
    }

    @Override
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductDto)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id: %d not found.", id)));
    }

    @Override
    public ProductDto add(ProductCreateDto productCreateDto) {
        return productMapper.productToProductDto(productRepository
                .save(productMapper.productCreateDtoToProduct(productCreateDto)));
    }

    @Override
    public ProductDto update(Long id, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id: %d not found.", id)));
        //Set values to product
        product.setTitle(productUpdateDto.getTitle());
        product.setDescription(productUpdateDto.getDescription());
        product.setPrice(productUpdateDto.getPrice());
        //Map product to DTO and return it
        return productMapper.productToProductDto(productRepository.save(product));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
