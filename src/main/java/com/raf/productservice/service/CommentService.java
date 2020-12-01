package com.raf.productservice.service;

import com.raf.productservice.dto.CommentCreateDto;
import com.raf.productservice.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<CommentDto> findAllByProductId(Long productId, Pageable pageable);

    CommentDto addCommentOnProduct(Long productId, CommentCreateDto commentAddDto);
}
