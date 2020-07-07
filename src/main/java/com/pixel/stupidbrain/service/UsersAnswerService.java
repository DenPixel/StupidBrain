package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.User;
import com.pixel.stupidbrain.entity.UsersAnswer;
import com.pixel.stupidbrain.entity.request.SaveUsersAnswerRequest;
import com.pixel.stupidbrain.entity.response.UsersAnswerResponse;
import com.pixel.stupidbrain.exception.*;
import com.pixel.stupidbrain.repository.QuestionRepository;
import com.pixel.stupidbrain.repository.TrueAnswerRepository;
import com.pixel.stupidbrain.repository.UserRepository;
import com.pixel.stupidbrain.repository.UsersAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UsersAnswerService implements UsersAnswerOperations{

    private final UsersAnswerRepository usersAnswerRepository;

    private final TrueAnswerRepository trueAnswerRepository;

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    @Autowired
    public UsersAnswerService(UsersAnswerRepository usersAnswerRepository,
                              TrueAnswerRepository trueAnswerRepository,
                              QuestionRepository questionRepository,
                              UserRepository userRepository) {
        this.usersAnswerRepository = usersAnswerRepository;
        this.trueAnswerRepository = trueAnswerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UsersAnswerResponse create(SaveUsersAnswerRequest request) {
        if (request == null) throw new RequestIsEmptyException();

        UsersAnswer usersAnswer = new UsersAnswer();

        String answer = request.getAnswer();

        if (answer == null ||answer.isEmpty()) throw new FieldIsEmptyException("Answer");

        UUID questionId = request.getQuestion();
        UUID userId = request.getUser();

        if (userId == null) throw new FieldIsEmptyException("User");
        if (questionId == null) throw new FieldIsEmptyException("Question");

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        setUsersAnswer(usersAnswer, answer, question, user);

        questionRepository.save(question);
        return UsersAnswerResponse.fromUsersAnswer(usersAnswerRepository.save(usersAnswer));
    }

    @Override
    public UsersAnswerResponse getById(UUID id) {
        if (id == null) throw new FieldIsEmptyException("ID");

        return UsersAnswerResponse.fromUsersAnswer(usersAnswerRepository.findById(id)
                .orElseThrow(() -> new UsersAnswerNotFoundException(id)));
    }

    @Override
    public void update(UUID id, SaveUsersAnswerRequest request) {
        if (id == null) throw new FieldIsEmptyException("ID");
        if (request == null) throw new RequestIsEmptyException();

        UsersAnswer usersAnswerExisting = usersAnswerRepository.findById(id)
                .orElseThrow(() -> new UsersAnswerNotFoundException(id));

        String answer = request.getAnswer();
        if (answer == null || answer.isEmpty()) throw new FieldIsEmptyException("Answer");

        UUID userId = request.getUser();
        if (userId == null) throw new FieldIsEmptyException("User");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        UUID questionId = request.getQuestion();
        if (questionId == null) throw new FieldIsEmptyException("Question");

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        setUsersAnswer(usersAnswerExisting, answer, question, user);

        usersAnswerRepository.save(usersAnswerExisting);
    }

    @Override
    public void deleteById(UUID id) {
        if (id == null) throw new FieldIsEmptyException("ID");

        UsersAnswer usersAnswer = usersAnswerRepository.findById(id)
                .orElseThrow(() -> new UsersAnswerNotFoundException(id));

        usersAnswerRepository.deleteById(usersAnswer.getId());
    }

    @Override
    public List<UsersAnswerResponse> getAllByQuestionId(UUID id) {
        if (id == null) throw new FieldIsEmptyException("ID");

        return UsersAnswerResponse.fromUsersAnswers(usersAnswerRepository
                .findAllByQuestionEquals(
                        questionRepository
                                .findById(id).orElseThrow(() -> new QuestionNotFoundException(id))));
    }

    private void setUsersAnswer(UsersAnswer usersAnswer,
                                String answer,
                                Question question,
                                User user) {
        boolean exists = usersAnswerRepository
                .existsByQuestionEqualsAndAnswerEqualsAndAndUserEquals(
                        question,
                        answer,
                        user
                );

        if (exists) throw new AnswersAlreadyExistsException(answer);

        boolean correctAnswer = trueAnswerRepository
                .existsByQuestionEqualsAndAnswerEquals(question, answer);

        int rating = question.getRating();

        question.setRating(correctAnswer ? --rating : ++rating);
        usersAnswer.setAnswer(answer);
        usersAnswer.setCorrectAnswer(correctAnswer);
        usersAnswer.setQuestion(question);
        usersAnswer.setUser(user);
    }
}
