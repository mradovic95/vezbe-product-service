package com.raf.productservice.controller;

import com.raf.productservice.domain.Comment;
import com.raf.productservice.domain.Product;
import com.raf.productservice.dto.CommentCreateDto;
import com.raf.productservice.dto.CommentDto;
import com.raf.productservice.repository.CommentRepository;
import com.raf.productservice.repository.ProductRepository;
import com.raf.productservice.wrapper.CommentPageWrapper;
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

import static com.raf.productservice.helper.CommentTestHelper.*;
import static com.raf.productservice.helper.ProductTestHelper.createTestProduct;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentControllerTest {

    private static final String COMMENT_URL = "/product/%d/comment";

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        //given
        Product product = createTestProduct("product1", "desc1", new BigDecimal("100.00"));
        productRepository.save(product);
        Comment comment = createTestComment("text", "user", 5, product);
        commentRepository.save(comment);
        //when
        ResponseEntity<CommentPageWrapper> response = testRestTemplate
                .exchange(String.format(COMMENT_URL, product.getId()), HttpMethod.GET, null, CommentPageWrapper.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent().size()).isEqualTo(1);
        assertCommentDto(response.getBody().getContent().get(0), comment.getId(), comment.getText(), comment.getUser(),
                comment.getProductRating());
    }

    @Test
    public void testFindAllWithSpecifiedPageable() {
        //given
        Product product = createTestProduct("product1", "desc1", new BigDecimal("100.00"));
        productRepository.save(product);
        Comment comment1 = createTestComment("text1", "user1", 5, product);
        Comment comment2 = createTestComment("text1", "user2", 10, product);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        //when
        ResponseEntity<CommentPageWrapper> response = testRestTemplate
                .exchange(String.format(COMMENT_URL + "?page=1&size=1", product.getId()), HttpMethod.GET, null, CommentPageWrapper.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent().size()).isEqualTo(1);
        assertCommentDto(response.getBody().getContent().get(0), comment2.getId(), comment2.getText(), comment2.getUser(),
                comment2.getProductRating());
    }

    @Test
    public void testAdd() {
        //given
        Product product = createTestProduct("product1", "prodcut1 description", BigDecimal.TEN);
        productRepository.save(product);
        CommentCreateDto commentCreateDto = createTestCommentCreateDto("text", "user", 5);
        HttpEntity<CommentCreateDto> request = new HttpEntity<>(commentCreateDto);
        //when
        ResponseEntity<CommentDto> response = testRestTemplate
                .exchange(String.format(COMMENT_URL, product.getId()), HttpMethod.POST, request, CommentDto.class);
        //then
        //check response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getText()).isEqualTo(commentCreateDto.getText());
        assertThat(response.getBody().getUser()).isEqualTo(commentCreateDto.getUser());
        assertThat(response.getBody().getProductRating()).isEqualTo(commentCreateDto.getProductRating());
        //check from database
        Comment commentFromDatabase = commentRepository.findAll().get(0);
        assertThat(commentFromDatabase.getText()).isEqualTo(commentFromDatabase.getText());
        assertThat(commentFromDatabase.getUser()).isEqualTo(commentFromDatabase.getUser());
        assertThat(commentFromDatabase.getProductRating()).isEqualTo(commentFromDatabase.getProductRating());
    }
}
