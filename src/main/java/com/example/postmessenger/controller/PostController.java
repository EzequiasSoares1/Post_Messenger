package com.example.postmessenger.controller;
import com.example.postmessenger.entity.Post;
import com.example.postmessenger.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Post> processPost(@PathVariable Long postId) {
        verifyId(postId);
        Post posts = postService.savePost(postId);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> disablePost(@PathVariable Long postId) {
        try {
            postService.desablePost(postId);
            return ResponseEntity.ok("Post disabled successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{postId}")
    public ResponseEntity<String> reprocessPost(@PathVariable Long postId) {
        try {

            Post reprocessedPost = postService.reprocessPost(postId);
            return ResponseEntity.ok("Post reprocessed successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private void verifyId(Long id){
        if( id <= 0 || id >= 101){
            throw new RuntimeException("Post not found");
        }
    }
}



