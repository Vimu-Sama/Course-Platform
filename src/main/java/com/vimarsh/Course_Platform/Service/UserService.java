package com.vimarsh.Course_Platform.Service;

import com.vimarsh.Course_Platform.DataTransferObjects.UserResponseDTO;
import com.vimarsh.Course_Platform.Repository.UserRepository;
import com.vimarsh.Course_Platform.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository ;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository ;
    }

    public Pair<Integer, UserResponseDTO> CreateUser(User userData){
        UserResponseDTO userResponseDTO = new UserResponseDTO() ;
        int statusCode= 200 ;
        log.info("[REGISTER-USER] [POST /api/auth/register] [CHECKING USER IF EXISTS] " +
                        "[METHOD: Service/UserService/CreateUser] userEmail= {}",
                userData.getEmail());
        if(userRepository.existsByEmail(userData.getEmail())){
            log.info("[REGISTER-USER] [POST /api/auth/register] [METHOD: /Service/CreateUser] " +
                    "[USER ALREADY EXISTS, EXITING METHOD] [METHOD: Service/UserService/CreateUser] " +
                    "userEmail= {}", userData.getEmail()) ;
            userResponseDTO.setMessage("User already exists!! Cannot create another user!") ;
            statusCode= 409 ;
        } else {
            User savedUser = new User() ;
            try{
                log.info("[REGISTER-USER] [POST /api/auth/register] [METHOD: /Service/CreateUser] " +
                                "[SAVING USER TO DATABASE] [METHOD: Service/UserService/CreateUser] " +
                                "userEmail= {}",
                        userData.getEmail());
                savedUser = userRepository.save(userData) ;
                log.info("[REGISTER-USER] [POST /api/auth/register] [USER SAVED TO DATABASE] " +
                                "[METHOD: Service/UserService/CreateUser] userEmail= {}",
                        userData.getEmail());
                userResponseDTO.setId(savedUser.getId());
                userResponseDTO.setEmail(savedUser.getEmail());
                userResponseDTO.setMessage("User created successfully!");
                statusCode= 201 ;
            } catch(Exception e){
                log.error("[REGISTER-USER] [POST /api/auth/register] [ERROR- WHILE SAVING USER] " +
                                "[METHOD: Service/UserService/CreateUser] userEmail= {}",
                        userData.getEmail(), e);
                userResponseDTO.setMessage("Error while saving the User to database occurred, " +
                        "no user created in database");
                statusCode= 500 ;
            }
        }
        return Pair.of(statusCode,userResponseDTO) ;
    }
}
