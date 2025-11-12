package com.example.backend.User.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginPageController {

    @Value("${kakao.client_id}")
    private String kakao_client_id;

    @Value("${kakao.redirect_uri}")
    private String kakao_redirect_uri;

    @Value("${google.client_id}")
    private String google_client_id;

    @Value("${google.redirect_uri}")
    private String google_redirect_uri;

    @GetMapping("/page")
    public String loginPage(Model model) {
//        String kakao_location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+kakao_client_id+"&redirect_uri="+kakao_redirect_uri;
        String kakao_location = "http://localhost:8080/oauth2/authorization/kakao";
        model.addAttribute("kakao_location", kakao_location);


//        String google_location = "https://accounts.google.com/o/oauth2/v2/auth?client_id="+google_client_id+"&redirect_uri="+google_redirect_uri+"&response_type=code&scope=email profile";
        String google_location = "http://localhost:8080/oauth2/authorization/google";
        model.addAttribute("google_location", google_location);

        return "login";
    }
}
