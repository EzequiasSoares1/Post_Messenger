package com.example.postmessenger.service;
import com.example.postmessenger.entity.Comment;
import com.example.postmessenger.entity.History;
import com.example.postmessenger.entity.Post;
import com.example.postmessenger.entity.Status;
import com.example.postmessenger.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ApiPostService apiPostService;
    private final ApiCommentService apiCommentService;

    @Autowired
    public PostService(PostRepository postRepository,ApiCommentService apiCommentService,
                       ApiPostService apiPostService) {
        this.postRepository = postRepository;
        this.apiPostService = apiPostService;
        this.apiCommentService = apiCommentService;

    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    public Post savePost(Long id) {
        Post post = findById(id);

        if(post != null ){
           throw new RuntimeException("Post ID " + id + " already exists.");
        }

        List<History> histories = new ArrayList<>();
        histories.add(new History(Status.CREATED));

        try{

            post =  apiPostService.fetchPost(id).block();
            histories.add(new History(Status.POST_FIND));

            if(post == null){
                throw new Exception("Post Not Found");
            }

            histories.add(new History(Status.POST_OK));


            List<Comment> comments = apiCommentService.fetchCommentsForPost(post.getId())
                    .collectList().block();
            histories.add(new History(Status.COMMENTS_FIND));

            post.setComments(comments);
            histories.add(new History(Status.COMMENTS_OK));

            histories.add(new History(Status.FAILED));

        } catch (Exception e){
            histories.add(new History(Status.FAILED));
        }

        post.setHistory(histories);
        post.setCurrentStatus(histories.get(histories.size() - 1).getStatus());

        return postRepository.save(post);
    }

    public void desablePost(Long id) {
        Post post = findById(id);

        if(post == null){
            throw new RuntimeException("Post ID " + id + " not found");
        } else if(post.getCurrentStatus().equals(Status.DISABLED)){
            throw new RuntimeException("the post is already disabled");
        }

        post.getHistory().add(new History(Status.DISABLED));
        post.setCurrentStatus(Status.DISABLED);
        postRepository.save(post);
    }

    public Post reprocessPost(Long id) {
        Post post = findById(id);

        if(post == null){
            throw new RuntimeException("Post ID " + id + " not found");
        } else if(!post.getCurrentStatus().equals(Status.DISABLED) || !post.getCurrentStatus().equals(Status.ENABLED) ||
                post.getCurrentStatus().equals(Status.FAILED)){
            throw new RuntimeException("Post cannot be processed");
        }

        delete(id);
        return savePost(id);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post update(Long id, Post updatedPost) {
        Post existingPost = findById(id);
        if (existingPost != null) {
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setBody(updatedPost.getBody());
            existingPost.setComments(updatedPost.getComments());
            existingPost.setHistory(updatedPost.getHistory());
            return postRepository.save(existingPost);
        } else {
            throw new IllegalArgumentException("Post not found with id: " + id);
        }
    }


}
