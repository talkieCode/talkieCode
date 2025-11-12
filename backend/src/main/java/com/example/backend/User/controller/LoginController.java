package com.example.backend.User.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class LoginController {

    /*
        Spring Security -> Security Chain Filter -> JWT Filter -> LoginController
        OAuth2 로그인 3단계 이후의 과정으로 토큰이 있는지 확인하고 Header에 담는 Controller
        HttpServletRequest request : 이미 JWT(JSON Web Token)를 생성해 클라이언트 쿠키에 저장한 상태
    */

    @GetMapping("/callback")
    public ResponseEntity<?> callback(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpStatus status;

        try {
            String token = getCookieValue(request);
            if (token.equals("Token not found")) {
                status = HttpStatus.NOT_FOUND;
            } else {
                status = HttpStatus.OK;
                response.addHeader("Authorization", "Bearer " + token);
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(status);
    }


    public String getCookieValue(HttpServletRequest request) {

        String tokenValue = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    tokenValue = cookie.getValue();
                    break;
                }
            }
        }
        return tokenValue != null ? tokenValue : "Token not found";
    }
}
