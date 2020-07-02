package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.response.QuestionResponse;
import com.pixel.stupidbrain.service.QuestionOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class QuestionController {

    private final QuestionOperations questionOperations;

    @Autowired
    public QuestionController(QuestionOperations questionOperations) {
        this.questionOperations = questionOperations;
    }



    @GetMapping("/questions")
    public String getAll(Model model){
        List<QuestionResponse> questions = getListResponseFromQuestions(questionOperations.getAll());

        model.addAllAttributes(questions);
        return "questions";
    }

    @GetMapping("/questions/{id}")
    public String getQuestion(Model model, @PathVariable UUID id){
        Question question = questionOperations.getById(id);

        model.addAttribute("name", question.getName());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("rating", question.getRating());

        return "question";
    }

    private List<QuestionResponse> getListResponseFromQuestions(Collection<Question> questions){
        return questions.stream()
                .map(QuestionResponse::fromQuestion)
                .collect(Collectors.toList());
    }
}
