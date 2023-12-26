package com.example.egypt.services;

import com.example.egypt.entity.MessageType;
// import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
    public void sendEmail(
            String name,
            String fromAddress,
            String body) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(adminEmail); // Utilisez l'adresse de l'administrateur comme expéditeur
        message.setTo(adminEmail); // Adresse de destination (c'est aussi l'adresse de l'administrateur)
        message.setSubject("Les Mystères de l'Égypte Antique : nouveau message de " + name);
        message.setText("De : " + fromAddress + "\n" + body); // Ajoutez l'adresse de l'expéditeur dans le corps
        emailSender.send(message);

        SimpleMailMessage responseMessage = new SimpleMailMessage();
        responseMessage.setFrom("noreply@mysteresegypteantique.com"); // Remplacez par l'adresse de l'administrateur
        responseMessage.setTo(fromAddress); // Adresse de l'expéditeur
        responseMessage.setSubject("Re : Nous avons reçu votre message ");
        responseMessage
                .setText("Merci pour votre message " + name + ".\n\nNotre Scribe vous répondra dès que possible." +
                        "\n\nLes Mystères de l'Égypte Antique");

        emailSender.send(responseMessage);
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
