package com.chat.app.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // The @NoArgsConstructor annotation is a Project Lombok feature used in Spring Boot to automatically generate a constructor with no arguments for a class at compile time.
public class ChatMessage {
    private Long id;
    private String sender;
    private String content;
}
