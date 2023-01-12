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
    @PostMapping("")
    public boolean register(@RequestBody User user, HttpServletResponse response){
        boolean isRegisterDone = userRepository.register(user);
        if(isRegisterDone){
            return true;
        }
        else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return false;
        }
    }
    @PatchMapping("/{isOnline}")
    public void changeStatus(@PathVariable boolean isOnline){
    Object user = SecurityContextHolder.getContext().getAuthentication();
    userRepository.changeStatus(user.toString(), isOnline);
    }
}
