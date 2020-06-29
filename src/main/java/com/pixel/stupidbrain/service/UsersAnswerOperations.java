package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.UsersAnswer;
import com.pixel.stupidbrain.entity.request.SaveUsersAnswerRequest;

import java.util.UUID;

public interface UsersAnswerOperations {

    UsersAnswer create(SaveUsersAnswerRequest request);

    UsersAnswer get(UUID id);

    void update(UUID id, SaveUsersAnswerRequest request);

    void deleteById(UUID id);
}
