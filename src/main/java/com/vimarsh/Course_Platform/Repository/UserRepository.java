package com.vimarsh.Course_Platform.Repository;

import com.vimarsh.Course_Platform.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String emailId) ;
    Optional<User> findByEmail(String emailId) ;
}