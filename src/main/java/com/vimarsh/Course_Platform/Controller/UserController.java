package com.vimarsh.Course_Platform.Controller;

import com.vimarsh.Course_Platform.DataTransferObjects.LoginRequestDTO;
import com.vimarsh.Course_Platform.DataTransferObjects.LoginResponseDTO;
import com.vimarsh.Course_Platform.DataTransferObjects.UserRequestDTO;
import com.vimarsh.Course_Platform.DataTransferObjects.UserResponseDTO;
import com.vimarsh.Course_Platform.Exception.UserBadRequestDTOError;
import com.vimarsh.Course_Platform.Model.Enrollment;
import com.vimarsh.Course_Platform.Service.AuthService;
import com.vimarsh.Course_Platform.Service.UserService;
import com.vimarsh.Course_Platform.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    AuthService authService ;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService ;

    public UserController(UserService userService){
        this.userService = userService ;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> RegisterNewUser(@RequestBody UserRequestDTO userRequestDTO) {

        log.info("[REGISTER-USER] [POST /api/auth/register] " +
                        "[METHOD: UserController.RegisterNewUser] [STEP: START] userEmail={}",
                userRequestDTO.getEmail());

        if (userRequestDTO.getEmail() == null || userRequestDTO.getPassword() == null) {

            log.error("[REGISTER-USER] [POST /api/auth/register] " +
                            "[METHOD: UserController.RegisterNewUser] [STEP: INVALID_DTO] userEmail={}",
                    userRequestDTO.getEmail());

            throw new UserBadRequestDTOError(
                    "User creation request not properly structured as required, user request -> " + userRequestDTO
            );
        }

        log.info("[REGISTER-USER] [POST /api/auth/register] " +
                        "[METHOD: UserController.RegisterNewUser] [STEP: DTO_TO_MODEL_START] userEmail={}",
                userRequestDTO.getEmail());

        User user = new User(
                userRequestDTO.getEmail(),
                userRequestDTO.getPassword()
        );

        log.info("[REGISTER-USER] [POST /api/auth/register] " +
                        "[METHOD: UserController.RegisterNewUser] [STEP: DTO_TO_MODEL_SUCCESS] userEmail={}",
                userRequestDTO.getEmail());

        UserResponseDTO response = userService.createUser(user);

        log.info("[REGISTER-USER] [POST /api/auth/register] " +
                        "[METHOD: UserController.RegisterNewUser] [STEP: USER_CREATED_SUCCESS] userEmail={}",
                userRequestDTO.getEmail());

        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {

        log.info("[LOGIN-USER] [POST /api/auth/login] " +
                        "[METHOD: UserController.login] [STEP: START] userEmail={}",
                request.getEmail());

        if (request.getEmail() == null || request.getPassword() == null) {

            log.error("[LOGIN-USER] [POST /api/auth/login] " +
                            "[METHOD: UserController.login] [STEP: INVALID_DTO] userEmail={}",
                    request.getEmail());

            throw new UserBadRequestDTOError(
                    "Login request not properly structured, request -> " + request
            );
        }

        log.info("[LOGIN-USER] [POST /api/auth/login] " +
                        "[METHOD: UserController.login] [STEP: AUTH_START] userEmail={}",
                request.getEmail());

        LoginResponseDTO response = authService.loginAuth(
                request.getEmail(),
                request.getPassword()
        );

        log.info("[LOGIN-USER] [POST /api/auth/login] " +
                        "[METHOD: UserController.login] [STEP: AUTH_SUCCESS] userEmail={}",
                request.getEmail());

        return ResponseEntity.ok(response);
    }
}
