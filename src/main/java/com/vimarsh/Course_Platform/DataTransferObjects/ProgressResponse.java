package com.vimarsh.Course_Platform.DataTransferObjects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vimarsh.Course_Platform.Model.Enrollment;
import java.util.List;

@JsonPropertyOrder({
        "enrollmentId",
        "courseId",
        "courseTitle",
        "totalSubtopics",
        "completedSubtopics",
        "completionPercentage",
        "completedItems"
})
public class ProgressResponse {

    private int enrollmentId;
    private String courseId;
    private String courseTitle;
    private long totalSubtopics;
    private long completedSubtopics;
    private double completionPercentage;
    private List<CompletedItemDto> completedItems;

    public ProgressResponse(Enrollment enrollment,
                            long totalSubtopics,
                            long completedSubtopics,
                            double completionPercentage,
                            List<CompletedItemDto> completedItems) {

        this.enrollmentId = enrollment.getId();
        this.courseId = enrollment.getCourse().getId();
        this.courseTitle = enrollment.getCourse().getTitle();
        this.totalSubtopics = totalSubtopics;
        this.completedSubtopics = completedSubtopics;
        this.completionPercentage = Math.round(completionPercentage * 100.0) / 100.0;
        this.completedItems = completedItems;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public long getTotalSubtopics() {
        return totalSubtopics;
    }

    public long getCompletedSubtopics() {
        return completedSubtopics;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public List<CompletedItemDto> getCompletedItems() {
        return completedItems;
    }
}
