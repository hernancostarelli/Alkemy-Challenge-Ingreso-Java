package com.alkemy.HFC.disney.auth.repository;

import com.alkemy.HFC.disney.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUsername(String username);
}
