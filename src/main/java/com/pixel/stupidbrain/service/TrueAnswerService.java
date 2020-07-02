package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.TrueAnswer;
import com.pixel.stupidbrain.entity.request.SaveTrueAnswerRequest;
import com.pixel.stupidbrain.exception.QuestionNotFoundException;
import com.pixel.stupidbrain.exception.TrueAnswerNotFoundException;
import com.pixel.stupidbrain.repository.QuestionRepository;
import com.pixel.stupidbrain.repository.TrueAnswerRepository;

import java.util.UUID;

public class TrueAnswerService implements TrueAnswerOperations{

    private final TrueAnswerRepository trueAnswerRepository;

    private final QuestionRepository questionRepository;

    public TrueAnswerService(TrueAnswerRepository trueAnswerRepository,
                             QuestionRepository questionRepository) {
        this.trueAnswerRepository = trueAnswerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public TrueAnswer create(SaveTrueAnswerRequest request) {
        TrueAnswer trueAnswer = new TrueAnswer();

        UUID questionId = request.getQuestion();

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        trueAnswer.setAnswer(request.getAnswer());
        trueAnswer.setDescription(request.getDescription());
        trueAnswer.setQuestion(question);

        return trueAnswerRepository.save(trueAnswer);
    }

    @Override
    public TrueAnswer getById(UUID id) {
        return trueAnswerRepository.findById(id)
                .orElseThrow(() -> new TrueAnswerNotFoundException(id));
    }

    @Override
    public void update(UUID id, SaveTrueAnswerRequest request) {
        TrueAnswer trueAnswer = trueAnswerRepository.findById(id)
                .orElseThrow(() -> new TrueAnswerNotFoundException(id));

        trueAnswer.setAnswer(request.getAnswer());
        trueAnswer.setDescription(request.getDescription());

        trueAnswerRepository.save(trueAnswer);
    }

    @Override
    public void deleteById(UUID id) {
        trueAnswerRepository.deleteById(id);

    }
}
