package com.vimarsh.Course_Platform.Controller;

import com.vimarsh.Course_Platform.Model.Enrollment;
import com.vimarsh.Course_Platform.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService ;

    @PostMapping("/api/courses/{courseId}/enroll")
    public ResponseEntity<?> enroll(
            @PathVariable String courseId,
            Authentication authentication) {

        String email = authentication.getName();

        Enrollment e = enrollmentService.enrollUserInCourse(courseId, email);

        Map<String, Object> res = Map.of(
                "enrollmentId", e.getId(),
                "courseId", e.getCourse().getId(),
                "courseTitle", e.getCourse().getTitle(),
                "enrolledAt", e.getEnrolledAt()
        );

        return ResponseEntity.ok(res);
    }
}
