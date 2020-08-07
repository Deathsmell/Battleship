package by.deathsmell.battleship.configuration;

import by.deathsmell.battleship.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketChatEventListener {


    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketChatEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println(accessor.toString());
        log.info("Received a new web socket connection. Session id: {}", accessor.getSessionId());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = null;
        String roomID = null;
        try {
            username = (String) headerAccessor.getSessionAttributes().get("sender");
            roomID = (String) headerAccessor.getSessionAttributes().get("roomId");
        } catch (NullPointerException e) {
            log.error("Unsupported user");
        }

        log.info("Disconnect user :{} in room {} ", username, roomID);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(ChatMessage.MessageType.LEAVE);
        chatMessage.setSender(username);

        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
}