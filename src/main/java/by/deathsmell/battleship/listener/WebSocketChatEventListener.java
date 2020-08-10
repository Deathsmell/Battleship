package by.deathsmell.battleship.listener;

import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.exception.IllegalRoomStateException;
import by.deathsmell.battleship.exception.IllegalSenderAction;
import by.deathsmell.battleship.repositories.RoomRepository;
import by.deathsmell.battleship.service.RoomCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

import java.util.Map;
import java.util.UUID;

import static by.deathsmell.battleship.domain.Room.RoomStatus.DESTROY;
import static by.deathsmell.battleship.dto.ChatMessage.MessageType;

@Component
@Slf4j
public class WebSocketChatEventListener implements WebSocketEventListener {


    private final SimpMessageSendingOperations messagingTemplate;
    private final RoomCreator roomCreator;

    @Autowired
    public WebSocketChatEventListener(SimpMessageSendingOperations messagingTemplate, RoomCreator roomCreator) {
        this.messagingTemplate = messagingTemplate;
        this.roomCreator = roomCreator;
    }

    @Override
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("Received a new web socket connection. Session id: {}", accessor.getSessionId());
    }

    @Override
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    }

    @Override
    public void handleWebSocketUnsubscribeListener(SessionUnsubscribeEvent event) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        roomCreator.roomDestroy(event, null, null, null, true);

    }

    @Override
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes == null || sessionAttributes.isEmpty()) {
            log.error("Illegal session state. Session id: {}", headerAccessor.getSessionId());
            throw new RuntimeException();
        }
        String username = (String) sessionAttributes.get("sender");
        UUID roomID = (UUID) sessionAttributes.get("roomId");
        String sessionId = headerAccessor.getSessionId();

        if (roomID != null) {
            roomCreator.roomDestroy(event, username, null, null, true);
        }
        log.info("Disconnect user :{}, session id: {}. Room id : {}", username, sessionId, roomID);

        createReport(username, MessageType.DISCONNECT);
    }

    // TODO: create common report class
    private void createReport(String username, MessageType type) {
        createReport(username, type, null);
    }

    private void createReport(String username, MessageType type, UUID room) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender("Server");
        chatMessage.setType(type);
        String text = "Just message";
        if (type.equals(MessageType.LEAVE)) {
            text = String.format("Player %s leave room", username);
        }
        if (type.equals(MessageType.DISCONNECT)) {
            text = String.format("%s exit own game. Good lack!", username);
        }
        chatMessage.setContent(text);
        String address = "/topic/public";
        if (room != null) {
            address = String.format("/topic/room/%s/chat.sendMessage", room.toString());
        }
        messagingTemplate.convertAndSend(address, chatMessage);
    }
}