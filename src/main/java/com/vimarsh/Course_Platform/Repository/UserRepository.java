package com.vimarsh.Course_Platform.Repository;

import com.vimarsh.Course_Platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String emailId) ;
}