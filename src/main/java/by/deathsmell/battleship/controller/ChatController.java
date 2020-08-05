package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        log.info("Send message");
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        String sender = chatMessage.getSender();
        if (null != sender) {
            try {
                headerAccessor.getSessionAttributes().put("sender", sender);
            } catch (NullPointerException e){
                log.error("Session dont exist!");
            }
            log.info("Registration new user: " + sender);
        } else {
            log.error("Username empty");
        }
        return chatMessage;
    }
}
