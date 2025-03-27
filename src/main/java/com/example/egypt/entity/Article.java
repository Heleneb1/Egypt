package com.example.egypt.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// TODOquand j'active cette ligne, j'ai une erreur de compilation a la
// creation d'un article : Cannot invoke "com.fasterxml.jackson.databind.deser.impl.ReadableObjectId.bindItem(Object)" because "roid" is null]
// 2023-10-10T10:02:33.318+02:00  WARN 14568 --- [nio-8080-exec-7] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot invoke "com.fasterxml.jackson.databind.deser.impl.ReadableObjectId.bindItem(Object)" because "roid" is null]
// mais ok pour la creation d'un commentaire idem avec
// cette ligne sur user
@Entity

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "id")

@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "title")
    private String title;
    @Lob
    @Column(nullable = false, name = "content", columnDefinition = "LONGTEXT")
    private String content;
    @Column(nullable = true, name = "creation_date")
    private LocalDateTime creationDate;
    @Column(nullable = true, name = "edition_date")
    private LocalDateTime editionDate;
    @Column(nullable = false, unique = true)
    private String slug;

    public LocalDateTime getEditionDate() {
        return editionDate;
    }

    public void setEditionDate(LocalDateTime editionDate) {
        this.editionDate = editionDate;
    }

    @Column(nullable = false, name = "tag")
    private String tag;
    @Column(nullable = false, name = "author")
    private String author;
    @Column(nullable = true, name = "image")
    private String image;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "article_quiz", joinColumns = @JoinColumn(name = "article_id"), inverseJoinColumns = @JoinColumn(name = "quiz_id"))
    private List<Quiz> quizzes;
    @Column(nullable = false, name = "archive", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean archive = false;
    @Column(name = "comment")
    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // Changez REMOVE en ALL pour conserver les notes
    private List<Rating> ratings = new ArrayList<>(); // Initialisez avec une ArrayList vide

    // Ajoutez un champ pour la note moyenne
    @Column(nullable = true, name = "average_rating", columnDefinition = "FLOAT DEFAULT 3.5")
    private Float averageRating = 3.5f;

    public Article() {
    }

    public Article(UUID id, String title, String content, LocalDateTime creationDate, LocalDateTime editionDate,
            Float averageRating, String tag, List<Quiz> quizzes, Boolean archive, List<Comment> comments,
            String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.editionDate = editionDate;
        this.averageRating = averageRating;
        this.tag = tag;
        this.quizzes = quizzes;
        this.archive = archive;
        this.comments = comments;

        this.author = author;
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

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void generateSlug() {
        if (this.title == null || this.title.isEmpty()) {
            this.slug = UUID.randomUUID().toString(); // Génère un slug unique si le titre est vide
        } else {
            this.slug = this.title.toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-");
        }
    }

    public float calculateAverageRating() {
        if (this.ratings == null || this.ratings.isEmpty()) {
            return 3.5f; // Valeur par défaut
        }

        return (float) this.ratings.stream()
                .mapToDouble(Rating::getRating)
                .average()
                .orElse(3.5);
    }

}
