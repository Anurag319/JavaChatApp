package com.chat.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//
//🧩 1. registerStompEndpoints() — Where Clients Connect
//@Override
//public void registerStompEndpoints(StompEndpointRegistry registry) {
//    registry.addEndpoint("chats")
//            .setAllowedOrigins("http://localhost:5173")
//            .withSockJS();
//}
//
//
//This method defines the WebSocket entry point for clients—essentially the URL where a browser or frontend app initiates the WebSocket/STOMP handshake.
//
//        🔍 Breakdown
//✔ registry.addEndpoint("chats")
//
//This exposes a WebSocket endpoint at:
//
//ws://localhost:8080/chats
//
//
//Your React/Vite frontend at port 5173 will connect to this URL.
//
//Example client code:
//
//        const socket = new SockJS("http://localhost:8080/chats");
//const stompClient = Stomp.over(socket);
//
//✔ setAllowedOrigins("http://localhost:5173")
//
//Allows your frontend to make WebSocket requests.
//
//If you don’t set this, the browser will block cross-origin WebSocket traffic.
//
//✔ withSockJS()
//
//This enables SockJS, a fallback technology.
//
//If a browser doesn’t support WebSockets or they’re blocked by a proxy, SockJS will fall back to:
//
//XHR polling
//
//Long polling
//
//Streaming
//
//This ensures maximum compatibility.
//
//        📌 Diagram: registerStompEndpoints() Flow
//┌─────────────────────────────────────────┐
//        │            Frontend (React/Vite)        │
//        │  http://localhost:5173                  │
//        └─────────────────────────────────────────┘
//        |
//        | 1. Connect using SockJS/STOMP
//        v
//ws://localhost:8080/chats
//        (WebSocket/STOMP endpoint exposed by Spring)
//        |
//v
//┌─────────────────────────────────────────┐
//        │  Spring Boot WebSocket Config           │
//        │  registerStompEndpoints()               │
//        │  - Exposes "chats" endpoint             │
//        │  - Allows CORS from :5173               │
//        │  - Enables SockJS fallback              │
//        └─────────────────────────────────────────┘
//
//        🧩 2. configureMessageBroker() — How Messages Move Inside the System
//@Override
//public void configureMessageBroker(MessageBrokerRegistry registry) {
//    registry.enableSimpleBroker("/topic");
//    registry.setApplicationDestinationPrefixes("/app");
//}
//
//
//This method configures routing rules for STOMP messages — where messages go and how Spring processes them.
//
//        ✔ registry.enableSimpleBroker("/topic")
//
//This enables an in-memory message broker that handles broadcasting messages to all subscribed clients.
//
//Clients subscribe to:
//
//        /topic/messages
///topic/chatroom
///topic/notifications
//
//
//Spring will deliver messages sent to any /topic/... destination to all subscribers.
//
//        ✔ registry.setApplicationDestinationPrefixes("/app")
//
//This defines the prefix for messages that should be handled by your server-side controller.
//
//Example:
//
//Frontend sends:
//
//SEND /app/sendmessage
//
//
//Spring maps it to your controller:
//
//@MessageMapping("/sendmessage")
//public void handle(ChatMessage msg) { ... }
//
//
//This is similar to REST:
//
//POST /api/message
//
//
//Except it’s over WebSockets.
//
//🔥 Full Messaging Flow With Both Methods Together
//Client → Server (SEND)
//
//Starts with /app → goes to your @MessageMapping method.
//
//Server → Clients (BROADCAST)
//
//Uses /topic → message broker will deliver to all subscribers.
//
//📌 Diagram: Message Routing in configureMessageBroker()
//CLIENT SIDE
//┌─────────────────────────────────────────┐
//        │ User sends message                      │
//        │ SEND /app/sendmessage                   │
//        └─────────────────────────────────────────┘
//        |
//v
//    /app → Sent to @MessageMapping
//                 |
//v
//Spring Controller handles message
//                 |
//v
//Message forwarded to:
//        /topic/messages
//        (Simple Broker broadcasts to all)
//                 |
//v
//┌─────────────────────────────────────────┐
//        │ All subscribed clients receive message  │
//        │ SUBSCRIBE /topic/messages               │
//        └─────────────────────────────────────────┘
//
//        🧨 Putting It All Together: End-to-End Diagram
//        ┌─────────────────────────────┐
//                │   Frontend (React/Vite)     │
//        │   Port 5173                 │
//        └──────────────┬──────────────┘
//        |
//        1. Connect              v
//ws://localhost:8080/chats  ← registerStompEndpoints()
//        |
//v
//        ┌─────────────────────────────┐
//                │ Spring WebSocket Endpoint   │
//        └──────────────┬──────────────┘
//        |
//        2. SEND message         v
//   /app/sendmessage  → Controller method (@MessageMapping)
//                       |
//v
//3. BROADCAST message
//   /topic/messages   ← enableSimpleBroker("/topic")
//                       |
//v
//4. All SUBSCRIBERS receive update
//SUBSCRIBE /topic/messages

@Configuration // Spring Boot simplifies configuration through a principle called "Convention over Configuration," primarily using auto-configuration and externalized properties. This minimizes explicit configuration, allowing developers to focus on business logic
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("chats")
                .setAllowedOrigins("http://localhost:8080")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // set message broker
        registry.enableSimpleBroker("/topic");

        // expect message with /app/sendmessage
        registry.setApplicationDestinationPrefixes("/app");
    }
}
