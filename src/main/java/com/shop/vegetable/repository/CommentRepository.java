package com.shop.vegetable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.vegetable.entity.Comment;
import com.shop.vegetable.entity.Order;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select o from Comment o where o.product.id = ?1")
    List<Comment> findAllCommentByProId(Long id);

    @Query("select o from Comment o where o.id = ?1")
    Comment findId(Long id);
}
