package com.Tutorial.Comments.Tutorial.Repository;

import com.Tutorial.Comments.Tutorial.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // methods /////////////////////////////////////////////////////////////////////////////////
    List<Comment> findByTutorialId (Long postId);
    void deleteByTutorialId        (long tutorialId);
}
