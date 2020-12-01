package com.raf.productservice.mapper;

import com.raf.productservice.domain.Comment;
import com.raf.productservice.domain.Product;
import com.raf.productservice.dto.CommentCreateDto;
import com.raf.productservice.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentDto commentToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setUser(comment.getUser());
        commentDto.setProductRating(comment.getProductRating());
        return commentDto;
    }

    public Comment commentCreateDtoToComment(CommentCreateDto commentCreateDto, Product product) {
        Comment comment = new Comment();
        comment.setText(commentCreateDto.getText());
        comment.setUser(commentCreateDto.getUser());
        comment.setProductRating(commentCreateDto.getProductRating());
        comment.setProduct(product);
        return comment;
    }
}
