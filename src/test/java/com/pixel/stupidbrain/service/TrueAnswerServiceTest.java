package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.request.SaveQuestionRequest;
import com.pixel.stupidbrain.entity.request.SaveTrueAnswerRequest;
import com.pixel.stupidbrain.entity.request.SaveUserRequest;
import com.pixel.stupidbrain.entity.response.QuestionResponse;
import com.pixel.stupidbrain.entity.response.TrueAnswerResponse;
import com.pixel.stupidbrain.entity.response.UserResponse;
import com.pixel.stupidbrain.exception.AnswersAlreadyExistsException;
import com.pixel.stupidbrain.exception.FieldIsEmptyException;
import com.pixel.stupidbrain.exception.TrueAnswerNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrueAnswerServiceTest {

    @Autowired
    private QuestionOperations questionOperations;

    @Autowired
    private UserOperations userOperations;

    @Autowired
    private TrueAnswerOperations trueAnswerOperations;

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
        String answer = "answer";
        String description = "description";
        UUID testQuestionId = testQuestion.getId();

        SaveTrueAnswerRequest answerRequest = new SaveTrueAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setDescription(description);
        answerRequest.setQuestion(testQuestionId);

        TrueAnswerResponse answerResponse = trueAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        assertNotNull(answerResponse);
        assertNotNull(id);

        assertEquals(answer, answerResponse.getAnswer());
        assertEquals(description, answerResponse.getDescription());
        assertEquals(testQuestionId, answerResponse.getQuestion());

        trueAnswerOperations.deleteById(id);
    }

    @Test
    void createWithoutAnswer(){
        String description = "description";
        UUID testQuestionId = testQuestion.getId();

        SaveTrueAnswerRequest answerRequest = new SaveTrueAnswerRequest();
        answerRequest.setAnswer(null);
        answerRequest.setDescription(description);
        answerRequest.setQuestion(testQuestionId);

        TrueAnswerResponse answerResponse = null;

        try {
            answerResponse = trueAnswerOperations.create(answerRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"Answer\" is empty", e.getReason());
        }

        assertNull(answerResponse);
    }

    @Test
    void createWithAlreadyExistAnswer() {
        String answer = "answer";
        String description = "description";
        UUID testQuestionId = testQuestion.getId();

        SaveTrueAnswerRequest answerRequest = new SaveTrueAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setDescription(description);
        answerRequest.setQuestion(testQuestionId);

        TrueAnswerResponse answerResponse = trueAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        TrueAnswerResponse newAnswerResponse = null;

        try {
            newAnswerResponse = trueAnswerOperations.create(answerRequest);
        } catch (AnswersAlreadyExistsException e) {
            assertEquals("Answer \"" + answer + "\" already exists", e.getReason());
        }

        assertNull(newAnswerResponse);

        trueAnswerOperations.deleteById(id);
    }

    @Test
    void createWithoutQuestion(){
        String answer = "answer";
        String description = "description";

        SaveTrueAnswerRequest answerRequest = new SaveTrueAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setDescription(description);

        TrueAnswerResponse answerResponse = null;

        try {
            answerResponse = trueAnswerOperations.create(answerRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"Question\" is empty", e.getReason());
        }

        assertNull(answerResponse);
    }

    @Test
    void getById() {
        String answer = "answer";
        String description = "description";
        UUID testQuestionId = testQuestion.getId();

        SaveTrueAnswerRequest answerRequest = new SaveTrueAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setDescription(description);
        answerRequest.setQuestion(testQuestionId);

        TrueAnswerResponse answerResponse = trueAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        TrueAnswerResponse response = trueAnswerOperations.getById(id);

        assertNotNull(response);
        assertNotNull(response.getId());

        assertEquals(answer, response.getAnswer());
        assertEquals(description, response.getDescription());
        assertEquals(testQuestionId, response.getQuestion());

        trueAnswerOperations.deleteById(id);
    }

    @Test
    void getByRandomId(){
        UUID randomUUID = UUID.randomUUID();

        TrueAnswerResponse response = null;

        try {
            response = trueAnswerOperations.getById(randomUUID);
        } catch (TrueAnswerNotFoundException e) {
            assertEquals("Answer with id " + randomUUID + " not found", e.getReason());
        }

        assertNull(response);
    }

    @Test
    void update() {
        String answer = "answer";
        String newAnswer = "new answer";
        String description = "description";
        String newDescription = "new description";
        UUID testQuestionId = testQuestion.getId();

        SaveTrueAnswerRequest answerRequest = new SaveTrueAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setDescription(description);
        answerRequest.setQuestion(testQuestionId);

        TrueAnswerResponse answerResponse = trueAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        answerRequest.setAnswer(newAnswer);
        answerRequest.setDescription(newDescription);

        trueAnswerOperations.update(id, answerRequest);

        TrueAnswerResponse response = trueAnswerOperations.getById(id);

        assertNotNull(response);
        assertNotNull(response.getId());

        assertEquals(newAnswer, response.getAnswer());
        assertEquals(newDescription, response.getDescription());

        trueAnswerOperations.deleteById(id);
    }

    @Test
    void updateWithoutAnswer(){
        String answer = "answer";
        String description = "description";
        String newDescription = "new description";
        UUID testQuestionId = testQuestion.getId();

        SaveTrueAnswerRequest answerRequest = new SaveTrueAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setDescription(description);
        answerRequest.setQuestion(testQuestionId);

        TrueAnswerResponse answerResponse = trueAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        answerRequest.setAnswer(null);
        answerRequest.setDescription(newDescription);

        try {
            trueAnswerOperations.update(id, answerRequest);
        } catch (FieldIsEmptyException e) {
            assertEquals("Field \"Answer\" is empty", e.getReason());
        }

        TrueAnswerResponse response = trueAnswerOperations.getById(id);

        assertEquals(answer, response.getAnswer());
        assertEquals(description, response.getDescription());

        trueAnswerOperations.deleteById(id);
    }


    @Test
    void deleteById() {
        String answer = "answer";
        String description = "description";
        UUID testQuestionId = testQuestion.getId();

        SaveTrueAnswerRequest answerRequest = new SaveTrueAnswerRequest();
        answerRequest.setAnswer(answer);
        answerRequest.setDescription(description);
        answerRequest.setQuestion(testQuestionId);

        TrueAnswerResponse answerResponse = trueAnswerOperations.create(answerRequest);

        UUID id = answerResponse.getId();

        trueAnswerOperations.deleteById(id);

        try {
            trueAnswerOperations.getById(id);
        } catch (TrueAnswerNotFoundException e) {
            assertEquals("Answer with id " + id + " not found", e.getReason());
        }
    }

    @Test
    void deleteByRandomId(){
        UUID randomUUID = UUID.randomUUID();

        try {
            trueAnswerOperations.deleteById(randomUUID);
        } catch (TrueAnswerNotFoundException e) {
            assertEquals("Answer with id " + randomUUID + " not found", e.getReason());
        }
    }
}