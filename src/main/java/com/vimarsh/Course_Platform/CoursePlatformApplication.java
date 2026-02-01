package com.vimarsh.Course_Platform;

import com.vimarsh.Course_Platform.Model.Course;
import com.vimarsh.Course_Platform.Service.DataImportService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class CoursePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoursePlatformApplication.class, args);
	}
    @Bean
    CommandLineRunner runner(DataImportService dataImportService, ObjectMapper objectMapper) {
        return args -> {
            ClassPathResource resource = new ClassPathResource("seed_data/courses.json");

            try (InputStream inputStream = resource.getInputStream()) {
                JsonNode rootNode = objectMapper.readTree(inputStream);

                JsonNode coursesNode = rootNode.get("courses");

                if (coursesNode != null && coursesNode.isArray()) {
                    List<Course> courses = objectMapper.readerForListOf(Course.class)
                            .readValue(coursesNode);

                    // 5. Import each course
                    for (Course course : courses) {
                        dataImportService.importCourseData(course);
                    }
                    System.out.println(">>> successfully imported " + courses.size() + " courses.");
                }
            } catch (Exception e) {
                System.err.println(">>> Failed to import data: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
