package com.vimarsh.Course_Platform.Repository;

import com.vimarsh.Course_Platform.DataTransferObjects.CourseSummaryDTO;
import com.vimarsh.Course_Platform.Model.Course;
import com.vimarsh.Course_Platform.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    @Query("SELECT new com.vimarsh.Course_Platform.DataTransferObjects.CourseSummaryDTO(" +
            "c.id, c.title, c.description, " +
            "COUNT(DISTINCT t.id), " +
            "COUNT(DISTINCT s.id)) " +
            "FROM Course c " +
            "LEFT JOIN c.topics t " +
            "LEFT JOIN t.subTopics s " +
            "GROUP BY c.id, c.title, c.description")
    List<CourseSummaryDTO> findAllCourseSummaries();

    @Query("SELECT DISTINCT c FROM Course c " +
            "LEFT JOIN FETCH c.topics t " +
            "LEFT JOIN FETCH t.subTopics s " +
            "WHERE c.id = :id")
    Optional<Course> findCourseWithDetails(@Param("id") String id);

    @Query(value = "SELECT DISTINCT c.* FROM course c " +
            "LEFT JOIN topic t ON c.id = t.course_id " +
            "LEFT JOIN sub_topic s ON t.id = s.topic_id " +
            "WHERE c.title ILIKE %:q% " +
            "OR c.description ILIKE %:q% " +
            "OR t.title ILIKE %:q% " +
            "OR s.title ILIKE %:q% " +
            "OR s.content ILIKE %:q%", nativeQuery = true)
    List<Course> searchCourses(@Param("q") String q);
}
