package com.vimarsh.Course_Platform.Service;

import com.vimarsh.Course_Platform.DataTransferObjects.UserResponseDTO;
import com.vimarsh.Course_Platform.Exception.UserAlreadyExistsException;
import com.vimarsh.Course_Platform.Repository.UserRepository;
import com.vimarsh.Course_Platform.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(User userData) {

        log.info("[REGISTER-USER] [POST /api/auth/register] " +
                        "[METHOD: UserService.createUser] [STEP: CHECK_USER_EXISTS] userEmail={}",
                userData.getEmail());

        if (userRepository.existsByEmail(userData.getEmail())) {

            log.info("[REGISTER-USER] [POST /api/auth/register] " +
                            "[METHOD: UserService.createUser] [STEP: USER_ALREADY_EXISTS] userEmail={}",
                    userData.getEmail());

            throw new UserAlreadyExistsException(
                    "User with email " + userData.getEmail() + " already exists"
            );
        }

        log.info("[REGISTER-USER] [POST /api/auth/register] " +
                        "[METHOD: UserService.createUser] [STEP: SAVE_USER_START] userEmail={}",
                userData.getEmail());

        User savedUser = userRepository.save(userData);

        log.info("[REGISTER-USER] [POST /api/auth/register] " +
                        "[METHOD: UserService.createUser] [STEP: SAVE_USER_SUCCESS] userEmail={}",
                userData.getEmail());

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getEmail(),
                "User created successfully!"
        );
    }
}
