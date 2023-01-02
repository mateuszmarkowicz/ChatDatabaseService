package com.example.ChatDatabaseApp.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    int id;
    private String sender;
    private String receiver;
    private String post_date;
    private String content;

}
