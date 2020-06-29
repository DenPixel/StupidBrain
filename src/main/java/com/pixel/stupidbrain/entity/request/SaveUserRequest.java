package com.pixel.stupidbrain.entity.request;

import java.util.Set;

public class SaveUserRequest {

    private String nickname;

    private String login;

    private String password;

    private String email;

    private Set<String> roles;

    public SaveUserRequest() {
    }


    public SaveUserRequest(String nickname, String login, String password, String email, Set<String> roles) {
        this.nickname = nickname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
