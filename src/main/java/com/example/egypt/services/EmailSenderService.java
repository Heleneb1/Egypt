package com.example.egypt.services;

import org.springframework.stereotype.Service;

@Service


public interface EmailSenderService {

        void sendEmail(
            String name,
            String fromAddress,
            String text

    ) throws Exception;



}
