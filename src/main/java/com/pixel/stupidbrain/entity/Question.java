package com.pixel.stupidbrain.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<TrueAnswer> trueAnswers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<UsersAnswer> usersAnswers;

    public Question() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<TrueAnswer> getTrueAnswers() {
        return trueAnswers;
    }

    public void setTrueAnswers(Set<TrueAnswer> trueAnswers) {
        this.trueAnswers = trueAnswers;
    }

    public Set<UsersAnswer> getUsersAnswers() {
        return usersAnswers;
    }

    public void setUsersAnswers(Set<UsersAnswer> usersAnswers) {
        this.usersAnswers = usersAnswers;
    }
}
