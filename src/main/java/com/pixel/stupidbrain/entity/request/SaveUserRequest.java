package com.pixel.stupidbrain.entity.request;

import java.util.HashSet;
import java.util.Set;

public class SaveUserRequest {

    private String nickname;

    private String password;

    private String rePassword;

    private String email;

    private Set<String> roles = new HashSet<>();

    public SaveUserRequest() {
    }


    public SaveUserRequest(String nickname,
                           String login,
                           String password,
                           String rePassword,
                           String email,
                           Set<String> roles) {
        this.nickname = nickname;
        this.password = password;
        this.rePassword = rePassword;
        this.email = email;
        this.roles = roles;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public void addRole(String role){
        roles.add(role);
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
