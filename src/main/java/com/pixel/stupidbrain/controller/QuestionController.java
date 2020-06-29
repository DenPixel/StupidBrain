package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.request.SaveQuestionRequest;
import com.pixel.stupidbrain.entity.response.QuestionResponse;
import com.pixel.stupidbrain.service.QuestionOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionOperations questionOperations;

    public QuestionController(QuestionOperations questionOperations) {
        this.questionOperations = questionOperations;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public QuestionResponse create(@RequestBody SaveQuestionRequest request){
        return QuestionResponse.fromQuestion(questionOperations.create(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public QuestionResponse getById(@PathVariable UUID id){
        return QuestionResponse.fromQuestion(questionOperations.getById(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable UUID id,
                       @RequestBody SaveQuestionRequest request){
        questionOperations.update(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id){
        questionOperations.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<QuestionResponse> getAllByRatingLessThan(
            @RequestParam(name = "rating-less") int ratingLess
    ){
        return questionOperations.getAllByRatingLessThan(ratingLess);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<QuestionResponse> getAllByRatingGreaterThan(
            @RequestParam(name = "rating-greater") int ratingGreater
    ){
        return questionOperations.getAllByRatingGreaterThan(ratingGreater);
    }
}
