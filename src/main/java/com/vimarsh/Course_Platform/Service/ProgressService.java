package com.vimarsh.Course_Platform.Service;

import com.vimarsh.Course_Platform.DataTransferObjects.CompletedItemDto;
import com.vimarsh.Course_Platform.DataTransferObjects.ProgressResponse;
import com.vimarsh.Course_Platform.Exception.ForbiddenException;
import com.vimarsh.Course_Platform.Exception.ResourceNotFoundException;
import com.vimarsh.Course_Platform.Model.*;
import com.vimarsh.Course_Platform.Repository.EnrollmentRepository;
import com.vimarsh.Course_Platform.Repository.SubTopicProgressRepository;
import com.vimarsh.Course_Platform.Repository.SubTopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {

    private static final Logger log = LoggerFactory.getLogger(ProgressService.class);
    @Autowired
    private SubTopicProgressRepository progressRepo;

    @Autowired
    private SubTopicRepository subTopicRepo;

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    public SubTopicProgress markCompleted(String subtopicId, User user) {
        log.info("[PROGRESS-SERVICE] [METHOD: markCompleted] [STEP: START] subtopicId={} userId={}",
                subtopicId, user.getId());

        SubTopic subtopic = subTopicRepo.findById(subtopicId)
                .orElseThrow(() -> {
                    log.error("[PROGRESS-SERVICE] [METHOD: markCompleted] [STEP: SUBTOPIC_NOT_FOUND] subtopicId={}", subtopicId);
                    return new ResourceNotFoundException("Subtopic with id '" + subtopicId + "' does not exist");
                });

        boolean enrolled = enrollmentRepo.existsByUserIdAndCourseId(user.getId(), subtopic.getTopic().getCourse().getId());

        if (!enrolled) {
            log.warn("[PROGRESS-SERVICE] [METHOD: markCompleted] [STEP: ENROLLMENT_CHECK_FAIL] userId={} courseId={}",
                    user.getId(), subtopic.getTopic().getCourse().getId());
            throw new ForbiddenException("You must be enrolled in this course to mark subtopics as complete");
        }

        Optional<SubTopicProgress> existing = progressRepo.findByUserAndSubtopic(user, subtopic);

        if (existing.isPresent()) {
            log.info("[PROGRESS-SERVICE] [METHOD: markCompleted] [STEP: ALREADY_COMPLETED] userId={} subtopicId={}",
                    user.getId(), subtopicId);
            return existing.get();
        }

        SubTopicProgress progress = new SubTopicProgress();
        progress.setUser(user);
        progress.setSubtopic(subtopic);
        progress.setCompleted(true);
        progress.setCompletedAt(Instant.now());

        log.info("[PROGRESS-SERVICE] [METHOD: markCompleted] [STEP: SAVING_PROGRESS] userId={} subtopicId={}",
                user.getId(), subtopicId);

        return progressRepo.save(progress);
    }

    public ProgressResponse getProgress(int enrollId) {

        log.info("[PROGRESS-SERVICE] [METHOD: getProgress] [STEP: START] enrollId={}", enrollId);

        Enrollment enrollment = enrollmentRepo
                .findById(enrollId)
                .orElseThrow(() -> {
                    log.error("[PROGRESS-SERVICE] [METHOD: getProgress] [STEP: ENROLLMENT_NOT_FOUND] enrollId={}", enrollId);
                    return new ResourceNotFoundException("Enrollment not found");
                });

        User user = enrollment.getUser();
        Course course = enrollment.getCourse();

        log.info("[PROGRESS-SERVICE] [METHOD: getProgress] [STEP: CALCULATING_PROGRESS] userId={} courseId={}",
                user.getId(), course.getId());

        long total = course.getTopics().stream()
                .flatMap(topic -> topic.getSubTopics().stream())
                .count();

        long completed = progressRepo
                .countByUserAndSubtopic_Topic_CourseAndCompletedTrue(user, course);

        double percentage = total == 0 ? 0 : (completed * 100.0) / total;

        log.info("[PROGRESS-SERVICE] [METHOD: getProgress] [STEP: FETCHING_COMPLETED_LIST] userId={} courseId={}",
                user.getId(), course.getId());

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

        log.info("[PROGRESS-SERVICE] [METHOD: getProgress] [STEP: SUCCESS] enrollId={} completion={}%%",
                enrollId, percentage);

        return new ProgressResponse(
                enrollment,
                total,
                completed,
                percentage,
                completedList
        );
    }
}

