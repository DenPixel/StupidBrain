package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.User;
import com.pixel.stupidbrain.entity.request.SaveQuestionRequest;
import com.pixel.stupidbrain.entity.response.QuestionResponse;
import com.pixel.stupidbrain.entity.response.TrueAnswerResponse;
import com.pixel.stupidbrain.exception.*;
import com.pixel.stupidbrain.repository.QuestionRepository;
import com.pixel.stupidbrain.repository.TrueAnswerRepository;
import com.pixel.stupidbrain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class QuestionService implements QuestionOperations {

    private static final int DEFAULT_RATING = 0;

    private final QuestionRepository questionRepository;

    private final TrueAnswerRepository trueAnswerRepository;

    private final UserRepository userRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository,
                           TrueAnswerRepository trueAnswerRepository,
                           UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.trueAnswerRepository = trueAnswerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public QuestionResponse create(SaveQuestionRequest request) {
        if (request == null) throw new RequestIsEmptyException();

        Question question = new Question();

        String name = request.getName();
        String description = request.getDescription();
        if (name == null || name.isEmpty()) throw new FieldIsEmptyException("Name");
        if (description == null || description.isEmpty()) throw new FieldIsEmptyException("Description");

        question.setName(name);
        question.setDescription(description);

        UUID requestUser = request.getUser();
        if (requestUser == null) throw new FieldIsEmptyException("User");

        User user = userRepository.findById(
                requestUser)
                .orElseThrow(() -> new UserNotFoundException(requestUser));

        question.setUser(user);
        question.setRating(DEFAULT_RATING);

        return QuestionResponse.fromQuestion(questionRepository.save(question));
    }

    @Override
    public QuestionResponse getById(UUID id) {
        if (id == null) throw new FieldIsEmptyException("ID");

        return QuestionResponse.fromQuestion(questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id)));
    }

    @Override
    public void update(UUID id, SaveQuestionRequest request) {
        if (id == null) throw new FieldIsEmptyException("ID");
        if (request == null) throw new RequestIsEmptyException();

        String name = request.getName();
        String description = request.getDescription();
        if (name == null || name.isEmpty()) throw new FieldIsEmptyException("Name");
        if (description == null || description.isEmpty()) throw new FieldIsEmptyException("Description");

        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        existingQuestion.setName(name);
        existingQuestion.setDescription(description);

        questionRepository.save(existingQuestion);
    }

    @Override
    public void updateRating(UUID id, int rating) {
        if (id == null) throw new FieldIsEmptyException("ID");

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        question.setRating(rating);
        questionRepository.save(question);
    }

    @Override
    public void deleteById(UUID id) {
        if (id == null) throw new FieldIsEmptyException("ID");

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        questionRepository.deleteById(question.getId());
    }

    @Override
    public List<TrueAnswerResponse> getAllTrueAnswers(UUID id) {
        if (id == null) throw new FieldIsEmptyException("ID");

        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        return TrueAnswerResponse
                .fromTrueAnswers(
                        trueAnswerRepository
                                .findAllByQuestionEquals(existingQuestion));
    }

    @Override
    public List<QuestionResponse> getAll() {
        return QuestionResponse.fromQuestions(questionRepository.findAll());
    }

    @Override
    public List<QuestionResponse> getAllByRatingLessThan(int rating) {
        return QuestionResponse.fromQuestions(questionRepository.findAllByRatingLessThan(rating));
    }

    @Override
    public List<QuestionResponse> getAllByRatingGreaterThan(int rating) {
        return QuestionResponse.fromQuestions(questionRepository.findAllByRatingGreaterThan(rating));
    }
}
