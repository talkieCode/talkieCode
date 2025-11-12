package com.example.backend.User.dto;

import com.example.backend.User.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JoinRequestDTO {

    private String email;
    private String userName;
    private String nickname;
    private String description;

    @Builder
    public UserEntity toEntity() {
        return UserEntity.builder()
                .email(email)
                .userName(userName)
                .nickname(nickname)
                .description(description)
                .build();
    }
}
