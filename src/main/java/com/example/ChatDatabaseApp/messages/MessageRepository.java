package com.example.ChatDatabaseApp.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<Message> getMessages(String username, String username2) {
        return jdbcTemplate.query("SELECT id, sender, receiver, post_date, content from messages WHERE " +
                "(sender LIKE ? AND receiver LIKE ?) OR (receiver LIKE ? AND sender LIKE ?) ORDER BY post_date ASC", BeanPropertyRowMapper.newInstance(Message.class), username, username2, username, username2);
    }

    public boolean sendMessage(Message message) {
        try {
            int insertUser =  jdbcTemplate.update("INSERT INTO messages(sender, receiver, content) VALUES(?,?,?)", message.getSender(), message.getReceiver(), message.getContent());
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public List<String> getFriends(String username) {
        return  jdbcTemplate.queryForList("SELECT DISTINCT receiver from messages where sender=?" +
                "UNION select distinct sender from messages where receiver=?", String.class, username, username);
    }
}
