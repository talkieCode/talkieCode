package com.example.backend.User.repository;

import com.example.backend.User.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByEmail(String email);

    Boolean existsByUserSerial(int userSerial);

    UserEntity findByEmail(String email);

    UserEntity findByUserSerial(int userSerial);
}
