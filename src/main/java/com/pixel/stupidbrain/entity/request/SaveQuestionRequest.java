package com.pixel.stupidbrain.entity.request;

import java.util.UUID;

public class SaveQuestionRequest {
    private String name;

    private String description;

    private UUID user;

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

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }
}
