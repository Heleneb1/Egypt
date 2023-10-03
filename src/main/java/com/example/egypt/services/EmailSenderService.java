package com.example.egypt.services;

import org.springframework.stereotype.Service;

@Service

public interface EmailSenderService {

    // Method to send an email.
    void sendEmail(
            String name,         // Sender's name
            String fromAddress,  // Sender's email address
            String toAddress,    // Recipient's email address
            String body          // Email body or content
    ) throws Exception;        // Exception can be thrown if there's an issue with sending the email.
}
