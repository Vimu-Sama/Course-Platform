package com.vimarsh.Course_Platform.Repository;

import com.vimarsh.Course_Platform.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    Optional<Enrollment> findByUserIdAndCourseId(int userId, String courseId);

    boolean existsByUserIdAndCourseId(int userId, String courseId);

    List<Enrollment> findByUserId(int userId);
}

