package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.request.SaveQuestionRequest;
import com.pixel.stupidbrain.entity.response.QuestionResponse;
import com.pixel.stupidbrain.entity.response.TrueAnswerResponse;
import com.pixel.stupidbrain.service.QuestionOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/questions")
public class QuestionRestController {

    private final QuestionOperations questionOperations;

    public QuestionRestController(QuestionOperations questionOperations) {
        this.questionOperations = questionOperations;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponse create(@RequestBody SaveQuestionRequest request){
        return questionOperations.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionResponse getById(@PathVariable UUID id){
        return questionOperations.getById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable UUID id,
                       @RequestBody SaveQuestionRequest request){
        questionOperations.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id){
        questionOperations.deleteById(id);
    }

    @GetMapping(params = "rating-less")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionResponse> getAllByRatingLessThan(
            @RequestParam(name = "rating-less") int ratingLess){
        return questionOperations.getAllByRatingLessThan(ratingLess);
    }

    @GetMapping(params = "rating-greater")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionResponse> getAllByRatingGreaterThan(
            @RequestParam(name = "rating-greater") int ratingGreater){
        return questionOperations.getAllByRatingGreaterThan(ratingGreater);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionResponse> getAllQuestion(){
        return questionOperations.getAll();
    }

    @GetMapping("/{id}/answers")
    @ResponseStatus(HttpStatus.OK)
    public List<TrueAnswerResponse> getTrueAnswers(@PathVariable UUID id){
        return questionOperations.getAllTrueAnswers(id);
    }
}
