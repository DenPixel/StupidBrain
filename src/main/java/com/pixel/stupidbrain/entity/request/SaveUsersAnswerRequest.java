package com.pixel.stupidbrain.entity.request;

import java.util.UUID;

public class SaveUsersAnswerRequest {
    private String answer;

    private UUID question;

    private UUID user;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
