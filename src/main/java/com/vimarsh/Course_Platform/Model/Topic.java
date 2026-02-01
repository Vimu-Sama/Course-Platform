package com.vimarsh.Course_Platform.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id ;

    private String title ;

    @OneToMany(mappedBy = "topic")
    private List<SubTopic> subTopics ;

    @ManyToOne
    @JoinColumn(name= "id")
    private Course course ;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getTopic() {
        return course;
    }

    public void setTopic(Course course) {
        this.course = course;
    }
}
