package com.example.postmessenger.service;
import com.example.postmessenger.entity.Comment;
import com.example.postmessenger.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).get();
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment update(Long id, Comment updatedComment) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment existingComment = optionalComment.get();
            existingComment.setBody(updatedComment.getBody());
            return commentRepository.save(existingComment);
        } else {
            throw new IllegalArgumentException("Comment not found with id: " + id);
        }
    }
}
