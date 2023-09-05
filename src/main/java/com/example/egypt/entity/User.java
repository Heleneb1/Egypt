package com.example.egypt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = true, name = "avatar")
    private String avatar;

    @Column(nullable = true, name = "biography")
    private String biography;
    @Column(nullable = false, name = "lastname")
    private String lastname;

    @Column(nullable = false, name = "firstname")
    private String firstname;


    @Column(nullable = false, name = "role")
    @Enumerated(EnumType.STRING)

   private Role role;

    //    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true, name = "email")
    private String email;

    //    @Size(min = 8, message = "Password should have at least 8 characters")
    @Column(nullable = false, name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @OneToMany(mappedBy = "author")
    private List<Quiz> quiz;


    @OneToMany(mappedBy = "user")
    private List<Badge> badge;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<Quiz> quiz) {
        this.quiz = quiz;
    }

    public List<Badge> getBadge() {
        return badge;
    }

    public void setBadge(List<Badge> badge) {
        this.badge = badge;
    }


    public User() {
    }

    public User(UUID id, String avatar, String biography, String lastname, String firstname, Role role, String email, String password, List<Comment> comments, List<Quiz> quiz, List<Badge> badge) {
        this.id = id;
        this.avatar = avatar;
        this.biography = biography;
        this.lastname = lastname;
        this.firstname = firstname;
        this.role = role;
        this.email = email;
        this.password = password;
        this.comments = comments;
        this.quiz = quiz;
        this.badge = badge;
    }
}
