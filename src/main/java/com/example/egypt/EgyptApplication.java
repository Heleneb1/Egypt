package com.example.egypt;

import com.example.egypt.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class EgyptApplication {
	private final EmailSenderService senderService;

	@Autowired
	public EgyptApplication(EmailSenderService senderService) {
		this.senderService = senderService;
	}

	public static void main(String[] args) {
		SpringApplication.run(EgyptApplication.class, args);
	}
}
