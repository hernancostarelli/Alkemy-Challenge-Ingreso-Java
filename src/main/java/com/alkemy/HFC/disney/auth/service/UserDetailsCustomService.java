package com.alkemy.HFC.disney.auth.service;

import com.alkemy.HFC.disney.service.EmailService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            throw new RegisterException(ExceptionMessage.USERNAME_OR_PASSWORD_NOT_FOUND);
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
    }

    public Boolean ifUsernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public boolean register(UserDTO userDTO) throws IOException {

        if (ifUsernameExist(userDTO.getUsername())) {
            throw new RegisterException(ExceptionMessage.USER_EXISTS);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());

        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        userEntity.setPassword(encryptedPassword);

        userEntity = userRepository.save(userEntity);

        if (userRepository.findByUsername(userEntity.getUsername()) != null) {

            emailService.sendWelcomeEmailTo(userEntity.getUsername());
        }
        return userEntity != null;
    }
}
