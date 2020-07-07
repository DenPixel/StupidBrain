package com.pixel.stupidbrain.repository;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.User;
import com.pixel.stupidbrain.entity.UsersAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UsersAnswerRepository extends JpaRepository<UsersAnswer, UUID> {

    int countAllByCorrectAnswerAndQuestion(boolean correctAnswer, Question question);

    boolean existsByQuestionEqualsAndAnswerEqualsAndAndUserEquals(Question question,
                                                                  String answer,
                                                                  User user);
    List<UsersAnswer> findAllByQuestionEquals(Question question);
}
