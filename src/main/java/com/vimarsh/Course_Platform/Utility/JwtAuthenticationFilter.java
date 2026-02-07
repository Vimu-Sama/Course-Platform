package com.vimarsh.Course_Platform.Utility;

import com.vimarsh.Course_Platform.Service.ProgressService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ProgressService.class);
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String path = request.getRequestURI();

        // Check for missing or invalid header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("[JWT-FILTER] [PATH: {}] [STEP: NO_TOKEN] Authorization header missing or invalid", path);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("[JWT-FILTER] [PATH: {}] [STEP: TOKEN_FOUND] Processing JWT token", path);

        try {
            String token = authHeader.substring(7);
            String email = jwtUtil.extractEmail(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                log.info("[JWT-FILTER] [PATH: {}] [STEP: AUTHENTICATING_USER] userEmail={}", path, email);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                email, null, new ArrayList<>());

                SecurityContextHolder.getContext().setAuthentication(auth);

                log.info("[JWT-FILTER] [PATH: {}] [STEP: SUCCESS] SecurityContext updated for userEmail={}", path, email);
            }
        } catch (Exception e) {
            log.error("[JWT-FILTER] [PATH: {}] [STEP: ERROR] JWT processing failed: {}", path, e.getMessage());
            // We don't throw an exception here so the filter chain can continue,
            // and Spring Security will naturally block unauthorized requests later.
        }

        filterChain.doFilter(request, response);
    }
}

