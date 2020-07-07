package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.request.SaveTrueAnswerRequest;
import com.pixel.stupidbrain.exception.StupidBrainException;
import com.pixel.stupidbrain.service.QuestionOperations;
import com.pixel.stupidbrain.service.TrueAnswerOperations;
import com.pixel.stupidbrain.service.UserOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Controller
@RequestMapping("/true-answers")
public class TrueAnswerController {

    private final TrueAnswerOperations trueAnswerOperations;
    private final UserOperations userOperations;
    private final QuestionOperations questionOperations;

    @Autowired
    public TrueAnswerController(TrueAnswerOperations trueAnswerOperations,
                                UserOperations userOperations,
                                QuestionOperations questionOperations) {
        this.trueAnswerOperations = trueAnswerOperations;
        this.userOperations = userOperations;
        this.questionOperations = questionOperations;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Model model,
                         SaveTrueAnswerRequest request,
                         @AuthenticationPrincipal User user){

        try {
            trueAnswerOperations.create(request);
        }catch (StupidBrainException e){
            model.addAttribute("message", e.getReason());
        }

        UUID question = request.getQuestion();

        model.addAttribute("trueAnswers", trueAnswerOperations.getAllByQuestionId(question));
        model.addAttribute("user", userOperations.getByUsername(user.getUsername()));
        model.addAttribute("question", questionOperations.getById(question));
        return "question";
    }
}
