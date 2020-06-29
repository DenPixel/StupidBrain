package com.pixel.stupidbrain.entity.request;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.User;

import java.util.UUID;

public class SaveUsersAnswerRequest {

    private String answer;

    private boolean correctAnswer;

    private UUID question;

    private UUID user;

    public SaveUsersAnswerRequest() {
    }

    public SaveUsersAnswerRequest(String answer, boolean correctAnswer, UUID question, UUID user) {
        this.answer = answer;
        this.correctAnswer = correctAnswer;
        this.question = question;
        this.user = user;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public UUID getQuestion() {
        return question;
    }

    public void setQuestion(UUID question) {
        this.question = question;
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }
}
