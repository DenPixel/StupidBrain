package com.pixel.stupidbrain.entity.response;

import com.pixel.stupidbrain.entity.TrueAnswer;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TrueAnswerResponse {
    private UUID id;

    private String answer;

    private String description;

    private UUID question;

    static public TrueAnswerResponse fromTrueAnswer(TrueAnswer trueAnswer){
        TrueAnswerResponse trueAnswerResponse = new TrueAnswerResponse();

        trueAnswerResponse.setId(trueAnswer.getId());
        trueAnswerResponse.setAnswer(trueAnswer.getAnswer());
        trueAnswerResponse.setDescription(trueAnswer.getDescription());
        trueAnswerResponse.setQuestion(trueAnswer.getQuestion().getId());

        return trueAnswerResponse;
    }

    static public List<TrueAnswerResponse> fromTrueAnswers(Collection<TrueAnswer> trueAnswers){
        return trueAnswers.stream()
                .map(TrueAnswerResponse::fromTrueAnswer)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getQuestion() {
        return question;
    }

    public void setQuestion(UUID question) {
        this.question = question;
    }
}
