package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.request.SaveQuestionRequest;
import com.pixel.stupidbrain.entity.response.QuestionResponse;
import com.pixel.stupidbrain.entity.response.TrueAnswerResponse;

import java.util.List;
import java.util.UUID;

public interface QuestionOperations {

    QuestionResponse create(SaveQuestionRequest request);

    QuestionResponse getById(UUID id);

    void update(UUID id, SaveQuestionRequest request);

    void updateRating(UUID id, int rating);

    void deleteById(UUID id);

    List<TrueAnswerResponse> getAllTrueAnswers(UUID id);

    List<QuestionResponse> getAll();

    List<QuestionResponse> getAllByRatingLessThan(int rating);

    List<QuestionResponse> getAllByRatingGreaterThan(int rating);


}
