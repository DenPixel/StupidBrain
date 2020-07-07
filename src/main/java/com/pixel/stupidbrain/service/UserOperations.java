package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.request.SaveUserRequest;
import com.pixel.stupidbrain.entity.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserOperations {

    UserResponse create(SaveUserRequest request);

    UserResponse getById(UUID id);

    void update(UUID id, SaveUserRequest request);

    void deleteById(UUID id);

    List<UserResponse> getAll();

    boolean existByUsername(String username);

    UserResponse getByUsername(String username);
}
