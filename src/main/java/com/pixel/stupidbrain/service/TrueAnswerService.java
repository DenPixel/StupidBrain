package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.TrueAnswer;
import com.pixel.stupidbrain.entity.request.SaveTrueAnswerRequest;
import com.pixel.stupidbrain.entity.response.TrueAnswerResponse;
import com.pixel.stupidbrain.exception.*;
import com.pixel.stupidbrain.repository.QuestionRepository;
import com.pixel.stupidbrain.repository.TrueAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TrueAnswerService implements TrueAnswerOperations{

    private final TrueAnswerRepository trueAnswerRepository;

    private final QuestionRepository questionRepository;

    public TrueAnswerService(TrueAnswerRepository trueAnswerRepository,
                             QuestionRepository questionRepository) {
        this.trueAnswerRepository = trueAnswerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public TrueAnswerResponse create(SaveTrueAnswerRequest request) {
        if (request == null) throw new RequestIsEmptyException();

        UUID questionId = request.getQuestion();
        String answer = request.getAnswer();

        if (questionId == null) throw new FieldIsEmptyException("Question");
        if (answer == null || answer.isEmpty()) throw new FieldIsEmptyException("Answer");

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        boolean exists = trueAnswerRepository
                .existsByQuestionEqualsAndAnswerEquals(question, answer);

        if (exists) throw new AnswersAlreadyExistsException(answer);

        TrueAnswer trueAnswer = new TrueAnswer();
        trueAnswer.setAnswer(answer);
        trueAnswer.setDescription(request.getDescription());
        trueAnswer.setQuestion(question);

        return TrueAnswerResponse.fromTrueAnswer(trueAnswerRepository.save(trueAnswer));
    }

    @Override
    public TrueAnswerResponse getById(UUID id) {
        if (id == null) throw new FieldIsEmptyException("ID");

        return TrueAnswerResponse.fromTrueAnswer(trueAnswerRepository.findById(id)
                .orElseThrow(() -> new TrueAnswerNotFoundException(id)));
    }

    @Override
    public void update(UUID id, SaveTrueAnswerRequest request) {
        if (id == null) throw new FieldIsEmptyException("ID");
        if (request == null) throw new RequestIsEmptyException();

        TrueAnswer trueAnswer = trueAnswerRepository.findById(id)
                .orElseThrow(() -> new TrueAnswerNotFoundException(id));

        String answer = request.getAnswer();
        if (answer == null || answer.isEmpty()) throw new FieldIsEmptyException("Answer");

        trueAnswer.setAnswer(answer);
        trueAnswer.setDescription(request.getDescription());

        trueAnswerRepository.save(trueAnswer);
    }

    @Override
    public void deleteById(UUID id) {
        if (id == null) throw new FieldIsEmptyException("ID");

        TrueAnswer trueAnswer = trueAnswerRepository.findById(id)
                .orElseThrow(() -> new TrueAnswerNotFoundException(id));

        trueAnswerRepository.deleteById(trueAnswer.getId());

    }

    @Override
    public List<TrueAnswerResponse> getAllByQuestionId(UUID id){
        if (id == null) throw new FieldIsEmptyException("ID");

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        return TrueAnswerResponse.fromTrueAnswers(trueAnswerRepository.findAllByQuestionEquals(question));
    }

    @Override
    public List<TrueAnswerResponse> getAll() {
        return TrueAnswerResponse.fromTrueAnswers(trueAnswerRepository.findAll());
    }
}
