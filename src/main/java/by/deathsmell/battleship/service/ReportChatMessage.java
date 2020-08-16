package by.deathsmell.battleship.service;

import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.dto.ReportType;
import by.deathsmell.battleship.exception.IllegalSenderAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportChatMessage implements ReportMessageCreator<ChatMessage>{

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public ReportChatMessage(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // QUESTION: this methods validated class type ?
    boolean supports(Class<? extends ReportType> clazz) {
        return true;
    }


    @Override
    public void sendTo(String sendTo, ChatMessage chatMessage) {
        messagingTemplate.convertAndSend(sendTo,chatMessage);
    }

    @Override
    public ChatMessage create(String text, String sender, ReportType type) {
        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        if (supports(type.getClass())){
            message.setType((ChatMessage.MessageType) type);
        } else {
            // QUESTION: reachable code ?
            String nonsupport_type_message = "Nonsupport type message";
            log.error(nonsupport_type_message);
            throw new IllegalSenderAction(nonsupport_type_message);
        }
        message.setContent(text);
        return message;
    }

    @Override
    public void createAndSendTo(String sendTo, String text, String sender, ReportType type) {
        ChatMessage message = create(text, sender, type);
        sendTo(sendTo,message);
    }

    @Override
    public ChatMessage empty() {
        return create("","", ChatMessage.MessageType.EMPTY);
    }
}
