package com.pixel.stupidbrain.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "true_answers")
public class TrueAnswer {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String answer;

    private String description;

    @ManyToOne
    @JoinColumn(name = "question_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Question question;

    public TrueAnswer() {
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrueAnswer that = (TrueAnswer) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answer);
    }
}
