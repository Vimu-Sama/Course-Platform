package com.vimarsh.Course_Platform.Model;

import jakarta.persistence.*;

@Entity
public class SubTopic {
    @Id
    // Removed @GeneratedValue because your JSON uses custom IDs like "speed", "velocity"
    private String id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Topic getTopic() { return topic; }
    public void setTopic(Topic topic) { this.topic = topic; }
}

