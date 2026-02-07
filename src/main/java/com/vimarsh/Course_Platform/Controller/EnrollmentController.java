package com.vimarsh.Course_Platform.Controller;

import com.vimarsh.Course_Platform.Model.Enrollment;
import com.vimarsh.Course_Platform.Service.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EnrollmentController {

    private static final Logger log = LoggerFactory.getLogger(EnrollmentController.class);
    @Autowired
    private EnrollmentService enrollmentService ;

    @PostMapping("/api/courses/{courseId}/enroll")
    public ResponseEntity<?> enroll(
            @PathVariable String courseId,
            Authentication authentication) {
        log.info("[ENROLLMENT] [POST /api/courses/{}/enroll] " +
                        "[METHOD: CourseController.enroll] [STEP: START] courseId={}",
                courseId, courseId);

        String email = authentication.getName();

        log.info("[ENROLLMENT] [POST /api/courses/{}/enroll] " +
                        "[METHOD: CourseController.enroll] [STEP: SERVICE_CALL_START] userEmail={} courseId={}",
                courseId, email, courseId);

        Enrollment e = enrollmentService.enrollUserInCourse(courseId, email);

        log.info("[ENROLLMENT] [POST /api/courses/{}/enroll] " +
                        "[METHOD: CourseController.enroll] [STEP: ENROLLMENT_SUCCESS] userEmail={} enrollmentId={}",
                courseId, email, e.getId());

        Map<String, Object> res = Map.of(
                "enrollmentId", e.getId(),
                "courseId", e.getCourse().getId(),
                "courseTitle", e.getCourse().getTitle(),
                "enrolledAt", e.getEnrolledAt()
        );

        return ResponseEntity.ok(res);
    }
}
