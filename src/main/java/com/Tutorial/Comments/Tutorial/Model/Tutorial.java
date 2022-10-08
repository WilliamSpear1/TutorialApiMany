package com.Tutorial.Comments.Tutorial.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tutorials")
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tutorial_generator")
    private long id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "published")
    private boolean published;
    
    public Tutorial() {}
    
    public Tutorial(String title, String description, boolean published) {
        this.title       = title;
        this.description = description;
        this.published   = published;
    }
}
