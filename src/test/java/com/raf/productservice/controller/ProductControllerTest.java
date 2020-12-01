package com.raf.productservice.controller;

import com.raf.productservice.domain.Product;
import com.raf.productservice.dto.ProductCreateDto;
import com.raf.productservice.dto.ProductDto;
import com.raf.productservice.dto.ProductUpdateDto;
import com.raf.productservice.exception.ErrorCode;
import com.raf.productservice.exception.ErrorDetails;
import com.raf.productservice.repository.ProductRepository;
import com.raf.productservice.wrapper.ProductPageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static com.raf.productservice.helper.ProductTestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private static final String PRODUCT_URL = "/product";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        //given
        Product product = createTestProduct("product1", "desc1", new BigDecimal("100.00"));
        productRepository.save(product);
        //when
        ResponseEntity<ProductPageWrapper> response = testRestTemplate
                .exchange(PRODUCT_URL, HttpMethod.GET, null, ProductPageWrapper.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent().size()).isEqualTo(1);
        assertProductDto(response.getBody().getContent().get(0), product.getId(), product.getTitle(),
                product.getDescription(), product.getPrice());
    }

    @Test
    public void testFindAllWithSpecifiedPageable() {
        //given
        Product product1 = createTestProduct("product1", "desc1", new BigDecimal("100.00"));
        Product product2 = createTestProduct("product2", "desc2", new BigDecimal("200.00"));
        productRepository.save(product1);
        productRepository.save(product2);
        String url = PRODUCT_URL + "?page=1&size=1";
        //when
        ResponseEntity<ProductPageWrapper> response = testRestTemplate
                .exchange(url, HttpMethod.GET, null, ProductPageWrapper.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent().size()).isEqualTo(1);
        assertProductDto(response.getBody().getContent().get(0), product2.getId(), product2.getTitle(),
                product2.getDescription(), product2.getPrice());
    }

    @Test
    public void testFindById() {
        //given
        Product product = createTestProduct("product", "desc", new BigDecimal("100.00"));
        productRepository.save(product);
        String url = PRODUCT_URL + "/" + product.getId();
        //when
        ResponseEntity<ProductDto> response = testRestTemplate
                .exchange(url, HttpMethod.GET, null, ProductDto.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertProductDto(response.getBody(), product.getId(), product.getTitle(),
                product.getDescription(), product.getPrice());
    }

    @Test
    public void testFindByIdNotFound() {
        //given
        long productId = 1;
        String url = PRODUCT_URL + "/" + productId;
        //when
        ResponseEntity<ErrorDetails> response = testRestTemplate
                .exchange(url, HttpMethod.GET, null, ErrorDetails.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getErrorMessage())
                .isEqualTo(String.format("Product with id: %d not found.", productId));
        assertThat(response.getBody().getErrorCode()).isEqualTo(ErrorCode.RESOURCE_NOT_FOUND);
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    @Test
    public void testAdd() {
        //given
        ProductCreateDto productCreateDto = createTestProductCreateDto("product", "desc",
                new BigDecimal("100.00"));
        HttpEntity<ProductCreateDto> request = new HttpEntity<>(productCreateDto);
        //when
        ResponseEntity<ProductDto> response = testRestTemplate
                .exchange(PRODUCT_URL, HttpMethod.POST, request, ProductDto.class);
        //then
        //check response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getTitle()).isEqualTo(productCreateDto.getTitle());
        assertThat(response.getBody().getDescription()).isEqualTo(productCreateDto.getDescription());
        assertThat(response.getBody().getPrice()).isEqualTo(productCreateDto.getPrice());
        //check from database
        Product productFromDatabase = productRepository.findAll().get(0);
        assertThat(productFromDatabase.getTitle()).isEqualTo(productCreateDto.getTitle());
        assertThat(productFromDatabase.getDescription()).isEqualTo(productCreateDto.getDescription());
        assertThat(productFromDatabase.getPrice()).isEqualTo(productCreateDto.getPrice());
    }

    @Test
    public void testUpdate() {
        //given
        Product product = createTestProduct("product", "desc", new BigDecimal("100.00"));
        productRepository.save(product);
        String url = PRODUCT_URL + "/" + product.getId();
        ProductUpdateDto productUpdateDto = createTestProductUpdateDto("title1", "desc1",
                new BigDecimal("200.00"));
        HttpEntity<ProductUpdateDto> request = new HttpEntity<>(productUpdateDto);
        //when
        ResponseEntity<ProductDto> response = testRestTemplate
                .exchange(url, HttpMethod.PUT, request, ProductDto.class);
        //then
        //check response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo(productUpdateDto.getTitle());
        assertThat(response.getBody().getDescription()).isEqualTo(productUpdateDto.getDescription());
        assertThat(response.getBody().getPrice()).isEqualTo(productUpdateDto.getPrice());
        //check from database
        Product productFromDatabase = productRepository.findAll().get(0);
        assertThat(productFromDatabase.getTitle()).isEqualTo(productUpdateDto.getTitle());
        assertThat(productFromDatabase.getDescription()).isEqualTo(productUpdateDto.getDescription());
        assertThat(productFromDatabase.getPrice()).isEqualTo(productUpdateDto.getPrice());
    }

    @Test
    public void testDelete() {
        //given
        Product product = createTestProduct("product", "desc", new BigDecimal("100.00"));
        productRepository.save(product);
        String url = PRODUCT_URL + "/" + product.getId();
        //when
        ResponseEntity<Void> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productRepository.count()).isEqualTo(0);
    }
}
