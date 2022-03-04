package com.alkemy.HFC.disney.service.impl;

import com.alkemy.HFC.disney.service.EmailService;
import com.alkemy.HFC.disney.exception.message.ExceptionMessage;
import org.springframework.beans.factory.annotation.Value;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private Environment environment;

    @Value("${alkemy.disney.email.sender}")
    private String emailSender;

    @Override
    public void sendWelcomeEmailTo(String to) throws IOException {

        String apiKey = environment.getProperty("API_KEY_DISNEY");

        Email mailSender = new Email(emailSender);
        Email toEmail = new Email(to);

        String subject = "ALKEMY CHALLENGE BACKEND JAVA";
        Content content = new Content("text/plain", "SUCCESSFUL REGISTRATION TO HERNÁN COSTARELLI'S DISNEY API");

        Mail newMail = new Mail(mailSender, subject, toEmail, content);
        SendGrid sendGrid = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(newMail.build());
            Response response = sendGrid.api(request);

        } catch (IOException exception) {
            throw new IOException(ExceptionMessage.SEND_EMAIL_FAIL);
        }
    }
}
