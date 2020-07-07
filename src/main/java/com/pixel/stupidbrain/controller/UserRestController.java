package com.pixel.stupidbrain.controller;

import com.pixel.stupidbrain.entity.request.SaveUserRequest;
import com.pixel.stupidbrain.entity.response.UserResponse;
import com.pixel.stupidbrain.service.UserOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserOperations userOperations;

    @Autowired
    public UserRestController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody SaveUserRequest saveUserRequest){
        return userOperations.create(saveUserRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse get(@PathVariable UUID id){
        return userOperations.getById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable UUID id,
                       @RequestBody SaveUserRequest request){
        userOperations.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        userOperations.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAll(){
        return userOperations.getAll();
    }
}
