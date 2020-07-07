package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.request.SaveUserRequest;
import com.pixel.stupidbrain.exception.StupidBrainException;
import com.pixel.stupidbrain.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserOperations userOperations;

    public RegistrationController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String registration(){
        return "registration";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(Model model, SaveUserRequest saveUserRequest) {
        try {
            saveUserRequest.addRole("USER");
            userOperations.create(saveUserRequest);
        }catch (StupidBrainException e){
            model.addAttribute("message", e.getReason());
            return "registration";
        }

        return "login";
    }
}
