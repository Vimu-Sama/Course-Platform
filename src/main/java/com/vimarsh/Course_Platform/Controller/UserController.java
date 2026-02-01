package com.vimarsh.Course_Platform.Controller;

import com.vimarsh.Course_Platform.DataTransferObjects.UserRequestDTO;
import com.vimarsh.Course_Platform.DataTransferObjects.UserResponseDTO;
import com.vimarsh.Course_Platform.Exception.UserBadRequestDTOError;
import com.vimarsh.Course_Platform.Service.UserService;
import com.vimarsh.Course_Platform.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class UserController {

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
}
