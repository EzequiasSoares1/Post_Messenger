package com.example.postmessenger.service;
import com.example.postmessenger.entity.Comment;
import com.example.postmessenger.entity.History;
import com.example.postmessenger.entity.Post;
import com.example.postmessenger.entity.Status;
import com.example.postmessenger.exception.PostAlreadyDisabledException;
import com.example.postmessenger.exception.PostAlreadyExistsException;
import com.example.postmessenger.exception.PostNotFoundException;
import com.example.postmessenger.exception.PostProcessingException;
import com.example.postmessenger.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
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
        verifyId(id);

        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    public Post savePost(Long id) {
        verifyId(id);

        Post post = findById(id);

        if(post != null ){
           throw new PostAlreadyExistsException("Post ID " + id + " already exists.");
        }

        List<History> histories = new ArrayList<>();
        histories.add(new History(Status.CREATED));

        try{

            post =  apiPostService.fetchPost(id).block();
            histories.add(new History(Status.POST_FIND));

            if(post == null){
                throw new PostNotFoundException("Post ID " + id + " Api not found");
            }

            histories.add(new History(Status.POST_OK));


            List<Comment> comments = apiCommentService.fetchCommentsForPost(post.getId())
                    .collectList().block();
            histories.add(new History(Status.COMMENTS_FIND));

            post.setComments(comments);
            histories.add(new History(Status.COMMENTS_OK));

            histories.add(new History(Status.ENABLED));

        } catch (Exception e){
            histories.add(new History(Status.FAILED));
        }

        post.setHistory(histories);
        post.setCurrentStatus(histories.get(histories.size() - 1).getStatus());

        return postRepository.save(post);
    }

    public void desablePost(Long id) {
        verifyId(id);

        Post post = findById(id);

        if(post == null){
            throw new PostNotFoundException("Post ID " + id + " not found");

        } else if(post.getCurrentStatus().equals(Status.DISABLED)){
            throw new PostAlreadyDisabledException("the post is already disabled");
        }

        post.getHistory().add(new History(Status.DISABLED));
        post.setCurrentStatus(Status.DISABLED);
        postRepository.save(post);
    }

    public Post reprocessPost(Long id) {
        verifyId(id);
        Post post = findById(id);

        if(post == null){
            throw new PostNotFoundException("Post ID " + id + " not found");

        } else if (!(post.getCurrentStatus().equals(Status.DISABLED) ||
            post.getCurrentStatus().equals(Status.ENABLED) ||
            post.getCurrentStatus().equals(Status.FAILED))) {

            throw new PostProcessingException("Post cannot be processed");
        }

        List<History> histories = new ArrayList<>();
        histories.add(new History(Status.UPDATING));
        post.setHistory(histories);

        try{
            postRepository.deleteById(post.getId());

            Post postApi = apiPostService.fetchPost(post.getId()).block();
            histories.add(new History(Status.POST_FIND));

            if(postApi == null){
                throw new NotFoundException("Post Api Not Found");
            }

            histories.add(new History(Status.POST_OK));

            List<Comment> comments = apiCommentService.fetchCommentsForPost(post.getId())
                    .collectList().block();
            histories.add(new History(Status.COMMENTS_FIND));

            post.setComments(comments);
            histories.add(new History(Status.COMMENTS_OK));

            histories.add(new History(Status.ENABLED));

            post.setCurrentStatus(Status.ENABLED);

            return postRepository.save(post);

        } catch (Exception e){
            post.getHistory().add(new History(Status.FAILED));
            post.setCurrentStatus(Status.FAILED);
            postRepository.save(post);
            throw new RuntimeException(e.getMessage());
        }

    }
    public List<Post> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.getContent();
    }
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post update(Long id, Post updatedPost) {
        verifyId(id);

        Post existingPost = findById(id);
        if (existingPost != null) {
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setBody(updatedPost.getBody());
            existingPost.setComments(updatedPost.getComments());
            existingPost.setHistory(updatedPost.getHistory());
            return postRepository.save(existingPost);
        } else {
            throw new PostNotFoundException("Post not found with id: " + id);
        }
    }

    private void verifyId(Long id){
        if( id <= 0 || id >= 101){
            throw new NotFoundException("Post not found");
        }
    }

}
