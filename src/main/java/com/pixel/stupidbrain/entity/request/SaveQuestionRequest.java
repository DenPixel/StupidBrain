package com.pixel.stupidbrain.entity.request;

import com.pixel.stupidbrain.entity.TrueAnswer;
import com.pixel.stupidbrain.entity.User;

import java.util.Set;
import java.util.UUID;

public class SaveQuestionRequest {

    private String name;

    private String description;

    private int rating;

    private UUID user;

    private Set<TrueAnswer> trueAnswers;

    public SaveQuestionRequest() {
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

    public Set<TrueAnswer> getTrueAnswers() {
        return trueAnswers;
    }

    public void setTrueAnswers(Set<TrueAnswer> trueAnswers) {
        this.trueAnswers = trueAnswers;
    }
}
