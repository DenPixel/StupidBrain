package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.request.SaveQuestionRequest;
import com.pixel.stupidbrain.entity.request.SaveUserRequest;
import com.pixel.stupidbrain.entity.request.SaveUsersAnswerRequest;
import com.pixel.stupidbrain.entity.response.QuestionResponse;
import com.pixel.stupidbrain.entity.response.UserResponse;
import com.pixel.stupidbrain.entity.response.UsersAnswerResponse;
import com.pixel.stupidbrain.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsersAnswerServiceTest {

    @Autowired
    private QuestionOperations questionOperations;

    @Autowired
    private UserOperations userOperations;

    @Autowired
    private UsersAnswerOperations usersAnswerOperations;

    private UserResponse testUser;
    private QuestionResponse testQuestion;

    @BeforeEach
    void before(){
        String user = "test";
        SaveUserRequest userRequest = new SaveUserRequest();
        userRequest.setUsername(user);
        userRequest.setPassword(user);
        userRequest.setRePassword(user);
        userRequest.setEmail(user);

        testUser = userOperations.create(userRequest);

//        String newUser = "test";
//        SaveUserRequest newUserRequest = new SaveUserRequest();
//        newUserRequest.setUsername(newUser);
//        newUserRequest.setPassword(newUser);
//        newUserRequest.setRePassword(newUser);
//        newUserRequest.setEmail(newUser);
//
//        testUser = userOperations.create(newUserRequest);

        assertNotNull(testUser);

        String question = "test";

        SaveQuestionRequest questionRequest = new SaveQuestionRequest();
        questionRequest.setName(question);
        questionRequest.setDescription(question);
        questionRequest.setUser(testUser.getId());

        testQuestion = questionOperations.create(questionRequest);

        assertNotNull(testQuestion);
    }

    @AfterEach
    void after(){
        userOperations.deleteById(testUser.getId());
    }
    
    @Test
    void create() {
        String answer = "test";

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = usersAnswerOperations.create(answerRequest);

        assertNotNull(answerResponse);
        assertNotNull(answerResponse.getId());

        assertEquals(answer, answerResponse.getAnswer());
        assertEquals(testUser.getId(), answerResponse.getUser());
        assertEquals(testQuestion.getId(), answerResponse.getQuestion());

        usersAnswerOperations.deleteById(answerResponse.getId());
    }

    @Test
    void createWithoutAnswer(){
        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setQuestion(testUser.getId());
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = null;

        try {
            answerResponse = usersAnswerOperations.create(answerRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"Answer\" is empty", e.getReason());
        }

        assertNull(answerResponse);
    }

    @Test
    void createWithoutUser(){
        String answer = "test";

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = null;

        try {
            answerResponse = usersAnswerOperations.create(answerRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"User\" is empty", e.getReason());
        }

        assertNull(answerResponse);
    }

    @Test
    void createWithoutQuestion(){
        String answer = "test";

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());

        UsersAnswerResponse answerResponse = null;

        try {
            answerResponse = usersAnswerOperations.create(answerRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"Question\" is empty", e.getReason());
        }

        assertNull(answerResponse);
    }

    @Test
    void createWithRandomUser(){
        String answer = "test";
        UUID randomUUID = UUID.randomUUID();

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(randomUUID);
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = null;

        try {
            answerResponse = usersAnswerOperations.create(answerRequest);
        } catch (UserNotFoundException e) {
            assertEquals("User with id " + randomUUID + " not found", e.getReason());
        }

        assertNull(answerResponse);
    }

    @Test
    void createWithRandomQuestion(){
        String answer = "test";
        UUID randomUUID = UUID.randomUUID();

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());
        answerRequest.setQuestion(randomUUID);

        UsersAnswerResponse answerResponse = null;

        try {
            answerResponse = usersAnswerOperations.create(answerRequest);
        } catch (QuestionNotFoundException e) {
            assertEquals("Question with id " + randomUUID + " not found", e.getReason());
        }

        assertNull(answerResponse);
    }

    @Test
    void createAlreadyAnswer(){
        String answer = "test";

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = usersAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        UsersAnswerResponse response = null;

        try {
            response = usersAnswerOperations.create(answerRequest);
        } catch (AnswersAlreadyExistsException e) {
            assertEquals("Answer \"" + answer + "\" already exists", e.getReason());
        }

        assertNull(response);

        usersAnswerOperations.deleteById(id);
    }

    @Test
    void createWithNullRequest(){
        UsersAnswerResponse answerResponse = null;

        try {
            answerResponse = usersAnswerOperations.create(null);
        } catch (RequestIsEmptyException e) {
            assertEquals("Request is empty", e.getReason());
        }

        assertNull(answerResponse);
    }

    @Test
    void getById() {
        String answer = "test";

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = usersAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        UsersAnswerResponse response = usersAnswerOperations.getById(id);

        assertNotNull(response);
        assertNotNull(response.getId());

        assertEquals(answer, response.getAnswer());
        assertEquals(testUser.getId(), response.getUser());
        assertEquals(testQuestion.getId(),response.getQuestion());

        usersAnswerOperations.deleteById(id);
    }

    @Test
    void getByRandomId(){
        UUID randomUUID = UUID.randomUUID();

        UsersAnswerResponse response = null;

        try {
            response = usersAnswerOperations.getById(randomUUID);
        } catch (UsersAnswerNotFoundException e) {
            assertEquals("Users answer with id " + randomUUID + " not found", e.getReason());
        }

        assertNull(response);
    }

    @Test
    void getWithNullId(){
        UsersAnswerResponse answerResponse = null;

        try {
            answerResponse = usersAnswerOperations.getById(null);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"ID\" is empty", e.getReason());
        }

        assertNull(answerResponse);
    }

    @Test
    void update() {
        String answer = "test";
        String newAnswer = "new test";

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = usersAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        answerRequest.setAnswer(newAnswer);

        usersAnswerOperations.update(id, answerRequest);

        UsersAnswerResponse response = usersAnswerOperations.getById(id);

        assertNotNull(response);
        assertNotNull(response.getId());

        assertEquals(id, response.getId());
        assertEquals(newAnswer, response.getAnswer());
        assertEquals(testUser.getId(), response.getUser());
        assertEquals(testQuestion.getId(),response.getQuestion());

        usersAnswerOperations.deleteById(id);
    }

    @Test
    void updateWithoutAnswer(){
        String answer = "test";

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = usersAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        answerRequest.setAnswer(null);

        try {
            usersAnswerOperations.update(id, answerRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"Answer\" is empty", e.getReason());
        }

        UsersAnswerResponse response = usersAnswerOperations.getById(id);

        assertEquals(answer, response.getAnswer());

        usersAnswerOperations.deleteById(id);
    }

    @Test
    void updateWithoutUser(){
        String answer = "test";

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = usersAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        answerRequest.setUser(null);

        try {
            usersAnswerOperations.update(id, answerRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"User\" is empty", e.getReason());
        }

        UsersAnswerResponse response = usersAnswerOperations.getById(id);

        assertEquals(testUser.getId(), response.getUser());

        usersAnswerOperations.deleteById(id);
    }

    @Test
    void updateWithoutQuestion(){
        String answer = "test";

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = usersAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        answerRequest.setAnswer(null);

        try {
            usersAnswerOperations.update(id, answerRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"Answer\" is empty", e.getReason());
        }

        UsersAnswerResponse response = usersAnswerOperations.getById(id);

        assertEquals(answer, response.getAnswer());

        usersAnswerOperations.deleteById(id);
    }

    @Test
    void updateRandomUser() {
        String answer = "test";
        UUID randomUUID = UUID.randomUUID();

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = usersAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        answerRequest.setUser(randomUUID);

        try {
            usersAnswerOperations.update(id, answerRequest);
        } catch (UserNotFoundException e) {
            assertEquals("User with id " + randomUUID + " not found", e.getReason());
        }

        UsersAnswerResponse response = usersAnswerOperations.getById(id);

        assertNotNull(response);
        assertNotNull(response.getId());

        assertEquals(id, response.getId());
        assertEquals(answer, response.getAnswer());
        assertEquals(testUser.getId(), response.getUser());
        assertEquals(testQuestion.getId(),response.getQuestion());

        usersAnswerOperations.deleteById(id);
    }

    @Test
    void updateRandomQuestion() {
        String answer = "test";
        UUID randomUUID = UUID.randomUUID();

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = usersAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        answerRequest.setQuestion(randomUUID);

        try {
            usersAnswerOperations.update(id, answerRequest);
        } catch (QuestionNotFoundException e) {
            assertEquals("Question with id " + randomUUID + " not found", e.getReason());
        }

        UsersAnswerResponse response = usersAnswerOperations.getById(id);

        assertNotNull(response);
        assertNotNull(response.getId());

        assertEquals(id, response.getId());
        assertEquals(answer, response.getAnswer());
        assertEquals(testUser.getId(), response.getUser());
        assertEquals(testQuestion.getId(),response.getQuestion());

        usersAnswerOperations.deleteById(id);
    }

    @Test
    void updateWithNullId(){
        try {
            usersAnswerOperations.update(null, new SaveUsersAnswerRequest());
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"ID\" is empty", e.getReason());
        }

    }

    @Test
    void updateWithNullRequest(){
        try {
            usersAnswerOperations.update(UUID.randomUUID(), null);
        } catch (RequestIsEmptyException e) {
            assertEquals("Request is empty", e.getReason());
        }
    }

    @Test
    void deleteById() {
        String answer = "test";

        SaveUsersAnswerRequest answerRequest = new SaveUsersAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setUser(testUser.getId());
        answerRequest.setQuestion(testQuestion.getId());

        UsersAnswerResponse answerResponse = usersAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        usersAnswerOperations.deleteById(id);

        UsersAnswerResponse response = null;

        try {
            response = usersAnswerOperations.getById(id);
        } catch (UsersAnswerNotFoundException e) {
            assertEquals("Users answer with id " + id + " not found", e.getReason());
        }

        assertNull(response);
    }

    @Test
    void deleteByRandomId(){
        UUID randomUUID = UUID.randomUUID();

        try {
            usersAnswerOperations.deleteById(randomUUID);
        } catch (UsersAnswerNotFoundException e) {
            assertEquals("Users answer with id " + randomUUID + " not found", e.getReason());
        }
    }

    @Test
    void deleteByNullId(){
        try {
            usersAnswerOperations.deleteById(null);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"ID\" is empty", e.getReason());
        }
    }

}