package com.example.backend.OAuth2.dto;

import com.example.backend.User.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public record CustomOAuth2UserRecord(UserEntity user, boolean isNewUser, Map<String, Object> attributes, String attributeKey) implements OAuth2User, UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return "USER";
            }
        });
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public boolean isNewUser() {
        return isNewUser;
    }

    @Override
    public String getName() {
        return attributes.get(attributeKey).toString();
    }

    @Override
    public String getPassword() {
        return "";
    }

    public int getUserSerial() {
        return user.getUserSerial();
    }

    public String getUserName() {
        return user.getUserName();
    }

    public String getNickname() {
        return user.getNickname();
    }


}
