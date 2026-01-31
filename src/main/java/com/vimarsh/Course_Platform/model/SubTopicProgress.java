package com.vimarsh.Course_Platform.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "subtopic_id"})})
class SubTopicProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private SubTopic subtopic;

    private boolean completed;
    private Instant completedAt;

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubTopic getSubtopic() {
        return subtopic;
    }

    public void setSubtopic(SubTopic subtopic) {
        this.subtopic = subtopic;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }
}

