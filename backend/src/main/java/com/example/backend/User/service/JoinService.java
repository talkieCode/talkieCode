package com.example.backend.User.service;

import com.example.backend.User.dto.JoinRequestDTO;
import com.example.backend.User.entity.UserEntity;
import com.example.backend.User.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;

    public JoinService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int joinProcess(JoinRequestDTO joinRequestDTO) {

        if (!userRepository.existsByEmail(joinRequestDTO.getEmail())) {
            throw new UsernameNotFoundException("해당 유저가 존재하지 않습니다.");
        }

        UserEntity user = userRepository.findByEmail(joinRequestDTO.getEmail());
        user.setNickname(joinRequestDTO.getNickname());
        user.setDescription(joinRequestDTO.getDescription());
        return userRepository.save(user).getUserSerial();
    }
}
