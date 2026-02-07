package com.vimarsh.Course_Platform.Repository;

import com.vimarsh.Course_Platform.Model.SubTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTopicRepository extends JpaRepository<SubTopic, String> {
}
