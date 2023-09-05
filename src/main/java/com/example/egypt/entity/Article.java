package com.example.egypt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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
    @Column(nullable = false, name = "tag")
    private String tag;
    @Column(nullable = true, name = "author")
    private String author;
    @ManyToMany
    @JoinTable(
            name = "article_quiz",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    private List<Quiz> quizzes;
    @Column(nullable = false, name = "archive", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean archive;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;
    @OneToMany(mappedBy = "article")
    private List<Rating> ratings;


    public Article() {
    }

    public Article(String title, String content, LocalDateTime creationDate, String tag, String author, Boolean archive) {

        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.tag = tag;
        this.author = author;
        this.archive = archive;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", tag='" + tag + '\'' +
                ", author='" + author + '\'' +
                ", quizzes=" + quizzes +
                ", archive=" + archive +
                ", comments=" + comments +
                ", ratings=" + ratings +
                '}';
    }
}
