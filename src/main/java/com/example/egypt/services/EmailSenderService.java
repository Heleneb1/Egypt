package com.example.egypt.services;

import org.springframework.stereotype.Service;

@Service

public interface EmailSenderService {

    // Method to send an email.
    void sendEmail(
            String name,
            String fromAddress,
            String toAddress,
            String body
    ) throws Exception;
}
