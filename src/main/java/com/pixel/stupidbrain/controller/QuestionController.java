package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.request.SaveQuestionRequest;
import com.pixel.stupidbrain.entity.response.QuestionResponse;
import com.pixel.stupidbrain.entity.response.UserResponse;
import com.pixel.stupidbrain.exception.StupidBrainException;
import com.pixel.stupidbrain.service.QuestionOperations;
import com.pixel.stupidbrain.service.TrueAnswerOperations;
import com.pixel.stupidbrain.service.UserOperations;
import com.pixel.stupidbrain.service.UsersAnswerOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionOperations questionOperations;
    private final TrueAnswerOperations trueAnswerOperations;
    private final UserOperations userOperations;
    private final UsersAnswerOperations usersAnswerOperations;

    @Autowired
    public QuestionController(QuestionOperations questionOperations,
                              TrueAnswerOperations trueAnswerOperations,
                              UserOperations userOperations,
                              UsersAnswerOperations usersAnswerOperations) {
        this.questionOperations = questionOperations;
        this.trueAnswerOperations = trueAnswerOperations;
        this.userOperations = userOperations;
        this.usersAnswerOperations = usersAnswerOperations;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getAll(Model model){
        List<QuestionResponse>  questions = questionOperations.getAll();

        model.addAllAttributes(Map.of("questionsList", questions));

        return "questions";
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getQuestion(@AuthenticationPrincipal User user,
                              Model model,
                              @PathVariable UUID id){
        QuestionResponse question = null;

        try {
            question = questionOperations.getById(id);
        }catch (StupidBrainException e){
            model.addAttribute("message", e.getReason());
        }

        if (question != null) {

            model.addAttribute("usersAnswers", usersAnswerOperations.getAllByQuestionId(id));
            model.addAttribute("trueAnswers", trueAnswerOperations.getAllByQuestionId(id));
            model.addAttribute("user", userOperations.getByUsername(user.getUsername()));
            model.addAttribute("question", question);
        }

        return "question";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createQuestion(@AuthenticationPrincipal User user,
                                 SaveQuestionRequest request,
                                 Model model){
        UserResponse userResponse = userOperations.getByUsername(user.getUsername());
        request.setUser(userResponse.getId());
        QuestionResponse questionResponse;

        try {
            questionResponse = questionOperations.create(request);
        } catch (StupidBrainException e){
            model.addAttribute("message", e.getReason());
            return getAll(model);
        }

        return getQuestion(user, model, questionResponse.getId());
    }
}
