package com.raf.productservice.helper;

import com.raf.productservice.domain.Comment;
import com.raf.productservice.domain.Product;
import com.raf.productservice.dto.CommentCreateDto;
import com.raf.productservice.dto.CommentDto;
import com.raf.productservice.dto.ProductDto;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public final class CommentTestHelper {

    private CommentTestHelper() {

    }

    public static Comment createTestComment(String text, String user, Integer productRating, Product product) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        comment.setProductRating(productRating);
        comment.setProduct(product);
        return comment;
    }

    public static CommentCreateDto createTestCommentCreateDto(String text, String user, Integer productRating) {
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setText(text);
        commentCreateDto.setUser(user);
        commentCreateDto.setProductRating(productRating);
        return commentCreateDto;
    }

    public static void assertCommentDto(CommentDto commentDto, Long id, String text, String user, Integer rating) {
        assertThat(commentDto.getId()).isEqualTo(id);
        assertThat(commentDto.getText()).isEqualTo(text);
        assertThat(commentDto.getUser()).isEqualTo(user);
        assertThat(commentDto.getProductRating()).isEqualTo(rating);
    }
}
