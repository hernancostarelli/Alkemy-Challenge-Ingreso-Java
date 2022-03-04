package com.alkemy.HFC.disney.service;

import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendWelcomeEmailTo(String to) throws IOException;

}
