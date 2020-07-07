package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.request.SaveQuestionRequest;
import com.pixel.stupidbrain.entity.request.SaveUserRequest;
import com.pixel.stupidbrain.entity.response.QuestionResponse;
import com.pixel.stupidbrain.entity.response.UserResponse;
import com.pixel.stupidbrain.exception.FieldIsEmptyException;
import com.pixel.stupidbrain.exception.QuestionNotFoundException;
import com.pixel.stupidbrain.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionOperations questionOperations;

    @Autowired
    private UserOperations userOperations;

    private UserResponse userResponse;

    @BeforeEach
    void before(){
        String user = "test";
        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(user);
        userRequest.setPassword(user);
        userRequest.setRePassword(user);
        userRequest.setEmail(user);

        userResponse = userOperations.create(userRequest);

        assertNotNull(userResponse);
    }

    @AfterEach
    void after(){
        userOperations.deleteById(userResponse.getId());
    }

    @Test
    void create() {
        String name = "test";
        String description = "test";

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setName(name);
        questionRequest.setDescription(description);
        questionRequest.setUser(userResponse.getId());

        QuestionResponse questionResponse = questionOperations.create(questionRequest);

        assertNotNull(questionResponse);
        assertNotNull(questionResponse.getId());

        assertEquals(name, questionResponse.getName());
        assertEquals(description, questionResponse.getDescription());
        assertEquals(userResponse.getId(), questionResponse.getUser());

        questionOperations.deleteById(questionResponse.getId());
    }

    @Test
    void createWithoutName(){
        String description = "test";

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setDescription(description);
        questionRequest.setUser(userResponse.getId());

        QuestionResponse questionResponse = null;

        try {
            questionResponse = questionOperations.create(questionRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"Name\" is empty", e.getReason());
        }

        assertNull(questionResponse);
    }

    @Test
    void createWithoutDescription(){
        String name = "test";

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setName(name);
        questionRequest.setUser(userResponse.getId());

        QuestionResponse questionResponse = null;

        try {
            questionResponse = questionOperations.create(questionRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"Description\" is empty", e.getReason());
        }

        assertNull(questionResponse);
    }

    @Test
    void createWithoutUser(){
        String name = "test";
        String description = "test";

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setName(name);
        questionRequest.setDescription(description);

        QuestionResponse questionResponse = null;

        try {
            questionResponse = questionOperations.create(questionRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"User\" is empty", e.getReason());
        }

        assertNull(questionResponse);
    }

    @Test
    void createWithRandomUser(){
        String name = "test";
        String description = "test";
        UUID randomUUID = UUID.randomUUID();

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setName(name);
        questionRequest.setDescription(description);
        questionRequest.setUser(randomUUID);

        QuestionResponse questionResponse = null;

        try {
            questionResponse = questionOperations.create(questionRequest);
        } catch (UserNotFoundException e) {
            assertEquals("User with id " + randomUUID + " not found", e.getReason());
        }

        assertNull(questionResponse);
    }

    @Test
    void getById() {
        String name = "test";
        String description = "test";

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setName(name);
        questionRequest.setDescription(description);
        questionRequest.setUser(userResponse.getId());

        QuestionResponse questionResponse = questionOperations.create(questionRequest);

        QuestionResponse response = questionOperations.getById(questionResponse.getId());

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(questionResponse.getId(), response.getId());
        assertEquals(questionResponse.getName(), response.getName());
        assertEquals(questionResponse.getDescription(), response.getDescription());
        assertEquals(questionResponse.getUser(), response.getUser());

        questionOperations.deleteById(questionResponse.getId());
    }


    @Test
    void getByRandomId(){
        UUID randomUUID = UUID.randomUUID();
        QuestionResponse questionResponse = null;

        try {
            questionResponse = questionOperations.getById(randomUUID);
        } catch (QuestionNotFoundException e) {
            assertEquals("Question with id " + randomUUID + " not found", e.getReason());
        }

        assertNull(questionResponse);
    }

    @Test
    void update() {
        String name = "test";
        String newName = "test";
        String description = "new test";
        String newDescription = "new test";

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setName(name);
        questionRequest.setDescription(description);
        questionRequest.setUser(userResponse.getId());

        QuestionResponse questionResponse = questionOperations.create(questionRequest);

        questionRequest.setName(newName);
        questionRequest.setDescription(newDescription);

        UUID id = questionResponse.getId();

        questionOperations.update(id, questionRequest);

        QuestionResponse newQuestionResponse = questionOperations.getById(id);

        assertNotNull(newQuestionResponse);
        assertNotNull(newQuestionResponse.getId());
        assertEquals(id, newQuestionResponse.getId());
        assertEquals(newName, newQuestionResponse.getName());
        assertEquals(newDescription, newQuestionResponse.getDescription());

        questionOperations.deleteById(id);
    }

    @Test
    void updateWithoutName(){
        String name = "test";
        String description = "test";
        String newDescription = "new test";

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setName(name);
        questionRequest.setDescription(description);
        questionRequest.setUser(userResponse.getId());

        QuestionResponse questionResponse = questionOperations.create(questionRequest);

        questionRequest.setName(null);
        questionRequest.setDescription(newDescription);

        UUID id = questionResponse.getId();

        try {
            questionOperations.update(id, questionRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"Name\" is empty", e.getReason());
        }

        QuestionResponse response = questionOperations.getById(id);

        assertNotEquals(null, response.getName());
        assertEquals(name, response.getName());
        assertNotEquals(newDescription, response.getDescription());
        assertEquals(description, response.getDescription());

        questionOperations.deleteById(id);
    }

    @Test
    void updateWithoutDescription(){
        String name = "test";
        String newName = "new test";
        String description = "test";

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setName(name);
        questionRequest.setDescription(description);
        questionRequest.setUser(userResponse.getId());

        QuestionResponse questionResponse = questionOperations.create(questionRequest);

        questionRequest.setName(newName);
        questionRequest.setDescription(null);

        UUID id = questionResponse.getId();

        try {
            questionOperations.update(id, questionRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"Description\" is empty", e.getReason());
        }

        QuestionResponse response = questionOperations.getById(id);

        assertNotEquals(newName, response.getName());
        assertEquals(name, response.getName());
        assertNotEquals(null, response.getDescription());
        assertEquals(description, response.getDescription());

        questionOperations.deleteById(id);
    }

    @Test
    void updateRating() {
        String name = "test";
        String description = "test";
        int rating = 500;

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setName(name);
        questionRequest.setDescription(description);
        questionRequest.setUser(userResponse.getId());

        QuestionResponse questionResponse = questionOperations.create(questionRequest);

        UUID id = questionResponse.getId();

        questionOperations.updateRating(id, 500);

        QuestionResponse response = questionOperations.getById(id);

        assertEquals(rating, response.getRating());

        questionOperations.deleteById(id);
    }

    @Test
    void deleteById() {
        String name = "test";
        String description = "test";

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setName(name);
        questionRequest.setDescription(description);
        questionRequest.setUser(userResponse.getId());

        QuestionResponse questionResponse = questionOperations.create(questionRequest);

        UUID id = questionResponse.getId();

        questionOperations.deleteById(id);

        try {
            questionOperations.getById(id);
        } catch (QuestionNotFoundException e) {
            assertEquals("Question with id " + id + " not found", e.getReason());
        }
    }

    @Test
    void deleteByRandomId(){
        UUID randomUUID = UUID.randomUUID();

        try {
            questionOperations.deleteById(randomUUID);
        } catch (QuestionNotFoundException e) {
            assertEquals("Question with id " + randomUUID + " not found", e.getReason());
        }

    }
}