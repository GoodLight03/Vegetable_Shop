package com.shop.vegetable.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shop.vegetable.dto.CommentDto;
import com.shop.vegetable.dto.TypeDto;
import com.shop.vegetable.entity.Comment;
import com.shop.vegetable.entity.Type;
import com.shop.vegetable.service.CommentService;
import com.shop.vegetable.service.TypeService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;

public class CommentApi {
    @Autowired
    private CommentService commentService;

    // @GetMapping("/all")
    // public ResponseEntity<List<Comment>> findAllType() {
    //     return ResponseEntity.ok(commentService.findAll());
    // }

    @GetMapping("/all/{id}")
    //@Operation(summary = "Get a book by id")
    public ResponseEntity<List<Comment>> findCommentById(@PathVariable long id) {
        return ResponseEntity.ok(commentService.findAll(id));

    }

    @PostMapping("/save")
    //@Operation(summary = "Get a book by id")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDto commentdto) {
        Comment mcd = commentService.save(commentdto);
        try {
            return ResponseEntity.created(new URI("/api/comment/save/" + mcd.getId())).body(mcd);

        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // @PutMapping("/all/{id}")
    // public ResponseEntity<Void> updateType(@RequestBody Type type, @PathVariable long id) {
    //     try {
    //         typeService.update(type);
    //         return ResponseEntity.ok().build();
    //     } catch (EntityNotFoundException ex) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @PatchMapping("/all/{id}")
    // public ResponseEntity<Void> updateMedicineName(@RequestBody String
    // nameString, @PathVariable long id) {
    // try {
    // typeService.updateName(id, nameString);
    // return ResponseEntity.ok().build();
    // } catch (EntityNotFoundException ex) {
    // return ResponseEntity.notFound().build();
    // }
    // }

    @DeleteMapping(path = "/all/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable long id) {
        try {
            commentService.deletComment(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
