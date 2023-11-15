package com.example.egypt.controller;

import com.example.egypt.entity.Comment;
import com.example.egypt.entity.Contact;
import com.example.egypt.entity.User;
import com.example.egypt.repository.ContactRepository;
import com.example.egypt.services.EmailSenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private ContactRepository contactRepository;
    ContactController(ContactRepository contactRepository){
        this.contactRepository=contactRepository;
    }

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("")
    public String index(ModelMap modelMap) {
        modelMap.put("contact", new Contact());
        return "Succès, Message envoyé!";
    }
    @GetMapping("/see-message")
    public List<Contact>getAll(){
        return this.contactRepository.findAll();
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void delete(@PathVariable Long id) { this.contactRepository.deleteById(id);}

@PostMapping("/send")
public ResponseEntity<String> send(@RequestBody Contact contact) {
    Dotenv dotenv = Dotenv.load();
    String adminEmail = dotenv.get("ADMIN_EMAIL");

    try {
        String content = "Nom: " + contact.getUsername() + "\n"
                + "Adresse: " + contact.getEmail() + "\n"
                + "Message: " + contact.getContent();

        // Enregistrez le contact en base de données
        contactRepository.save(contact);

        emailSenderService.sendEmail(contact.getUsername(), contact.getEmail(), contact.getContent());

        return ResponseEntity.ok("Message envoyé avec succès !");
    } catch (Exception exception) {
        return ResponseEntity.status(500).body("Erreur lors de l'envoi du message : " + exception.getMessage());
    }
}
    @PostMapping("/send-after-comment")
    public ResponseEntity<String> sendAfterComment(@RequestBody Map<String, Object> requestBody) {
        try {
            Comment comment = new ObjectMapper().convertValue(requestBody.get("comment"), Comment.class);
            User author = new ObjectMapper().convertValue(requestBody.get("author"), User.class);

            System.out.println("author:" + author);

            Dotenv dotenv = Dotenv.load();
            String adminEmail = dotenv.get("ADMIN_EMAIL");

            if (author != null) {
                //  accéder aux propriétés de l'auteur :
                String authorName = author.getFirstname() + " " + author.getLastname();
                System.out.println(authorName);
                String authorEmail = author.getEmail();

                assert comment != null;
                String content = "Nom de l'auteur: " + authorName + "\n"
                        + "Adresse e-mail de l'auteur: " + authorEmail + "\n";

                //  méthode  pour envoyer l'e-mail
                emailSenderService.sendNoRespectMessage(authorName, authorEmail, content);

                return ResponseEntity.status(200).body("Notification envoyée avec succès à l'auteur du commentaire !");
            } else {
                // cas où l'auteur est null
                return ResponseEntity.status(400).body("Aucun auteur trouvé dans la requête.");
            }
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de la notification : " + exception.getMessage());
        }
    }

}



