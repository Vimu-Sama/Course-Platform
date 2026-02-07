package com.vimarsh.Course_Platform.Service;

import com.vimarsh.Course_Platform.Exception.AlreadyEnrolledException;
import com.vimarsh.Course_Platform.Exception.ResourceNotFoundException;
import com.vimarsh.Course_Platform.Model.Course;
import com.vimarsh.Course_Platform.Model.Enrollment;
import com.vimarsh.Course_Platform.Model.User;
import com.vimarsh.Course_Platform.Repository.CourseRepository;
import com.vimarsh.Course_Platform.Repository.EnrollmentRepository;
import com.vimarsh.Course_Platform.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EnrollmentService {

    private static final Logger log = LoggerFactory.getLogger(EnrollmentService.class);
    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private UserRepository userRepo;

    public Enrollment enrollUserInCourse(String courseId, String userEmail) {
        log.info("[ENROLLMENT-SERVICE] [METHOD: enrollUserInCourse] [STEP: START] courseId={} userEmail={}",
                courseId, userEmail);

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course with id '" + courseId + "' does not exist"));

        log.info("[ENROLLMENT-SERVICE] [METHOD: enrollUserInCourse] [STEP: COURSE_FOUND] courseId={}", courseId);

        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        log.info("[ENROLLMENT-SERVICE] [METHOD: enrollUserInCourse] [STEP: USER_FOUND] userId={}", user.getId());

        if (enrollmentRepo.existsByUserIdAndCourseId(user.getId(), course.getId())) {
            log.warn("[ENROLLMENT-SERVICE] [METHOD: enrollUserInCourse] [STEP: ALREADY_ENROLLED] userId={} courseId={}",
                    user.getId(), course.getId());
            throw new AlreadyEnrolledException(
                    "You are already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(Instant.now());

        log.info("[ENROLLMENT-SERVICE] [METHOD: enrollUserInCourse] [STEP: SAVING_ENROLLMENT] userId={} courseId={}",
                user.getId(), course.getId());

        Enrollment savedEnrollment = enrollmentRepo.save(enrollment);

        log.info("[ENROLLMENT-SERVICE] [METHOD: enrollUserInCourse] [STEP: SUCCESS] enrollmentId={}",
                savedEnrollment.getId());

        return savedEnrollment;
    }

    public Enrollment getEnrollmentOrThrow(int userId, String courseId) {

        log.info("[ENROLLMENT-SERVICE] [METHOD: getEnrollmentOrThrow] [STEP: START] userId={} courseId={}",
                userId, courseId);

        return enrollmentRepo.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> {
                    log.error("[ENROLLMENT-SERVICE] [METHOD: getEnrollmentOrThrow] [STEP: NOT_FOUND] userId={} courseId={}",
                            userId, courseId);
                    return new ResourceNotFoundException("Enrollment not found");
                });
    }
}