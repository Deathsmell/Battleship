package by.deathsmell.battleship.listener;

import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.exception.IllegalRoomStateException;
import by.deathsmell.battleship.exception.IllegalSenderAction;
import by.deathsmell.battleship.repositories.RoomRepository;
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
public class WebSocketChatEventListener {


    private final SimpMessageSendingOperations messagingTemplate;
    private final RoomRepository roomRepo;

    @Autowired
    public WebSocketChatEventListener(SimpMessageSendingOperations messagingTemplate, RoomRepository roomRepo) {
        this.messagingTemplate = messagingTemplate;
        this.roomRepo = roomRepo;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("Received a new web socket connection. Session id: {}", accessor.getSessionId());
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    }

    @EventListener
    public void handleWebSocketUnsubscribeListener(SessionUnsubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        roomDestroy(event);

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes == null || sessionAttributes.isEmpty()){
            log.error("Illegal session state. Session id: {}",headerAccessor.getSessionId());
            throw new RuntimeException();
        }
        String username = (String) sessionAttributes.get("sender");
        UUID roomID = (UUID) sessionAttributes.get("roomId");
        String sessionId = headerAccessor.getSessionId();

        if (roomID != null) {
            roomDestroy(event, username);
        }
        log.info("Disconnect user :{}, session id: {}. Room id : {}", username, sessionId, roomID);

        createReport(username, MessageType.DISCONNECT);
    }

    private void roomDestroy(final AbstractSubProtocolEvent event) {
        roomDestroy(event, null);
    }

    private void roomDestroy(final AbstractSubProtocolEvent event, String username) {


        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        UUID roomId = (UUID) accessor.getSessionAttributes().get("roomId");
        log.debug("Starting room destroy event. Input values: room id - {} , username - {}",
                roomId,
                username);
        if (roomId != null) {
            Room room = roomRepo.findByRoom(roomId);
            log.debug("Move in db and get {}", room);
            if (room != null) {

                username = username == null ?
                        (String) accessor.getSessionAttributes().get("sender")
                        : username;

                if (!username.isEmpty()) {
                    boolean hasPlayer1 = username.equals(room.getHost());
                    boolean hasPlayer2 = username.equals(room.getOpponent());
                    if (hasPlayer1 && hasPlayer2) {
                        log.error("Sender listed in two room slots. Illegal room state. Sender : {}, {}", username, roomId);
                        throw new IllegalRoomStateException();
                    }
                    if (hasPlayer1) {
                        room.setHost(null);
                        accessor.getSessionAttributes().remove("roomId");
                    } else {
                        if (hasPlayer2) {
                            room.setOpponent(null);
                            accessor.getSessionAttributes().remove("roomId");
                        } else {
                            log.error("Player not belongs that room. Sender : {} expect {} or {} , room id: {}",
                                    username,
                                    room.getHost(),
                                    room.getOpponent(),
                                    roomId);
                            throw new IllegalSenderAction();
                        }
                    }
                    if (room.getHost() == null && room.getOpponent() == null && room.getRoomStatus().equals(DESTROY)) {
                        log.info("Room is empty. Deleting: {}", room);
                        roomRepo.delete(room);
                    } else {
                        log.debug("Room have give status DESTROY");
                        room.setRoomStatus(DESTROY);
                        roomRepo.save(room);
                        createReport(username, MessageType.LEAVE, room.getRoom());
                    }
                }

            }
        }
    }

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