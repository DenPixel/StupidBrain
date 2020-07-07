package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.request.SaveTrueAnswerRequest;
import com.pixel.stupidbrain.entity.response.TrueAnswerResponse;

import java.util.List;
import java.util.UUID;

public interface TrueAnswerOperations {

    TrueAnswerResponse create(SaveTrueAnswerRequest request);

    TrueAnswerResponse getById(UUID id);

    void update(UUID id, SaveTrueAnswerRequest request);

    void deleteById(UUID id);

    List<TrueAnswerResponse> getAllByQuestionId(UUID id);

    List<TrueAnswerResponse> getAll();
}
