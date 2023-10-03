package com.example.egypt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "biography")
    private String biography;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Email(message = "L'adresse e-mail doit être valide")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @OneToMany(mappedBy = "author")
    private List<Quiz> quizzes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_badge", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "badge_id"))
    private Set<Badge> badges = new HashSet<>();

    public User() {
    }

    public User(UUID id, String avatar, String biography, String lastname, String firstname, Role role, String email,
            String password, List<Comment> comments, List<Quiz> quizzes, Set<Badge> badges) {
        this.id = id;
        this.avatar = avatar;
        this.biography = biography;
        this.lastname = lastname;
        this.firstname = firstname;
        this.role = role;
        this.email = email;
        this.password = password;
        this.comments = comments;
        this.quizzes = quizzes;
        this.badges = badges;
    }

    public <E> User(String email, String password, Set<E> user) {
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
        return List.of(this.role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
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

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public Set<Badge> getBadges() {
        return badges;
    }

    public void setBadges(Set<Badge> badges) {
        this.badges = badges;
    }
}