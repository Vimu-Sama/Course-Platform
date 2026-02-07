package com.vimarsh.Course_Platform.Service;

import com.vimarsh.Course_Platform.DataTransferObjects.CompletedItemDto;
import com.vimarsh.Course_Platform.DataTransferObjects.ProgressResponse;
import com.vimarsh.Course_Platform.Exception.ForbiddenException;
import com.vimarsh.Course_Platform.Exception.ResourceNotFoundException;
import com.vimarsh.Course_Platform.Model.*;
import com.vimarsh.Course_Platform.Repository.EnrollmentRepository;
import com.vimarsh.Course_Platform.Repository.SubTopicProgressRepository;
import com.vimarsh.Course_Platform.Repository.SubTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {

    @Autowired
    private SubTopicProgressRepository progressRepo;

    @Autowired
    private SubTopicRepository subTopicRepo;

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    public SubTopicProgress markCompleted(String subtopicId, User user) {

        SubTopic subtopic = subTopicRepo.findById(subtopicId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subtopic with id '" + subtopicId + "' does not exist"));

        boolean enrolled = enrollmentRepo.existsByUserIdAndCourseId(user.getId(), subtopic.getTopic().getCourse().getId());
        if (!enrolled) {
            throw new ForbiddenException(
                    "You must be enrolled in this course to mark subtopics as complete");
        }

        Optional<SubTopicProgress> existing =
                progressRepo.findByUserAndSubtopic(user, subtopic);

        if (existing.isPresent()) {
            return existing.get();
        }

        SubTopicProgress progress = new SubTopicProgress();
        progress.setUser(user);
        progress.setSubtopic(subtopic);
        progress.setCompleted(true);
        progress.setCompletedAt(Instant.now());

        return progressRepo.save(progress);
    }

    public ProgressResponse getProgress(int enrollId) {

        Enrollment enrollment = enrollmentRepo
                .findById(enrollId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Enrollment not found"));

        User user = enrollment.getUser();
        Course course = enrollment.getCourse();

        long total = course.getTopics().stream()
                .flatMap(topic -> topic.getSubTopics().stream())
                .count();

        long completed = progressRepo
                .countByUserAndSubtopic_Topic_CourseAndCompletedTrue(user, course);

        double percentage = total == 0 ? 0 :
                (completed * 100.0) / total;

        List<CompletedItemDto> completedList =
                progressRepo
                        .findByUserAndSubtopic_Topic_CourseAndCompletedTrue(user, course)
                        .stream()
                        .map(p -> new CompletedItemDto(
                                p.getSubtopic().getId(),
                                p.getSubtopic().getTitle(),
                                p.getCompletedAt()
                        ))
                        .toList();

        return new ProgressResponse(
                enrollment,
                total,
                completed,
                percentage,
                completedList
        );

    }

}

