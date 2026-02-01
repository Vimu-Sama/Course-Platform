package com.vimarsh.Course_Platform.Service;

import com.vimarsh.Course_Platform.Model.Course;
import com.vimarsh.Course_Platform.Repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataImportService {

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public void importCourseData(Course courseFromJson) {
        if (courseRepository.existsById(courseFromJson.getId())) {
            System.out.println("Course " + courseFromJson.getId() + " exists. Skipping...");
            return;
        }

        if (courseFromJson.getTopics() != null) {
            courseFromJson.getTopics().forEach(topic -> {
                topic.setCourse(courseFromJson);
                if (topic.getSubTopics() != null) {
                    topic.getSubTopics().forEach(sub -> {
                        sub.setTopic(topic);
                    });
                }
            });
        }

        // 3. Save the Course. Because of CascadeType.ALL, this saves everything!
        courseRepository.save(courseFromJson);
    }
}