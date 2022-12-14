package com.Tutorial.Comments.Tutorial.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
    // members /////////////////////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_generator")
    private long id;
   
    @Lob
    private String content;
    
    @ManyToOne  (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "tutorial_id", nullable = false)
    @OnDelete   (action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Tutorial tutorial;
    
    // methods /////////////////////////////////////////////////////////////////////////////////
    public Comment(){}
    public Comment(Tutorial tutorial, String content) {
        this.tutorial = tutorial;
        this.content  = content;
    }
    
    public void setTutorial(Tutorial tutorial) { this.tutorial = tutorial;}
    public void setContent(String content)     { this.content = content;}
    public String getContent() { return content;}
}
