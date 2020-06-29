package com.pixel.stupidbrain.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users_answers")
public class UsersAnswer {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String answer;

    @Column(name = "correct_answer",nullable = false)
    private boolean correctAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id",nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
