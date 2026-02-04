package com.vimarsh.Course_Platform.Controller;

import com.vimarsh.Course_Platform.DataTransferObjects.CourseSummaryDTO;
import com.vimarsh.Course_Platform.Model.Course;
import com.vimarsh.Course_Platform.Repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CourseController {

    CourseRepository courseRepository ;

    public CourseController(CourseRepository courseRepository){
        this.courseRepository = courseRepository ;
    }

    @GetMapping("/courses")
    public ResponseEntity<Map<String, List<CourseSummaryDTO>>> getCourseList() {
        List<CourseSummaryDTO> summaries = courseRepository.findAllCourseSummaries();
        return ResponseEntity.ok(Collections.singletonMap("courses", summaries));
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        return courseRepository.findCourseWithDetails(id)
                .map(course -> ResponseEntity.ok(course))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchForKeyword(@RequestParam("q") String query) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<Course> results = courseRepository.searchCourses(query);
        return ResponseEntity.ok(results);
    }

}
