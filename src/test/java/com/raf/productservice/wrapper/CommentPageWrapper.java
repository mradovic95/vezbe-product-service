package com.raf.productservice.wrapper;

import com.raf.productservice.dto.CommentDto;

import java.util.List;

public class CommentPageWrapper {

    private List<CommentDto> content;

    public List<CommentDto> getContent() {
        return content;
    }

    public void setContent(List<CommentDto> content) {
        this.content = content;
    }
}
