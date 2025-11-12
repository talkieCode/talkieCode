package com.example.backend.JWT.filter;

import com.example.backend.JWT.service.JWTService;
import com.example.backend.OAuth2.dto.CustomOAuth2UserRecord;
import com.example.backend.User.entity.UserEntity;
import com.example.backend.User.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private UserRepository userRepository;

    public LoginFilter(AuthenticationManager authenticationManager, JWTService jwtSerivce, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtSerivce;
        this.userRepository = userRepository;
        this.setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String email = request.getHeader("email");
        String password = request.getHeader("password");

        // User 사용자 조회
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new AuthenticationException("User not found") {};
        }

        if (user.getIsDeleted() == 1) {
            throw new AuthenticationException("This account has been deleted") {};
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        CustomOAuth2UserRecord customUserDetails = (CustomOAuth2UserRecord) authentication.getPrincipal();

        // getUsername = getEmail
        String email = customUserDetails.getUsername();
        UserEntity user = userRepository.findByEmail(email);
        int userSerial = user.getUserSerial();
        String userName = user.getUserName();

        System.out.println("successfulAuthentication => " + user.toString());

        String token = jwtService.createJwt(email, userSerial, userName);

        System.out.println("!!!!Token => " + token);
        System.out.println("!!!!Name from token => " + jwtService.getUserName(token));
        response.addHeader("Authorization", "Bearer" + token);
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}
