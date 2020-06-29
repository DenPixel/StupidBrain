package com.pixel.stupidbrain.repository;

import com.pixel.stupidbrain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    List<Question> findAllByRatingLessThan(int rating);

    List<Question> findAllByRatingGreaterThan(int rating);
}
