package com.vimarsh.Course_Platform.Service;

import com.vimarsh.Course_Platform.Exception.AlreadyEnrolledException;
import com.vimarsh.Course_Platform.Exception.ResourceNotFoundException;
import com.vimarsh.Course_Platform.Model.Course;
import com.vimarsh.Course_Platform.Model.Enrollment;
import com.vimarsh.Course_Platform.Model.User;
import com.vimarsh.Course_Platform.Repository.CourseRepository;
import com.vimarsh.Course_Platform.Repository.EnrollmentRepository;
import com.vimarsh.Course_Platform.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private UserRepository userRepo;

    public Enrollment enrollUserInCourse(String courseId, String userEmail) {

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course with id '" + courseId + "' does not exist"));

        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (enrollmentRepo.existsByUserIdAndCourseId(user.getId(), course.getId())) {
            throw new AlreadyEnrolledException(
                    "You are already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(Instant.now());

        return enrollmentRepo.save(enrollment);
    }

    public Enrollment getEnrollmentOrThrow(int userId, String courseId) {
        return enrollmentRepo.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment not found"));
    }
}