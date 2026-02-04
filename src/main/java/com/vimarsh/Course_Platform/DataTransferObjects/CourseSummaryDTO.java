package com.vimarsh.Course_Platform.DataTransferObjects;

public record CourseSummaryDTO(
    String id ,
    String title ,
    String description ,
    Long topicCount,
    Long subTopicCount
) {}
