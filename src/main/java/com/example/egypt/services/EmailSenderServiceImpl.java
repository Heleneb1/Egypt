package com.example.egypt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.egypt.entity.MessageType;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender emailSender;

    private final String adminEmail;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender emailSender, @Value("${ADMIN_EMAIL}") String adminEmail) {
        this.emailSender = emailSender;
        this.adminEmail = adminEmail;
    }

    @Override
    public void sendEmail(String name, String fromAddress, String text, MessageType messageType) throws Exception {

    }

    @Override
    public void sendEmail(String name, String fromAddress, String body) throws Exception {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(adminEmail);
            System.out.println("ADMIN_EMAIL = " + adminEmail);
            message.setTo(adminEmail);
            message.setSubject("Les Mystères de l'Égypte Antique : nouveau message de " + name);
            message.setText("De : " + fromAddress + "\n" + body);
            emailSender.send(message);

            SimpleMailMessage responseMessage = new SimpleMailMessage();
            responseMessage.setFrom(adminEmail);
            ;
            responseMessage.setTo(fromAddress);
            responseMessage.setSubject("Re : Nous avons reçu votre message");
            responseMessage.setText("Bonjour " + name + " 👋,\n\n" +
                    "Merci pour ton message ! 📨\n" +
                    "Notre scribe a bien reçu ton papyrus et te répondra dès que possible.\n\n" +
                    "En attendant, que les dieux de l'Égypte veillent sur toi ☀️🐪.\n\n" +
                    "L’équipe des Mystères de l’Égypte Antique");
            emailSender.send(responseMessage);

            System.out.println("✅ Email envoyé avec succès !");
        } catch (Exception e) {
            e.printStackTrace(); // <--- ça affichera la vraie cause dans la console
            throw e; // pour continuer à renvoyer le 500 côté front
        }
    }

    @Override
    public void sendNoRespectMessage(
            String authorName,
            String fromAddress,
            String body) throws Exception {

        SimpleMailMessage noRespectMessage = new SimpleMailMessage();
        noRespectMessage.setFrom("noreply@lesmysteresdelegypteantique.fr"); // Remplacez par l'adresse de
                                                                            // l'administrateur
        noRespectMessage.setTo(fromAddress); // Adresse de l'expéditeur
        noRespectMessage.setSubject("Re : Suite à votre commentaire ");
        noRespectMessage.setText("Bonjour " + authorName
                + ".\n\nVotre message ne respecte pas notre charte de bonne conduite , il ne sera pas mis en ligne." +
                "\n\nLes Mystères de l'Égypte Antique");

        emailSender.send(noRespectMessage);

    }

}
