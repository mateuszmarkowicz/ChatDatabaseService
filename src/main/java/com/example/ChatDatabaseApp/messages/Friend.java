package com.example.ChatDatabaseApp.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    private String friend;
    private int is_online;
    private int is_all_read;
}
