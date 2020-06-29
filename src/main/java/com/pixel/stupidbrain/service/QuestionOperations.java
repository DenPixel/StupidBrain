package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.TrueAnswer;
import com.pixel.stupidbrain.entity.request.SaveQuestionRequest;
import com.pixel.stupidbrain.entity.response.QuestionResponse;

import java.util.List;
import java.util.UUID;

public interface QuestionOperations {

    Question create(SaveQuestionRequest request);

    Question getById(UUID id);

    void update(UUID id, SaveQuestionRequest request);

    void deleteById(UUID id);

    List<TrueAnswer> getAllTrueAnswer(UUID id);

    List<QuestionResponse> getAllByRatingLessThan(int rating);

    List<QuestionResponse> getAllByRatingGreaterThan(int rating);


}
