package com.vimarsh.Course_Platform.Service;

import com.vimarsh.Course_Platform.DataTransferObjects.LoginResponseDTO;
import com.vimarsh.Course_Platform.Model.User;
import com.vimarsh.Course_Platform.Repository.UserRepository;
import com.vimarsh.Course_Platform.Utility.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO loginAuth(String email, String password) {
        log.info("[AUTH-SERVICE] [METHOD: loginAuth] [STEP: FETCH_USER_START] userEmail={}", email);

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    log.error("[AUTH-SERVICE] [METHOD: loginAuth] [STEP: FETCH_USER_FAIL] userEmail={} - Reason: User not found", email);
                    throw new RuntimeException("Invalid email or password");
                });

        log.info("[AUTH-SERVICE] [METHOD: loginAuth] [STEP: PASSWORD_CHECK_START] userEmail={}", email);

        if (!user.getPassword().equals(password)) {
            log.error("[AUTH-SERVICE] [METHOD: loginAuth] [STEP: PASSWORD_CHECK_FAIL] userEmail={} - Reason: Password mismatch", email);
            throw new RuntimeException("Invalid email or password");
        }

        log.info("[AUTH-SERVICE] [METHOD: loginAuth] [STEP: JWT_GENERATION_START] userEmail={}", email);

        String token = jwtUtil.generateToken(email);

        log.info("[AUTH-SERVICE] [METHOD: loginAuth] [STEP: SUCCESS] userEmail={}", email);

        return new LoginResponseDTO(
                token,
                email,
                3600  // 1 hr in seconds
        );
    }
}

