package com.pixel.stupidbrain.entity.request;

import java.util.UUID;

public class SaveTrueAnswerRequest {
    private String answer;

    private String description;

    private UUID question;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getQuestion() {
        return question;
    }

    public void setQuestion(UUID question) {
        this.question = question;
    }
}
