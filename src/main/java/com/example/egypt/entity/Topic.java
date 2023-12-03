package com.example.egypt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = true, name = "creation_date")
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)

    private User author;

//    @ManyToOne
//    @JoinColumn(name = "receiver_id", nullable = false)
//
//    private User receiver;
@Column(nullable = true, name="tag")
private String tag;
    @Column(nullable = false,length = 1000,name = "message")

    @Size(min = 1, max = 1000)
    private String message;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Answer> answers;


    public Topic() {
    }

    public Topic(UUID id, LocalDateTime creationDate, User author, User receiver, String message, List<Answer> answers) {
        this.id = id;
        this.creationDate = creationDate;
        this.author = author;
//        this.receiver = receiver;
        this.tag= tag;
        this.message = message;
        this.answers = answers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

//    public User getReceiver() {
//        return receiver;
//    }
//
//    public void setReceiver(User receiver) {
//        this.receiver = receiver;
//    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
