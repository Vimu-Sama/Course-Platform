package com.vimarsh.Course_Platform.Controller;

import com.vimarsh.Course_Platform.DataTransferObjects.CourseSummaryDTO;
import com.vimarsh.Course_Platform.Model.Course;
import com.vimarsh.Course_Platform.Repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public ResponseEntity<Map<String, List<CourseSummaryDTO>>> getCourseList() {
        log.info("[COURSE-CONTROLLER] [GET /api/courses] [METHOD: getCourseList] [STEP: START]");

        List<CourseSummaryDTO> summaries = courseRepository.findAllCourseSummaries();

        log.info("[COURSE-CONTROLLER] [GET /api/courses] [METHOD: getCourseList] [STEP: SUCCESS] count={}",
                summaries.size());

        return ResponseEntity.ok(Collections.singletonMap("courses", summaries));
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {

        log.info("[COURSE-CONTROLLER] [GET /api/courses/{}] [METHOD: getCourseById] [STEP: START] courseId={}",
                id, id);

        return courseRepository.findCourseWithDetails(id)
                .map(course -> {
                    log.info("[COURSE-CONTROLLER] [GET /api/courses/{}] [METHOD: getCourseById] [STEP: SUCCESS] courseId={}",
                            id, id);
                    return ResponseEntity.ok(course);
                })
                .orElseGet(() -> {
                    log.warn("[COURSE-CONTROLLER] [GET /api/courses/{}] [METHOD: getCourseById] [STEP: NOT_FOUND] courseId={}",
                            id, id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchForKeyword(@RequestParam("q") String query) {

        log.info("[COURSE-CONTROLLER] [GET /api/search] [METHOD: searchForKeyword] [STEP: START] query='{}'",
                query);

        if (query == null || query.trim().isEmpty()) {
            log.info("[COURSE-CONTROLLER] [GET /api/search] [METHOD: searchForKeyword] [STEP: EMPTY_QUERY]");
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<Course> results = courseRepository.searchCourses(query);

        log.info("[COURSE-CONTROLLER] [GET /api/search] [METHOD: searchForKeyword] [STEP: SUCCESS] query='{}' resultsFound={}",
                query, results.size());

        return ResponseEntity.ok(results);
    }
}
