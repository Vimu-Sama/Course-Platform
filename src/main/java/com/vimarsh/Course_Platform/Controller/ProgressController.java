package com.vimarsh.Course_Platform.Controller;

import com.vimarsh.Course_Platform.DataTransferObjects.ProgressResponse;
import com.vimarsh.Course_Platform.Model.SubTopicProgress;
import com.vimarsh.Course_Platform.Model.User;
import com.vimarsh.Course_Platform.Repository.UserRepository;
import com.vimarsh.Course_Platform.Service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProgressController {

    @Autowired
    UserRepository userRepo ;

    @Autowired
    ProgressService progressService ;

    @PostMapping("/subtopics/{subtopicId}/complete")
    public ResponseEntity<?> complete(
            @PathVariable String subtopicId,
            Authentication authentication) {

        User user = userRepo.findByEmail(authentication.getName()).get();

        SubTopicProgress p = progressService.markCompleted(subtopicId, user);

        return ResponseEntity.ok(Map.of(
                "subtopicId", p.getSubtopic().getId(),
                "completed", true,
                "completedAt", p.getCompletedAt()
        ));
    }

    @GetMapping("/enrollments/{enrollmentId}/progress")
    public ResponseEntity<ProgressResponse> progress(
            @PathVariable int enrollmentId,
            Authentication authentication) {

        User user = userRepo.findByEmail(authentication.getName()).get();

        ProgressResponse res =
                progressService.getProgress(enrollmentId);

        return ResponseEntity.ok(res);
    }

}
