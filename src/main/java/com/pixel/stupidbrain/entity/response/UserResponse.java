package com.pixel.stupidbrain.entity.response;

import com.pixel.stupidbrain.entity.Role;
import com.pixel.stupidbrain.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserResponse {
    private String nickname;

    private String login;

    private String email;

    private Set<String> roles;

    public UserResponse() {
    }

    public UserResponse(String nickname, String login, String email, Set<String> roles) {
        this.nickname = nickname;
        this.login = login;
        this.email = email;
        this.roles = roles;
    }

    static public UserResponse fromUser(User user){
        UserResponse userResponse = new UserResponse();

        userResponse.setNickname(user.getNickname());
        userResponse.setEmail(user.getEmail());
        userResponse.setLogin(user.getLogin());
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        userResponse.setRoles(roles);

        return userResponse;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
