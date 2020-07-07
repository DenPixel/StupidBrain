package com.pixel.stupidbrain.entity.response;

import com.pixel.stupidbrain.entity.UsersAnswer;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UsersAnswerResponse {
    private UUID id;

    private String answer;

    private boolean correctAnswer;

    private UUID question;

    private UUID user;

    static public UsersAnswerResponse fromUsersAnswer(UsersAnswer usersAnswer){
        UsersAnswerResponse usersAnswerResponse = new UsersAnswerResponse();

        usersAnswerResponse.setId(usersAnswer.getId());
        usersAnswerResponse.setAnswer(usersAnswer.getAnswer());
        usersAnswerResponse.setCorrectAnswer(usersAnswer.isCorrectAnswer());
        usersAnswerResponse.setQuestion(usersAnswer.getQuestion().getId());
        usersAnswerResponse.setUser(usersAnswer.getUser().getId());

        return usersAnswerResponse;
    }

    static public List<UsersAnswerResponse> fromUsersAnswers(Collection<UsersAnswer> usersAnswers){
        return usersAnswers.stream()
                .map(UsersAnswerResponse::fromUsersAnswer)
                .collect(Collectors.toList());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public UUID getQuestion() {
        return question;
    }

    public void setQuestion(UUID question) {
        this.question = question;
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }
}
