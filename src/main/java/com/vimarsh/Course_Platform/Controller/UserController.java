package com.vimarsh.Course_Platform.Controller;

import com.vimarsh.Course_Platform.DataTransferObjects.UserRequestDTO;
import com.vimarsh.Course_Platform.DataTransferObjects.UserResponseDTO;
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
    private UserService userService ;

    public UserController(UserService userService){
        this.userService = userService ;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> RegisterNewUser(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("[REGISTER-USER] [POST /api/auth/register] [METHOD: UserController/RegisterNewUser] [START] userEmail= {}",
                userRequestDTO.getEmail());
        try{
            log.info("[REGISTER-USER] [POST /api/auth/register] [METHOD: UserController/RegisterNewUser] " +
                            "[DTO CONVERSION TO MODEL START] userEmail= {}",
                    userRequestDTO.getEmail());

            User user = new User( userRequestDTO.getEmail() ,
                    userRequestDTO.getPassword()) ;

            log.info("[REGISTER-USER] [POST /api/auth/register] [METHOD: UserController/RegisterNewUser] " +
                            "[DTO CONVERSION TO MODEL SUCCESSFUL] userEmail= {}",
                    userRequestDTO.getEmail()) ;

            Pair<Integer, UserResponseDTO> serviceUserResponse = userService.CreateUser(user) ;
            int status = serviceUserResponse.getFirst() ;
            UserResponseDTO userResponseDTO= serviceUserResponse.getSecond() ;

            log.info("[REGISTER-USER] [POST /api/auth/register] [METHOD: UserController/RegisterNewUser] " +
                            "[RESPONSE RETURNED FROM SERVICE WITH STATUS CODE = {}] userEmail= {}", status,
                    userRequestDTO.getEmail()) ;

            return ResponseEntity.status(status).body(userResponseDTO) ;

        } catch (Exception e){
            log.error("[REGISTER-USER] [POST /api/auth/register] [METHOD: UserController/RegisterNewUser] " +
                    "[ERROR - DTO CONVERSION] - ",e);
            log.info("[REGISTER-USER] [POST /api/auth/register] [METHOD: UserController/RegisterNewUser] " +
                            "[RETURNING BAD REQUEST RESPONSE] userEmail= {}",
                    userRequestDTO.getEmail()) ;
            return ResponseEntity.badRequest().build() ;
        }
    }
}
