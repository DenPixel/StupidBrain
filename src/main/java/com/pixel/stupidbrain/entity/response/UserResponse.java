package com.pixel.stupidbrain.entity.response;

import com.pixel.stupidbrain.entity.Role;
import com.pixel.stupidbrain.entity.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserResponse {
    private UUID id;

    private String username;

    private String email;

    private Set<String> roles;

    static public UserResponse fromUser(User user){
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        userResponse.setRoles(roles);

        return userResponse;
    }

    static public List<UserResponse> fromUsers(Collection<User> users){
        return users.stream()
                .map(UserResponse::fromUser)
                .collect(Collectors.toList());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return username.equals(that.username) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }
}
