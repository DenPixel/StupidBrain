package com.pixel.stupidbrain.entity.response;

import com.pixel.stupidbrain.entity.Question;

public class QuestionResponse {

    private String name;

    private String description;

    private int rating;

    public QuestionResponse() {
    }

    public QuestionResponse(String name, String description, int rating) {
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

    static public QuestionResponse fromQuestion(Question question){
        QuestionResponse questionResponse = new QuestionResponse();

        questionResponse.setName(question.getName());
        questionResponse.setDescription(question.getDescription());
        questionResponse.setRating(question.getRating());

        return questionResponse;
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
