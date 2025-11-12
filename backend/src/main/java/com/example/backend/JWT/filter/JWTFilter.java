package com.example.backend.JWT.filter;

import com.example.backend.JWT.service.JWTService;
import com.example.backend.OAuth2.dto.CustomOAuth2UserRecord;
import com.example.backend.User.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);
        if (token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 소멸 시간 검증
        if (jwtService.isExpired(token)) {
            System.out.println("token expried");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 email과 nickname 획득
        String email = jwtService.getEmail(token);
        int userSerial = jwtService.getUserSerial(token);
        String userName = jwtService.getUserName(token);

        // User 생성해서 값 set
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setUserSerial(userSerial);
        user.setNickname(userName);

        Map<String, Object> attributes = Map.of(
                "email", email,
                "userName", userName,
                "userSerial", userSerial
        );

        CustomOAuth2UserRecord oauth2User = new CustomOAuth2UserRecord(
                user,
                false,
                attributes,
                "email" // attributeKey
        );

        // Spring Security 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(oauth2User, null, Collections.singletonList(new SimpleGrantedAuthority("USER")));

        // session에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
