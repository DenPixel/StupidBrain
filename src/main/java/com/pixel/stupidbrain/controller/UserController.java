package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.request.SaveUserRequest;
import com.pixel.stupidbrain.entity.response.UserResponse;
import com.pixel.stupidbrain.service.UserOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserOperations userOperations;

    @Autowired
    public UserController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserResponse create(SaveUserRequest saveUserRequest){
        return UserResponse.fromUser(userOperations.create(saveUserRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{id}")
    public UserResponse get(@PathVariable UUID id){
        return UserResponse.fromUser(userOperations.getById(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable UUID id,
                       @RequestBody SaveUserRequest request){
        userOperations.update(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        userOperations.deleteById(id);
    }
}
