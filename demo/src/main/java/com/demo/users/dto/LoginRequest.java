package com.demo.users.dto;



import lombok.Data;

@Data
public class LoginRequest {

    private String user;
    private String password;
    public LoginRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }

    
}

