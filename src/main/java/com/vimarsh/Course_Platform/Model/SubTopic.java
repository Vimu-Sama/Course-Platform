package com.vimarsh.Course_Platform.Model;

import jakarta.persistence.*;

@Entity
public class SubTopic {
    @Id
    private String id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content; // markdown

    @ManyToOne
    @JoinColumn(name= "id")
    private Topic topic;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}

