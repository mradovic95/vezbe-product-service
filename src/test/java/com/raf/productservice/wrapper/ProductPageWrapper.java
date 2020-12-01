package com.raf.productservice.wrapper;

import com.raf.productservice.dto.ProductDto;

import java.util.List;

public class ProductPageWrapper {

    private List<ProductDto> content;

    public List<ProductDto> getContent() {
        return content;
    }

    public void setContent(List<ProductDto> content) {
        this.content = content;
    }
}
