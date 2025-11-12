package com.example.backend.User.controller;

import com.example.backend.JWT.service.JWTService;
import com.example.backend.OAuth2.dto.CustomOAuth2UserRecord;
import com.example.backend.User.entity.UserEntity;
import com.example.backend.User.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainPageController {

    private final JWTService jWTService;
    private final UserRepository userRepository;

    public MainPageController(JWTService jWTService, UserRepository userRepository) {
        this.jWTService = jWTService;
        this.userRepository = userRepository;
    }

    @GetMapping("/page")
    public String mainPage(Authentication authentication, Model model) {

        CustomOAuth2UserRecord userDetail = (CustomOAuth2UserRecord) authentication.getPrincipal();

        if (userDetail == null) {
            return "redirect:/login?error=unauthorized";
        }

        try {

            String email = userDetail.getUsername();
            UserEntity user = userRepository.findByEmail(email);
            model.addAttribute("user", user);

            return "main";
        } catch (Exception e) {
            return "redirect:/login?error=invalid_token";
        }
    }
}