package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.Question;
import com.pixel.stupidbrain.entity.User;
import com.pixel.stupidbrain.entity.request.SaveUserRequest;

import java.util.List;
import java.util.UUID;

public interface UserOperations {

    User create(SaveUserRequest request);

    User getById(UUID id);

    void update(UUID id, SaveUserRequest request);

    void deleteById(UUID id);
}
