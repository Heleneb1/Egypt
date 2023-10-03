package com.example.egypt.services;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service
//public class EmailServiceImpl implements EmailSenderService {
//
//    private final JavaMailSender emailSender;
//
//    @Autowired
//    public EmailServiceImpl(JavaMailSender emailSender) {
//        this.emailSender = emailSender;
//    }
//
//    @Override
//    public void sendEmail(
//            String name,
//            String fromAddress,
//            String toAddress,
//            String body
//    ) throws Exception {
//        MimeMessage mimeMessage = emailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//        mimeMessageHelper.setFrom(fromAddress);
//        mimeMessageHelper.setTo(toAddress);
//        mimeMessageHelper.setText(body, true);
//        mimeMessageHelper.setSubject("Les Mystères de l'Égypte Antique : nouveau message de " + name);
//        emailSender.send(mimeMessage);
//    }
@Component
public class EmailServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

    @Override
    public void sendEmail(String name, String fromAddress, String toAddress, String body) throws Exception {

    }
}

