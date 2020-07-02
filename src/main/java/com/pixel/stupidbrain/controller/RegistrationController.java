package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.request.SaveUserRequest;
import com.pixel.stupidbrain.exception.StupidBrainException;
import com.pixel.stupidbrain.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class RegistrationController {

    private final UserOperations userOperations;

    public RegistrationController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public String createUser(Model model, SaveUserRequest saveUserRequest) {
        String message = null;

        try {
            saveUserRequest.addRole("USER");
            userOperations.create(saveUserRequest);
        }catch (StupidBrainException e){
            message = e.getReason();
        }

        if (message != null) {
            model.addAttribute("message", message);
            return "registration";
        }
        
        return "login";
    }

}
