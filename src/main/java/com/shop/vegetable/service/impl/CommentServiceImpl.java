package com.shop.vegetable.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.vegetable.dto.CommentDto;
import com.shop.vegetable.entity.Comment;
import com.shop.vegetable.repository.CommentRepository;
import com.shop.vegetable.service.CommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
     private final CommentRepository commentRepository;

    @Override
    public Comment save(CommentDto commentdto) {
        Comment cm= new Comment();
        cm.setContent(commentdto.getContent());
        cm.setCommentDate(commentdto.getCommentDate());
        cm.setProduct(commentdto.getProduct());
        cm.setStart(commentdto.getStart());
        cm.setUsers(commentdto.getUsers());
        return commentRepository.save(cm);
    }

    @Override
    public List<Comment> findAll(Long id) {     
        return commentRepository.findAllCommentByProId(id);
    }

    @Override
    public void deletComment(Long id) {
        Comment fi=commentRepository.getReferenceById(id);
       
        commentRepository.delete(fi);
    }
    
}
