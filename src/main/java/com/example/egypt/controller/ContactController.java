package com.example.egypt.controller;

import com.example.egypt.entity.Contact;
import com.example.egypt.services.EmailSenderService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("")
    public String index(ModelMap modelMap) {
        modelMap.put("contact", new Contact());
        return "Succès, Message envoyé!";
    }

    @PostMapping("")
    public ResponseEntity<String> send(@RequestBody Contact contact) {
        Dotenv dotenv = Dotenv.load();
        String adminEmail = dotenv.get("ADMIN_EMAIL");

        try {
            String content = "Nom: " + contact.getUsername() + "\n"
                    + "Adresse: " + contact.getEmail() + "\n"
                    + "Message: " + contact.getContent();

            emailSenderService.sendEmail(contact.getUsername(), contact.getEmail(), adminEmail, content);

            return ResponseEntity.ok("Message envoyé avec succès !");
        } catch (Exception exception) {

            return ResponseEntity.status(500).body("Erreur lors de l'envoi du message : " + exception.getMessage());
        }
    }
}
