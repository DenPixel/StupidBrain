package com.pixel.stupidbrain.entity.response;

import com.pixel.stupidbrain.entity.Question;

import java.util.UUID;

public class QuestionResponse {

    private UUID id;

    private String name;

    private String description;

    private int rating;

    public QuestionResponse() {
    }

    public QuestionResponse(UUID id, String name, String description, int rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

    static public QuestionResponse fromQuestion(Question question){
        QuestionResponse questionResponse = new QuestionResponse();

        questionResponse.setId(question.getId());
        questionResponse.setName(question.getName());
        questionResponse.setDescription(question.getDescription());
        questionResponse.setRating(question.getRating());

        return questionResponse;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
