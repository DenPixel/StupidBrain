package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.request.SaveUsersAnswerRequest;
import com.pixel.stupidbrain.entity.response.UserResponse;
import com.pixel.stupidbrain.entity.response.UsersAnswerResponse;
import com.pixel.stupidbrain.exception.StupidBrainException;
import com.pixel.stupidbrain.service.QuestionOperations;
import com.pixel.stupidbrain.service.UserOperations;
import com.pixel.stupidbrain.service.UsersAnswerOperations;
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
@RequestMapping("/users-answer")
public class UsersAnswerController {

    private final UsersAnswerOperations usersAnswerOperations;
    private final QuestionOperations questionOperations;
    private final UserOperations userOperations;

    @Autowired
    public UsersAnswerController(UsersAnswerOperations usersAnswerOperations,
                                 QuestionOperations questionOperations,
                                 UserOperations userOperations) {
        this.usersAnswerOperations = usersAnswerOperations;
        this.questionOperations = questionOperations;
        this.userOperations = userOperations;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@AuthenticationPrincipal User user,
                         SaveUsersAnswerRequest request,
                         Model model){
        UserResponse userResponse = userOperations.getByUsername(user.getUsername());
        request.setUser(userResponse.getId());
        String attributeMessage = "message";

        try {
            UsersAnswerResponse usersAnswer = usersAnswerOperations.create(request);

            if (usersAnswer.isCorrectAnswer()) model.addAttribute(attributeMessage, "You are right!!!");
            else model.addAttribute(attributeMessage,"This is the wrong answer");

        } catch (StupidBrainException e){
            model.addAttribute(attributeMessage, e.getReason());
        }

        UUID question = request.getQuestion();

        model.addAttribute("usersAnswers", usersAnswerOperations.getAllByQuestionId(question));
        model.addAttribute("user", userResponse);
        model.addAttribute("question", questionOperations.getById(question));

        return "question";
    }
}
