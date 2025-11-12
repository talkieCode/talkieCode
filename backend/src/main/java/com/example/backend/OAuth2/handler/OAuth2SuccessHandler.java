package com.example.backend.OAuth2.handler;

import com.example.backend.JWT.service.JWTService;
import com.example.backend.OAuth2.dto.CustomOAuth2UserRecord;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${OAuth2SuccessHandler.sendRedirect.join}")
    private String joinRedirect;

    @Value("${OAuth2SuccessHandler.sendRedirect.main}")
    private String mainRedirect;

    private final JWTService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        /*
            customOAuth2UserService에서
            new CustomOAuth2User(user, isNewUser, oAuth2UserAttributes, principalName)
            을 반환
         */

        // 1. OAuth2User 가져오기
        CustomOAuth2UserRecord customOAuth2User = (CustomOAuth2UserRecord) authentication.getPrincipal();

        boolean isNewUser = customOAuth2User.isNewUser();
        int userSerial = customOAuth2User.getUserSerial();
        String email = customOAuth2User.getUsername();
        String userName = customOAuth2User.getUserName();
        System.out.println("접속 유저 : "+customOAuth2User);
        System.out.println("email : "+email);
        System.out.println("userName : "+userName);

        String token = jwtService.createJwt(email, userSerial, userName);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        if (isNewUser) {

            // 2-1. 만약 아직 회원가입을 하지 않은 유저라면
            response.sendRedirect(joinRedirect);
        } else {

            // 2-2. 이미 회원가입 한 유저라면
            response.sendRedirect(mainRedirect);
        }
    }
}
