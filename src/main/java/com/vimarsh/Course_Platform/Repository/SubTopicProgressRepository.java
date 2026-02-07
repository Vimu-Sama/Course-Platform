package com.vimarsh.Course_Platform.Repository;

import com.vimarsh.Course_Platform.Model.Course;
import com.vimarsh.Course_Platform.Model.SubTopic;
import com.vimarsh.Course_Platform.Model.SubTopicProgress;
import com.vimarsh.Course_Platform.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubTopicProgressRepository extends JpaRepository<SubTopicProgress, Integer> {

    Optional<SubTopicProgress> findByUserAndSubtopic(User user, SubTopic subtopic);

    long countByUserAndSubtopic_Topic_CourseAndCompletedTrue(
            User user, Course course
    );

    List<SubTopicProgress> findByUserAndSubtopic_Topic_CourseAndCompletedTrue(
            User user, Course course
    );
}
