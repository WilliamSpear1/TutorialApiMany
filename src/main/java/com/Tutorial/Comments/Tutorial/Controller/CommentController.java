package com.Tutorial.Comments.Tutorial.Controller;

import com.Tutorial.Comments.Tutorial.Exception.ResourceNotFoundException;
import com.Tutorial.Comments.Tutorial.Model.Comment;
import com.Tutorial.Comments.Tutorial.Repository.CommentRepository;
import com.Tutorial.Comments.Tutorial.Repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping
public class CommentController {
    
    // members /////////////////////////////////////////////////////////////////////////////////
    @Autowired
    private TutorialRepository tutorialRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @GetMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByTutorialId(@PathVariable(value = "tutorialId") long tutorialId) {
        if (!tutorialRepository.existsById(tutorialId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
        }
        
        List<Comment> comments = commentRepository.findByTutorialId(tutorialId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    
    @GetMapping("/commennts/{id}")
    public ResponseEntity<Comment> getCommentsByTutorialId(@PathVariable(value = "id") long id) {
        Comment comment = commentRepository.findById(id)
          .orElse(() -> new ResourceNotFoundException("Not found Comment with id = " + id));
        
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    
    @PostMapping("/tutorials/{tutorialId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable(value = "tutorialId")long tutorialId,
                                                 @RequestBody Comment commentRequest) {
        Comment comment = tutorialRepository.findById(tutorialId).map(tutorial -> {
            commentRequest.setTutorial(tutorial);
            return commentRepository.save(commentRequest);
        }).orElse(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
    
   @PutMapping("/comments/{id}")
   public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest) {
        Comment comment = commentRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("CommentId" + id + "not found"));
        
        comment.setContent(commentRequest.getContent());
        return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
   }
  
   @DeleteMapping("/comments/{id}")
   public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
        commentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
   
   @DeleteMapping("/tutorials/{tutorialId}/comments")
   public ResponseEntity<List<Comment>> deleteAllCommentsOfTutorial(@PathVariable(value = "tutorialId") long tutorialId) {
        if (!tutorialRepository.existsById(tutorialId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
        }
        
        commentRepository.deleteByTutorialId(tutorialId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}
