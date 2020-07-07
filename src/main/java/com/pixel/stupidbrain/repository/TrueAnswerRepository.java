package com.pixel.stupidbrain.repository;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.TrueAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrueAnswerRepository extends JpaRepository<TrueAnswer, UUID> {

    List<TrueAnswer> findAllByQuestionEquals(Question question);

    boolean existsByQuestionEqualsAndAnswerEquals(Question question, String answer);
}
