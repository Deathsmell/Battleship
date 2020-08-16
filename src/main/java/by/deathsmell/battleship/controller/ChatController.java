package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@Slf4j
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessageInPublicChat(@Payload ChatMessage chatMessage) {
        log.info("Send message");
        return chatMessage;
    }

    @MessageMapping("/room/{roomId}/chat.sendMessage")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendMessageInRoomChat(@Payload ChatMessage chatMessage,
                                             @DestinationVariable UUID roomId) {
        log.info("Send message between users in {} room. {}", roomId, chatMessage);

        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        String sender = chatMessage.getSender();
        if (null != sender) {
            log.info("Registration new user: {}.", sender);
        } else {
            log.error("Username empty");
        }
        return chatMessage;
    }

}
