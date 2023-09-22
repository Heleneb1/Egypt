package com.example.egypt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "question")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = true, name = "category")
    private String category;
    @Column(nullable = true, name = "option_1")
    private String option_1;
    @Column(nullable = true, name = "option_2")
    private String option_2;
    @Column(nullable = true, name = "option_3")
    private String option_3;
    @Column(nullable = true, name = "question_title")
    private String question_title;
    @Column(nullable = true, name = "right_answer")
    private String right_answer;
    @Column(nullable = true, name = "right_answer_2")
    private String right_answer_2;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    public Question() {
    }

    public Question(UUID id, String category, String option_1, String option_2, String option_3, String question_title, String right_answer, String right_answer_2, Quiz quiz) {
        this.id = id;
        this.category = category;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.question_title = question_title;
        this.right_answer = right_answer;
        this.right_answer_2 = right_answer_2;
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOption_1() {
        return option_1;
    }

    public void setOption_1(String option_1) {
        this.option_1 = option_1;
    }

    public String getOption_2() {
        return option_2;
    }

    public void setOption_2(String option_2) {
        this.option_2 = option_2;
    }

    public String getOption_3() {
        return option_3;
    }

    public void setOption_3(String option_3) {
        this.option_3 = option_3;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public String getRight_answer_2() {
        return right_answer_2;
    }

    public void setRight_answer_2(String right_answer_2) {
        this.right_answer_2 = right_answer_2;
    }
}
