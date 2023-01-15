package com.example.ChatDatabaseApp.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //klasa odzwierciedlajaca podstatwowe dane uzytkownika(rejestracja)
    private String username;
    private String password;
    private int enabled;
}