package com.example.ChatDatabaseApp.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletResponse;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    //funcja dodajaca uzytkownika do bazy
    public boolean register(User user) throws DataIntegrityViolationException{
        try {
            //haszowanie hasla uzytkownika
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            int insertUser =  jdbcTemplate.update("INSERT INTO users(username, password, enabled) VALUES(?,?,?)", user.getUsername(), user.getPassword(), 1);
            int insertAuth = jdbcTemplate.update("INSERT INTO authorities(username, authority) VALUES(?,?)", user.getUsername(),"ROLE_USER");
        }catch (Exception e){
            return false;
        }
        return true;
    }
    //funkcja zmieniajaca status aktywnosci uzytkownika
    public boolean changeStatus(String username, boolean isOnline) {
        try{
                jdbcTemplate.update("UPDATE users SET is_online=? WHERE username=?", isOnline, username);
        }catch (Exception e){
        return false;
    }
        return true;
    }
}
