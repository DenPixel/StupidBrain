package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.TrueAnswer;
import com.pixel.stupidbrain.entity.request.SaveQuestionRequest;
import com.pixel.stupidbrain.entity.response.QuestionResponse;
import com.pixel.stupidbrain.service.QuestionOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
public class QuestionRestController {

    private final QuestionOperations questionOperations;

    public QuestionRestController(QuestionOperations questionOperations) {
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
    @GetMapping(params = "rating-less")
    public List<QuestionResponse> getAllByRatingLessThan(
            @RequestParam(name = "rating-less") int ratingLess){
        return getListResponseFromQuestions(questionOperations.getAllByRatingLessThan(ratingLess));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = "rating-greater")
    public List<QuestionResponse> getAllByRatingGreaterThan(
            @RequestParam(name = "rating-greater") int ratingGreater){
        return getListResponseFromQuestions(questionOperations.getAllByRatingGreaterThan(ratingGreater));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<QuestionResponse> getAllQuestion(){
        return getListResponseFromQuestions(questionOperations.getAll());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/answers")
    public List<TrueAnswer> getTrueAnswers(@PathVariable UUID id){
        return questionOperations.getAllQuestionsTrueAnswers(id);
    }

    private List<QuestionResponse> getListResponseFromQuestions(Collection<Question> questions){
        return questions.stream()
                .map(QuestionResponse::fromQuestion)
                .collect(Collectors.toList());

    }
}
