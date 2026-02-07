package com.vimarsh.Course_Platform.DataTransferObjects;

import java.time.Instant;

public class CompletedItemDto {

    private String subtopicId;
    private String subtopicTitle;
    private Instant completedAt;

    public CompletedItemDto(String subtopicId, String subtopicTitle, Instant completedAt) {
        this.subtopicId = subtopicId;
        this.subtopicTitle = subtopicTitle;
        this.completedAt = completedAt;
    }

    public String getSubtopicId() {
        return subtopicId;
    }

    public String getSubtopicTitle() {
        return subtopicTitle;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }
}
