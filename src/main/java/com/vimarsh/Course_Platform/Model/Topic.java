package com.vimarsh.Course_Platform.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Topic {
    @Id
    private String id;

    private String title;

    @JsonProperty("subtopics")
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<SubTopic> subTopics;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    // Getters and Setters
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Set<SubTopic> getSubTopics() { return subTopics; }
    public void setSubTopics(Set<SubTopic> subTopics) { this.subTopics = subTopics; }

    // Updated naming for clarity
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}