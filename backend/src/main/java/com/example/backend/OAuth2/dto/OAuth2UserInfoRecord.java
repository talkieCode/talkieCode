package com.example.backend.OAuth2.dto;

import com.example.backend.User.entity.UserEntity;
import jakarta.security.auth.message.AuthException;
import lombok.Builder;

import java.util.Map;

@Builder
public record OAuth2UserInfoRecord(String email, String userName, String profile) {

    public static OAuth2UserInfoRecord of(String registrationId, Map<String, Object> attributes) {

        return switch (registrationId) {
            case "google" -> ofGoogle(attributes);
            case "kakao" -> ofKakao(attributes);
            default -> {
                try {
                    throw new AuthException("잘못된 Registration Id입니다.\n"+registrationId);
                } catch (AuthException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private static OAuth2UserInfoRecord ofGoogle(Map<String, Object> attributes) {

        return OAuth2UserInfoRecord.builder()
                .email((String) attributes.get("email"))
                .userName((String) attributes.get("name"))
                .build();
    }

    private static OAuth2UserInfoRecord ofKakao(Map<String, Object> attributes) {

        Map accountMap = (Map) attributes.get("kakao_account");
        Map profileMap = (Map) accountMap.get("profile");

        return OAuth2UserInfoRecord.builder()
                .email((String) accountMap.get("email"))
                .userName((String) profileMap.get("nickname"))
                .build();
    }

    public UserEntity toEntity() {

        return UserEntity.builder()
                .email(email)
                .userName(userName)
                .build();
    }
}
