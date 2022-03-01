package com.alkemy.HFC.disney.auth.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendWelcomeEmailTo(String to);

}
