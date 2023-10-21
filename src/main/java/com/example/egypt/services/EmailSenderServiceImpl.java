package com.example.egypt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import io.github.cdimascio.dotenv.Dotenv;

//@Service
//public class EmailServiceImpl implements EmailSenderService {
//
//    private final JavaMailSender emailSender;
//    private final String adminEmail;
//
//    @Autowired
//    public EmailServiceImpl(JavaMailSender emailSender) {
//        this.emailSender = emailSender;
//        Dotenv dotenv = Dotenv.load(); // Charge le fichier .env
//        adminEmail = dotenv.get("ADMIN_EMAIL"); // Initialise adminEmail avec la valeur de ADMIN_EMAIL du fichier .env
//    }
//
//    @Override
//    public void sendEmail(
//            String name,
//            String fromAddress,
//            String toAddress,
//            String body) throws Exception {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(fromAddress); // Remplacez "fromAddress" par l'adresse e-mail de l'expéditeur
//        message.setTo(adminEmail);
//        message.setSubject("Les Mystères de l'Égypte Antique : nouveau message de " + name);
//        message.setText(body);
//        emailSender.send(message);
//    }
//}
//@Service
//public class EmailSenderServiceImpl implements EmailSenderService {
//    private final JavaMailSender emailSender;
//
//    @Autowired
//    public EmailSenderServiceImpl(JavaMailSender emailSender) {
//        this.emailSender = emailSender;
//    }
//
//    @Override
//    public void sendEmail(String name, String fromAddress, String toAddress, String body) throws Exception {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(fromAddress);
//        message.setTo(toAddress);
//        message.setSubject("Nouveau message de " + name);
//        message.setText(body);
//        emailSender.send(message);
//    }
//}
