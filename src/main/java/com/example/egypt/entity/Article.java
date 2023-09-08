package com.example.egypt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "title")
    private String title;
    @Lob
    @Column(nullable = false, name = "content", length = 1000)
    private String content;
    @Column(nullable = true, name = "creation_date")
    private LocalDateTime creationDate;
    @Column(nullable = true, name = "edition_date")
    private LocalDateTime editionDate;

    public LocalDateTime getEditionDate() {
        return editionDate;
    }

    public void setEditionDate(LocalDateTime editionDate) {
        this.editionDate = editionDate;
    }
    @Column(nullable = true, name = "rating")
    private Float rating;
    @Column(nullable = false, name = "tag")
    private String tag;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "article_quiz",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    private List<Quiz> quizzes;
    @Column(nullable = false, name = "archive", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean archive = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();



    @OneToMany(mappedBy = "article")
    private List<Rating> ratings;


    public Article() {
    }

    public Article(UUID id, String title, String content, LocalDateTime creationDate, LocalDateTime editionDate, Float rating, String tag, User author, List<Quiz> quizzes, Boolean archive, List<Comment> comments, List<Rating> ratings) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.editionDate = editionDate;
        this.rating = rating;
        this.tag = tag;
        this.author = author;
        this.quizzes = quizzes;
        this.archive = archive;
        this.comments = comments;
        this.ratings = ratings;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
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
