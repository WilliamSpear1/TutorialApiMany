package com.Tutorial.Comments.Tutorial.Controller;

import com.Tutorial.Comments.Tutorial.Exception.ResourceNotFoundException;
import com.Tutorial.Comments.Tutorial.Model.Tutorial;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TutorialController {
   // members /////////////////////////////////////////////////////////////////////////////////
   @Autowired
    TutorialRepository tutorialRepository;
    
   // methods /////////////////////////////////////////////////////////////////////////////////
   @GetMapping("/tutorials")
   public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
       List<Tutorial> tutorialLists = new ArrayList<Tutorial>();
    
       if (title == null) tutorialRepository.findAll().forEach(tutorialLists::add);
       else tutorialRepository.findByTitleContaining(title).forEach(tutorialLists::add);
       
       if (tutorialLists.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       else return new ResponseEntity<>(tutorialLists, HttpStatus.OK);
   }
   
   @GetMapping("/tutorials/{id}")
   public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
       Tutorial tutorial = tutorialRepository.findById(id)
         .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
       
       return new ResponseEntity<>(tutorial, HttpStatus.OK);
   }
   
   @PostMapping("/tutorials")
   public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
       Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), true));
       return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
   }
   
   @PutMapping("/tutorials/{id}")
   public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
       Tutorial _tutorial = tutorialRepository.findById(id)
         .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
       
       _tutorial.setTitle(tutorial.getTitle());
       _tutorial.setDescription(tutorial.getDescription());
       _tutorial.setPublished(tutorial.isPublished());
       
       return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
   }
   
   @DeleteMapping("/tutorials/{id}")
   public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
       tutorialRepository.deleteById(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
   
   @DeleteMapping("/tutorials")
   public ResponseEntity<HttpStatus> deleteAllTutorials() {
       tutorialRepository.deleteAll();
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
   
   @DeleteMapping("tutorials/published")
   public ResponseEntity<List<Tutorial>> findByPublished() {
       List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
       
       if(tutorials.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       else return new ResponseEntity<>(tutorials, HttpStatus.OK);
   }
}
