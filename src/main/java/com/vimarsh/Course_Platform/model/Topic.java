package com.vimarsh.Course_Platform.model;

import jakarta.persistence.*;

@Entity
class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;

    private String title ;

    @Column(columnDefinition = "TEXT")
    private String content; // markdown

    @ManyToOne
    private Topic topic ;

    public int getId() {
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
