package com.example.egypt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = true, name = "avatar")
    private String avatar;

    @Column(nullable = true, name = "biography")
    private String biography;
    @Column(nullable = false, name = "lastname")
    private String lastname;

    @Column(nullable = true, name = "firstname")
    private String firstname;


    @Column(nullable = false, name = "role")
    @Enumerated(EnumType.STRING)

   private Role role;

        @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true, name = "email")
    private String email;

   @Size(min = 8, message = "Password should have at least 8 characters")
    @Column(nullable = false, name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @OneToMany(mappedBy = "author")
    private List<Quiz> quiz;




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
//        this.badge = badge;
    }

//    @OneToMany(mappedBy = "user")
//    private List<Badge> badge;
public <E> User(String mail, String password, Set<E> user) {
}

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List .of(new SimpleGrantedAuthority(role.name())) ;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

//    public List<Badge> getBadge() {
//        return badge;
//    }
//
//    public void setBadge(List<Badge> badge) {
//        this.badge = badge;
//    }


}
