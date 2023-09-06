package com.example.egypt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Lob
    @Column(nullable = false, name = "content", length = 1000)
    private String content;
    @Column(nullable = false, name = "creation_date")
    private LocalDateTime creationDate;
    @Column(nullable = false, name = "article")
    private String article;
    @Column(nullable = true, name = "rating")
    private Float rating;
    @Column(nullable = false, name = "archive", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean archive;
    @ManyToOne
    @JoinColumn(name = "author")
    private User quizAuthor;


    @OneToMany(mappedBy = "quiz")
    private List<Badge> badge;
    @ManyToMany(mappedBy = "quizzes")
    private List<Article> articles;


    @OneToMany(mappedBy = "quiz")
    private List<Comment> comments;
    @OneToMany(mappedBy = "quiz")
    private List<Rating> ratings;


    public Quiz() {
    }

    public Quiz(UUID id, String content, LocalDateTime creationDate, String article, Float rating, Boolean archive, User author, List<Badge> badge, List<Article> articles, List<Comment> comments) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.article = article;
        this.rating = rating;
        this.archive = archive;
        this.quizAuthor = author;
        this.badge = badge;
        this.articles = articles;
        this.comments = comments;
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

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public User getAuthor() {
        return quizAuthor;
    }

    public void setAuthor(User author) {
        this.quizAuthor = author;
    }

    public List<Badge> getBadge() {
        return badge;
    }

    public void setBadge(List<Badge> badge) {
        this.badge = badge;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
