package com.vimarsh.Course_Platform.Controller;

import com.vimarsh.Course_Platform.DataTransferObjects.ProgressResponse;
import com.vimarsh.Course_Platform.Model.SubTopicProgress;
import com.vimarsh.Course_Platform.Model.User;
import com.vimarsh.Course_Platform.Repository.UserRepository;
import com.vimarsh.Course_Platform.Service.ProgressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProgressController {

    private static final Logger log = LoggerFactory.getLogger(ProgressController.class);
    @Autowired
    UserRepository userRepo ;

    @Autowired
    ProgressService progressService ;

    @PostMapping("/subtopics/{subtopicId}/complete")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<?> complete(
            @PathVariable String subtopicId,
            Authentication authentication) {
        log.info("[PROGRESS] [POST /api/subtopics/{}/complete] " +
                        "[METHOD: ProgressController.complete] [STEP: START] subtopicId={}",
                subtopicId, subtopicId);

        String email = authentication.getName();
        User user = userRepo.findByEmail(email).get();

        log.info("[PROGRESS] [POST /api/subtopics/{}/complete] " +
                        "[METHOD: ProgressController.complete] [STEP: SERVICE_CALL_START] userEmail={} subtopicId={}",
                subtopicId, email, subtopicId);

        SubTopicProgress p = progressService.markCompleted(subtopicId, user);

        log.info("[PROGRESS] [POST /api/subtopics/{}/complete] " +
                        "[METHOD: ProgressController.complete] [STEP: SUCCESS] userEmail={} subtopicId={}",
                subtopicId, email, subtopicId);

        return ResponseEntity.ok(Map.of(
                "subtopicId", p.getSubtopic().getId(),
                "completed", true,
                "completedAt", p.getCompletedAt()
        ));
    }

    @GetMapping("/enrollments/{enrollmentId}/progress")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<ProgressResponse> progress(
            @PathVariable int enrollmentId,
            Authentication authentication) {

        log.info("[PROGRESS] [GET /api/enrollments/{}/progress] " +
                        "[METHOD: ProgressController.progress] [STEP: START] enrollmentId={}",
                enrollmentId, enrollmentId);

        String email = authentication.getName();
        User user = userRepo.findByEmail(email).get();

        log.info("[PROGRESS] [GET /api/enrollments/{}/progress] " +
                        "[METHOD: ProgressController.progress] [STEP: SERVICE_CALL_START] userEmail={} enrollmentId={}",
                enrollmentId, email, enrollmentId);

        ProgressResponse res =
                progressService.getProgress(enrollmentId);

        log.info("[PROGRESS] [GET /api/enrollments/{}/progress] " +
                        "[METHOD: ProgressController.progress] [STEP: SUCCESS] userEmail={} percentage={}",
                enrollmentId, email, res.getCompletionPercentage());

        return ResponseEntity.ok(res);
    }
}
