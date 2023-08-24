package com.example.postmessenger.service;
import com.example.postmessenger.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ApiCommentService {
    private final WebClient webClient;

    @Autowired
    public ApiCommentService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Comment> fetchCommentsForPost(Long postId) {
        return webClient.get()
                .uri("/posts/{postId}/comments", postId)
                .retrieve()
                .bodyToFlux(Comment.class);
    }

}

