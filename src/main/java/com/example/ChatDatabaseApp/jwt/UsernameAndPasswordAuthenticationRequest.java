package com.example.ChatDatabaseApp.jwt;

import lombok.Data;

@Data
public class UsernameAndPasswordAuthenticationRequest {
    //klasa odzwierciedlajaca dane uzytkownika potrzebne do autoryzacji
    private String username;
    private String password;

    public UsernameAndPasswordAuthenticationRequest() {
    }
}