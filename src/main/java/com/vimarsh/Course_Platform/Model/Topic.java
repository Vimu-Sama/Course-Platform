package com.vimarsh.Course_Platform.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Topic {
    @Id
    private String id;

    private String title;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<SubTopic> subTopics;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // Getters and Setters
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<SubTopic> getSubTopics() { return subTopics; }
    public void setSubTopics(List<SubTopic> subTopics) { this.subTopics = subTopics; }

    // Updated naming for clarity
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}