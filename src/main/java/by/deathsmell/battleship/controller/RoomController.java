package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.dto.ChatMessage.MessageType;
import by.deathsmell.battleship.exception.EmptySenderNameSpaceException;
import by.deathsmell.battleship.exception.IncorrectStatusMessageExeption;
import by.deathsmell.battleship.exception.IncorrectStatusOfTheCreatedRoomException;
import by.deathsmell.battleship.repositories.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static by.deathsmell.battleship.domain.Room.*;

@RestController
@RequestMapping("/room")
@Slf4j
public class RoomController {

    private final RoomRepository roomRepo;
    private final SimpMessagingTemplate template;

    @Autowired
    public RoomController(RoomRepository roomRepo, SimpMessagingTemplate template) {
        this.roomRepo = roomRepo;
        this.template = template;
    }

    @PostMapping("/create")
    public Room createRoom() {
        UUID roomID = UUID.randomUUID();
        Room newRoom = new Room();
        newRoom.setRoom(roomID);
        newRoom.setState(State.CREATE);
        roomRepo.save(newRoom);
        log.info("Create new room. Status : {}, id : {}", newRoom.getState(), roomID);
        return newRoom;
    }

    @MessageMapping("/room/{roomId}/join")
    @SendTo("/topic/room/{roomId}")
    // FIXME: dont send response
    public ChatMessage joinToRoom(@Payload ChatMessage message,
                                  @DestinationVariable UUID roomId)
            throws IncorrectStatusOfTheCreatedRoomException,
            IncorrectStatusMessageExeption,
            EmptySenderNameSpaceException {
        log.debug("Start joining into room");
        String sender = message.getSender();
        if (null != sender) {
            if (message.getType() == MessageType.JOIN) {
                if (null != roomId) {
                    Room roomFromDb = roomRepo.findByRoom(roomId);
                    log.debug("Find room in DB. Room: {}", roomFromDb);
                    State state = roomFromDb.getState();
                    if (state == State.CREATE) {
                        joinFirstPlayer(roomId, sender, roomFromDb, state);
                    } else if (state == State.WAIT) {
                        joinSecondPlayer(roomId, sender, roomFromDb, state);
                    } else {
                        return reportRoomFieldOrDestroyed();
                    }
                    return successReport(message, roomId, sender);
                }
            }
            log.error("Incorrect status represent message. Message status: {}", message.getType());
            throw new IncorrectStatusMessageExeption();
        }
        log.error("User name empty");
        throw new EmptySenderNameSpaceException();

    }

    private ChatMessage successReport(ChatMessage message, UUID roomId, String sender) {
        log.info("User '{}' join in room. Room id: {}", sender, roomId);
        ChatMessage successMessage = new ChatMessage();
        successMessage.setType(MessageType.JOIN);
        successMessage.setContent("Success join. Just enjoy");
        successMessage.setSender("Server");
        template.convertAndSend("/room/"+roomId+"/chat.sendMessage",successMessage);
        return successMessage;
    }

    private ChatMessage reportRoomFieldOrDestroyed() {
        log.debug("Room filed or destroyed");
        ChatMessage errorResponseMessage = new ChatMessage();
        errorResponseMessage.setSender("Server");
        errorResponseMessage.setContent("Room filed or destroyed");
        errorResponseMessage.setType(MessageType.CHAT);
        return errorResponseMessage;
    }

    private void joinFirstPlayer(UUID roomId, String sender, Room roomFromDb, State state) throws IncorrectStatusOfTheCreatedRoomException {
        if (null == roomFromDb.getPlayer1() && null == roomFromDb.getPlayer2()) {
            roomFromDb.setPlayer1(sender);
            roomFromDb.setState(State.WAIT);
            log.info("Add host players in new room. Player name: {}, room id: {}", sender, roomId);
        } else {
            log.error("Incorrect status of the create room." +
                            " One or two place in room taken. Status: {}, room id: {}",
                    state, roomId);
            throw new IncorrectStatusOfTheCreatedRoomException();
        }
    }

    private void joinSecondPlayer(UUID roomId, String sender, Room roomFromDb, State state) throws IncorrectStatusOfTheCreatedRoomException {
        if (!sender.equals(roomFromDb.getPlayer1()) && null == roomFromDb.getPlayer2()) {
            roomFromDb.setPlayer2(sender);
            roomFromDb.setState(State.FILED);
        } else {
            log.error("Incorrect status of the create room." +
                            " Sender try to rejoin second time or room filed. Status: {}, room id: {}.",
                    state, roomId);
            throw new IncorrectStatusOfTheCreatedRoomException();
        }
    }


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        String sender = chatMessage.getSender();
        if (null != sender) {
            headerAccessor.getSessionAttributes().put("sender", sender);
            log.info("Registration new user: {}.", sender);
        } else {
            log.error("Username empty");
        }
        return chatMessage;
    }
}
