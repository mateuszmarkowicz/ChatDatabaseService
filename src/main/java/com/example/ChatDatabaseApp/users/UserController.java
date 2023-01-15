package com.example.ChatDatabaseApp.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    //endpoint umozliwiajacy rejestracje nowego uzytkownika
    @PostMapping("")
    public boolean register(@RequestBody User user, HttpServletResponse response){
        boolean isRegisterDone = userRepository.register(user);
        if(isRegisterDone){
            return true;
        }
        else {
            //jesli cos poszlo nie tak ustaw status 409
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return false;
        }
    }
    //endpoint sluzocy do zmiany statusu aktywnosci uzytkownika(online/offline)
    @PatchMapping("/{isOnline}")
    public boolean changeStatus(@PathVariable boolean isOnline){
    //pobranie nazwy uzytkownika z tokenu
    Object user = SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
    return userRepository.changeStatus(user.toString(), isOnline);
    }
}
