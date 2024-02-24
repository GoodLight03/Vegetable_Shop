package com.shop.vegetable.service;

import java.util.List;

import com.shop.vegetable.dto.CommentDto;
import com.shop.vegetable.entity.Comment;
import com.shop.vegetable.entity.Order;

public interface CommentService {
    Comment save(CommentDto commentdto);

    List<Comment> findAll(Long id);

    void deletComment(Long id);
}
