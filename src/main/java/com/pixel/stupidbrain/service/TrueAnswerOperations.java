package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.TrueAnswer;
import com.pixel.stupidbrain.entity.request.SaveTrueAnswerRequest;

import java.util.UUID;

public interface TrueAnswerOperations {

    TrueAnswer create(SaveTrueAnswerRequest request);

    TrueAnswer getById(UUID id);

    void update(UUID id, SaveTrueAnswerRequest request);

    void deleteById(UUID id);
}
