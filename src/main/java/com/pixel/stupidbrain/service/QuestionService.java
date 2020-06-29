package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.TrueAnswer;
import com.pixel.stupidbrain.entity.User;
import com.pixel.stupidbrain.entity.request.SaveQuestionRequest;
import com.pixel.stupidbrain.entity.response.QuestionResponse;
import com.pixel.stupidbrain.exception.QuestionNotFoundException;
import com.pixel.stupidbrain.exception.UserNotFoundException;
import com.pixel.stupidbrain.repository.QuestionRepository;
import com.pixel.stupidbrain.repository.TrueAnswerRepository;
import com.pixel.stupidbrain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public Question create(SaveQuestionRequest request) {

        Question question = new Question();
        question.setName(request.getName());
        question.setDescription(request.getDescription());
        User user = userRepository.findById(request.getUser())
                .orElseThrow(() -> new UserNotFoundException(request.getUser()));
        question.setUser(user);
        question.setTrueAnswers(request.getTrueAnswers());
        question.setRating(DEFAULT_RATING);

        return questionRepository.save(question);
    }

    @Override
    public Question getById(UUID id) {

        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }

    @Override
    public void update(UUID id, SaveQuestionRequest request) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        existingQuestion.setName(request.getName());
        existingQuestion.setDescription(request.getDescription());
        User user = userRepository.findById(request.getUser())
                .orElseThrow(() -> new UserNotFoundException(request.getUser()));
        existingQuestion.setUser(user);
        existingQuestion.setTrueAnswers(request.getTrueAnswers());

        questionRepository.save(existingQuestion);
    }

    @Override
    public void deleteById(UUID id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<TrueAnswer> getAllTrueAnswer(UUID id) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        return trueAnswerRepository.findAllByQuestionEquals(existingQuestion);
    }

    @Override
    public List<QuestionResponse> getAllByRatingLessThan(int rating) {
        List<Question> ratingLess = questionRepository.findAllByRatingLessThan(rating);

        return getQuestionResponses(ratingLess);
    }

    @Override
    public List<QuestionResponse> getAllByRatingGreaterThan(int rating) {
        List<Question> ratingGreater = questionRepository.findAllByRatingGreaterThan(rating);

        return getQuestionResponses(ratingGreater);
    }

    private List<QuestionResponse> getQuestionResponses(List<Question> questions) {
        List<QuestionResponse> questionResponses = new ArrayList<>();

        for (Question question : questions) {
            questionResponses.add(QuestionResponse.fromQuestion(question));
        }

        return questionResponses;
    }
}
