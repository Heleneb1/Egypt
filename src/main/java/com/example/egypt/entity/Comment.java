package com.example.egypt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Lob
    @Column(nullable = false, name = "content", length = 1000)
    private String content;
    @Column(nullable = false, name = "creation_date")
    private LocalDateTime creationDate;

    @Column(nullable = false, name = "archive", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean archive;


    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Comment() {
    }

    public Comment(UUID id, String content, LocalDateTime creationDate, Boolean archive, User author, Quiz quiz,
                   Article article) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.archive = archive;
        this.author = author;
        this.quiz = quiz;
        this.article = article;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public User getAuthor() {

        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Article getArticle()  {
             System.out.println("Article"+article);

        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
