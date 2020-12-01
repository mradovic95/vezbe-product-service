package com.raf.productservice.service.impl;

import com.raf.productservice.domain.Product;
import com.raf.productservice.dto.CommentCreateDto;
import com.raf.productservice.dto.CommentDto;
import com.raf.productservice.exception.NotFoundException;
import com.raf.productservice.mapper.CommentMapper;
import com.raf.productservice.repository.CommentRepository;
import com.raf.productservice.repository.ProductRepository;
import com.raf.productservice.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ProductRepository productRepository;
    private CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ProductRepository productRepository
            , CommentMapper commentMapper) {

        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public Page<CommentDto> findAllByProductId(Long productId, Pageable pageable) {
        return commentRepository.findAllByProduct_Id(productId, pageable)
                .map(commentMapper::commentToCommentDto);
    }

    @Override
    public CommentDto addCommentOnProduct(Long productId, CommentCreateDto commentCreateDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id: %d not found.", productId)));
        //Add comment to database
        return commentMapper.commentToCommentDto(commentRepository
                .save(commentMapper.commentCreateDtoToComment(commentCreateDto, product)));
    }
}
