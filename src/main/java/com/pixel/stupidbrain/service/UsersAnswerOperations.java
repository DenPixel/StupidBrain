package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.request.SaveUsersAnswerRequest;
import com.pixel.stupidbrain.entity.response.UsersAnswerResponse;

import java.util.List;
import java.util.UUID;

public interface UsersAnswerOperations {

    UsersAnswerResponse create(SaveUsersAnswerRequest request);

    UsersAnswerResponse getById(UUID id);

    void update(UUID id, SaveUsersAnswerRequest request);

    void deleteById(UUID id);

    List<UsersAnswerResponse> getAllByQuestionId(UUID id);

}
