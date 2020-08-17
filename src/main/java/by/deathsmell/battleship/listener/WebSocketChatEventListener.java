package by.deathsmell.battleship.listener;

import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.service.ReportChatMessage;
import by.deathsmell.battleship.service.RoomCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

import java.security.Principal;
import java.util.UUID;

import static by.deathsmell.battleship.dto.ChatMessage.MessageType;

@Component
@Slf4j
public class WebSocketChatEventListener implements WebSocketEventListener {


    private final SimpMessageSendingOperations messagingTemplate;
    private final RoomCreator roomCreator;
    private final ReportChatMessage chatMessage;

    @Autowired
    public WebSocketChatEventListener(SimpMessageSendingOperations messagingTemplate, RoomCreator roomCreator, ReportChatMessage chatMessage) {
        this.messagingTemplate = messagingTemplate;
        this.roomCreator = roomCreator;
        this.chatMessage = chatMessage;
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
        String username = getUsername(event);
        log.debug(username + "started unsubscribe event");
        roomCreator.roomDestroy(username);

    }

    @Override
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String username = getUsername(event);
        log.debug("Started disconnect event for " + username);
        try {
            roomCreator.roomDestroy(username);
        } catch (IllegalArgumentException e){
            log.warn("Room not found. May be error or room not exist");
        }
        log.info("Disconnect user :{}", username);

        // TODO: 8/14/20 Create report to user or room where he was
        createReport(username, MessageType.DISCONNECT);
    }

    private String getUsername(AbstractSubProtocolEvent event){
        Principal user = event.getUser();
        if (user != null) {
            return user.getName();
        } else {
            log.error("Illegal principal state. User doesnt exist or have not name");
            throw new RuntimeException("Illegal principal state. User doesnt exist or have not name");
        }
    }

    // FIXME: 8/14/20 Remove this function and replace on common ReportChatMessage method
    private void createReport(String username, MessageType type) {
        createReport(username, type, null);
    }

    // FIXME: 8/14/20 Remove this function and replace on common ReportChatMessage method
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