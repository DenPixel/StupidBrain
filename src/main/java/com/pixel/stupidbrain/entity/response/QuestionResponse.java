package com.pixel.stupidbrain.entity.response;

import com.pixel.stupidbrain.entity.Question;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class QuestionResponse {
    private UUID id;

    private String name;

    private String description;

    private int rating;

    private UUID user;

    static public QuestionResponse fromQuestion(Question question){
        QuestionResponse questionResponse = new QuestionResponse();

        questionResponse.setId(question.getId());
        questionResponse.setName(question.getName());
        questionResponse.setDescription(question.getDescription());
        questionResponse.setRating(question.getRating());
        questionResponse.setUser(question.getUser().getId());

        return questionResponse;
    }

    static public List<QuestionResponse> fromQuestions(Collection<Question> questions){
        return questions.stream()
                .map(QuestionResponse::fromQuestion)
                .collect(Collectors.toList());
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

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }
}
