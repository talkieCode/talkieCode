package com.example.backend.User.controller;

import com.example.backend.JWT.service.JWTService;
import com.example.backend.OAuth2.dto.CustomOAuth2UserRecord;
import com.example.backend.User.dto.JoinRequestDTO;
import com.example.backend.User.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@Controller
@RequestMapping("/join")
public class JoinPageController {

    private final JWTService jWTService;
    private final JoinService joinService;

    public JoinPageController(JWTService jWTService, JoinService joinService) {
        this.jWTService = jWTService;
        this.joinService = joinService;
    }

    @GetMapping("/page")
    public String mainPage(Authentication authentication, Model model) {

        CustomOAuth2UserRecord userDetail = (CustomOAuth2UserRecord) authentication.getPrincipal();

        System.out.println("join 시 : "+userDetail);
        if (userDetail == null) {
            return "redirect:/login?error=unauthorized";
        }

        try {

            String userName = userDetail.getUserName();
            String email = userDetail.getUsername();

            model.addAttribute("userName", userName);
            model.addAttribute("email", email);

            return "join";
        } catch (Exception e) {
            return "redirect:/login?error=invalid_token";
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<?> joinProcess(@RequestBody JoinRequestDTO joinRequestDTO) {

        try {
            int userSerial = joinService.joinProcess(joinRequestDTO);
            return ResponseEntity.ok()
                    .body(Map.of("success", true, "userSerial", userSerial, "redirect", "/main/page"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("success", false, "error", e.getMessage()));
        }
    }
}