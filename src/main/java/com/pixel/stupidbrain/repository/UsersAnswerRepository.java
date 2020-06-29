package com.pixel.stupidbrain.repository;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.UsersAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsersAnswerRepository extends JpaRepository<UsersAnswer, UUID> {

    int countAllByCorrectAnswerAndQuestion(boolean correctAnswer, Question question);
}
