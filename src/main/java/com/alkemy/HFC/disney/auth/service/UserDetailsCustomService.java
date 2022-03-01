package com.alkemy.HFC.disney.auth.service;

import com.alkemy.HFC.disney.auth.dto.UserDTO;
import com.alkemy.HFC.disney.auth.entity.UserEntity;
import com.alkemy.HFC.disney.auth.repository.UserRepository;
import com.alkemy.HFC.disney.exception.RegisterException;
import com.alkemy.HFC.disney.exception.message.ExceptionMessage;
import java.io.IOException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("USERNAME OR PASSWORD NOT FOUND");
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
    }

    public void register(UserDTO userDTO) throws IOException {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());

        if (userRepository.findByUsername(userEntity.getUsername()) == null) {

            userEntity = userRepository.save(userEntity);
            emailService.sendWelcomeEmailTo(userEntity.getUsername());

        } else {
            throw new RegisterException(ExceptionMessage.USER_EXISTS);
        }
    }
}
