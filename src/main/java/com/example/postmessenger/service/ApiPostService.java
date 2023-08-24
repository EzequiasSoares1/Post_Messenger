package com.example.postmessenger.service;
import com.example.postmessenger.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApiPostService {
    private final WebClient webClient;

    @Autowired
    public ApiPostService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Post> fetchPost(Long postId) {
        return webClient.get()
                .uri("/posts/{postId}", postId)
                .retrieve()
                .bodyToMono(Post.class);
    }

    public Flux<Post> fetchAllPosts() {
        return webClient.get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(Post.class);
    }
}