package com.example.egypt.services;

import com.example.egypt.entity.MessageType;
import org.springframework.stereotype.Service;

@Service


public interface EmailSenderService {

    void sendEmail(String name, String fromAddress, String text, MessageType messageType) throws Exception;

    void sendEmail(
            String name,
            String fromAddress,
            String text
    ) throws Exception;


    void sendNoRespectMessage(
            String name,
            String fromAddress,
            String body) throws Exception;
}
