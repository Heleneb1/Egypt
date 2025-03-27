package com.example.egypt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "badge")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = true, name = "description")
    private String description;
    @Column(nullable = false, name = "image")
    private String image;
    @OneToOne(mappedBy = "badge") // Mappé par le champ "badge" de la classe Quiz
    private Quiz quiz; // Le quiz associé à ce badge

    public Badge() {
    }

    public Badge(UUID id, String name, String description, String image, Quiz quiz, Set<User> user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        // this.quiz = quiz;
        // this.user = user;
    }

    // public Set<User> getUser() {
    // return user;
    // }
    //
    // public void setUser(Set<User> user) {
    // this.user = user;
    // }

    // public Quiz getQuiz() {
    // return quiz;
    // }
    //
    // public void setQuiz(Quiz quiz) {
    // this.quiz = quiz;
    // }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}