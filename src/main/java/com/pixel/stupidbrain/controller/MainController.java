package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.response.UserResponse;
import com.pixel.stupidbrain.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

@Controller
@RequestMapping("/")
public class MainController {

    private final UserOperations userOperations;

    public MainController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String main(@AuthenticationPrincipal User user, Model model){

        if (user != null) {
            UserResponse userResponse = userOperations.getByUsername(user.getUsername());
            model.addAttribute("user", userResponse);
            Set<String> roles = userResponse.getRoles();
            if (!roles.isEmpty()) {
                model.addAttribute("roles", roles);
            }
        }

        return "index";
    }
}
