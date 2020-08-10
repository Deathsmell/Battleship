package by.deathsmell.battleship.service;

import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.dto.ReportType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReportChatMessage implements ReportMessageCreator<ChatMessage>{

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public ReportChatMessage(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public void sendTo(String sendTo, ChatMessage chatMessage) {
        messagingTemplate.convertAndSend(sendTo,chatMessage);
    }

    @Override
    public ChatMessage create(String text, String sender, ReportType type) {
        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setType((ChatMessage.MessageType) type);
        message.setContent(text);
        return message;
    }

    @Override
    public void createAndSendTo(String sendTo, String text, String sender, ReportType type) {
        ChatMessage message = create(text, sender, type);
        sendTo(sendTo,message);
    }
}
