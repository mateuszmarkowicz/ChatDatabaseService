package com.example.ChatDatabaseApp.messages;

import com.example.ChatDatabaseApp.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Receiver;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;
    @GetMapping("")
    public List<Message> getMessages(@RequestParam String username){
        Object user1 = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username2 = user1.toString();
        return messageRepository.getMessages(username, username2);
    }
    @PostMapping("")
    public boolean sendMessage(@RequestBody Message message){
        boolean isMessageSend = messageRepository.sendMessage(message);
        if(isMessageSend){
            return true;
        }
        else {
            return false;
        }
    }
    @GetMapping("/friends")
    public List<Friend> getFriends(){
        Object user = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = user.toString();
        return messageRepository.getFriends(username);
    }

    @PatchMapping("/{senderUsername}")
    public boolean changeStatus(@PathVariable String senderUsername){
        Object user = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String receiverUsername = user.toString();
        return messageRepository.changeStatus(receiverUsername, senderUsername);
    }
}