package com.chat.app.controller;


import com.chat.app.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage")
//    The @MessageMapping annotation in Spring Boot is used to route messages based on their destination in applications that use messaging protocols like STOMP over WebSockets. It maps incoming messages to specific handler methods within a controller.

//    Key Concepts and Usage
//    Protocol: @MessageMapping is part of Spring's support for message handling, typically used with the STOMP (Simple Text Oriented Messaging Protocol) sub-protocol over WebSockets to enable real-time, bidirectional communication.
//    Controller: It is used within a class annotated with @Controller (not typically @RestController, as it handles messages rather than traditional HTTP REST requests).
//    Destination Mapping: The value in the annotation defines the destination (e.g., /hello) for which the method should be invoked. In the configuration, you typically set an application destination prefix (e.g., /app), so a client sending a message to /app/hello would trigger a method with @MessageMapping("/hello").
//    Method Arguments: @MessageMapping methods are flexible and support various argument types for accessing message details:
//    @Payload to extract the message body (and automatically convert it to a Java object). This is assumed by default for unannotated arguments.
//    @Header or @Headers to access specific message headers or all headers.
//    @DestinationVariable to access template variables from the destination path (e.g., /user/{userId}).
//    Principal to access the authenticated user information in a secured session.
//    Return Value and @SendTo:
//    By default, the return value from the method is wrapped as a new message and sent to a default broker destination (usually the incoming destination with a /topic prefix).
//    You can use the @SendTo annotation on the method (or class) to specify a custom public destination(s) for the return message.
//    Use @SendToUser to direct the response to only the specific user associated with the input message.

    @SendTo("/topic/messages") // It is used to broadcast message to all the subscribers
    public ChatMessage sendMessage(ChatMessage message){
        return message;
    }

    @GetMapping("chat")
    public String chat(){
        return "chat";
    }
}
