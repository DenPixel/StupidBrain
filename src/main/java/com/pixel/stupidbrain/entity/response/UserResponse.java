package com.pixel.stupidbrain.entity.response;

import com.pixel.stupidbrain.entity.Role;
import com.pixel.stupidbrain.entity.User;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserResponse {

    private UUID id;

    private String nickname;

    private String email;

    private Set<String> roles;

    public UserResponse() {
    }

    public UserResponse(UUID id,
                        String nickname,
                        String email,
                        Set<String> roles) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.roles = roles;
    }

    static public UserResponse fromUser(User user){
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setNickname(user.getNickname());
        userResponse.setEmail(user.getEmail());
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        userResponse.setRoles(roles);

        return userResponse;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
}
