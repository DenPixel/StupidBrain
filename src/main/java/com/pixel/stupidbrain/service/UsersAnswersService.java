package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.User;
import com.pixel.stupidbrain.entity.UsersAnswer;
import com.pixel.stupidbrain.entity.request.SaveUsersAnswerRequest;
import com.pixel.stupidbrain.exception.QuestionNotFoundException;
import com.pixel.stupidbrain.exception.UserNotFoundException;
import com.pixel.stupidbrain.exception.UsersAnswerNotFoundException;
import com.pixel.stupidbrain.repository.QuestionRepository;
import com.pixel.stupidbrain.repository.TrueAnswerRepository;
import com.pixel.stupidbrain.repository.UserRepository;
import com.pixel.stupidbrain.repository.UsersAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UsersAnswersService implements UsersAnswerOperations{

    private final UsersAnswerRepository usersAnswerRepository;

    private final TrueAnswerRepository trueAnswerRepository;

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    @Autowired
    public UsersAnswersService(UsersAnswerRepository usersAnswerRepository,
                               TrueAnswerRepository trueAnswerRepository,
                               QuestionRepository questionRepository,
                               UserRepository userRepository) {
        this.usersAnswerRepository = usersAnswerRepository;
        this.trueAnswerRepository = trueAnswerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }


    @Override
    public UsersAnswer create(SaveUsersAnswerRequest request) {

        UsersAnswer usersAnswer = new UsersAnswer();

        String answer = request.getAnswer();
        UUID idQuestion = request.getQuestion();
        UUID idUser = request.getUser();
        Question question = questionRepository.findById(idQuestion)
                .orElseThrow(() -> new QuestionNotFoundException(idQuestion));
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(idUser));
        boolean correctAnswer = trueAnswerRepository.existsByQuestionEqualsAndAnswer(
                question,
                answer
        );

        usersAnswer.setAnswer(answer);
        usersAnswer.setCorrectAnswer(correctAnswer);
        usersAnswer.setQuestion(question);
        usersAnswer.setUser(user);

        return usersAnswerRepository.save(usersAnswer);
    }

    @Override
    public UsersAnswer get(UUID id) {
        return usersAnswerRepository.findById(id)
                .orElseThrow(() -> new UsersAnswerNotFoundException(id));
    }

    @Override
    public void update(UUID id, SaveUsersAnswerRequest request) {
    }

    @Override
    public void deleteById(UUID id) {
        usersAnswerRepository.deleteById(id);
    }
}
