package com.example.postmessenger.controller;
import com.example.postmessenger.entity.Post;
import com.example.postmessenger.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;


import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Post> posts = postService.getAllPosts(page, size);
        return ResponseEntity.ok(posts);
    }


    @PostMapping("/{postId}")
    public ResponseEntity<?> processPost(@PathVariable Long postId) {
        try {
            Post posts = postService.savePost(postId);
            return ResponseEntity.ok(posts);

        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();

        }  catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> disablePost(@PathVariable Long postId) {
        try {

            postService.desablePost(postId);
            return ResponseEntity.ok("Post disabled successfully.");

        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();

        }  catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> reprocessPost(@PathVariable Long postId) {
        try {

            Post reprocessedPost = postService.reprocessPost(postId);
            return ResponseEntity.ok("Post reprocessed successfully");

        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}



