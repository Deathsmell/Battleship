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

import java.time.LocalDateTime;
import java.util.List;
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

    @GetMapping("/create")
    public Room createRoom() {
        //FIXME: Relocate in service class
        UUID roomID = UUID.randomUUID();
        Room newRoom = new Room();
        newRoom.setRoom(roomID);
        newRoom.setRoomStatus(RoomStatus.CREATE);
        newRoom.setCreateTime(LocalDateTime.now());
        roomRepo.save(newRoom);
        log.info("Create new room. Room {}", newRoom);
        return newRoom;
    }

    @GetMapping("/list")
    public List<Room> getAllWaitRooms() {
        return roomRepo.findAllByHostNotNullOrOpponentNotNull();
    }

    @GetMapping("/allRooms")
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    // FIXME: Name method dont right. Create get method
    @PutMapping
    public Room updateRoom(@RequestBody Room room) {
        Room roomFromDb = roomRepo.findByRoom(room.getRoom());
        log.debug("Update room : {}", roomFromDb);
        return roomFromDb;
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

    @MessageMapping("/room/{roomId}/join")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage joinToRoom(@Payload ChatMessage message,
                                  @DestinationVariable UUID roomId,
                                  SimpMessageHeaderAccessor accessor)
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
                    RoomStatus roomStatus = roomFromDb.getRoomStatus();
                    if (roomStatus == RoomStatus.CREATE) {
                        joinFirstPlayer(roomId, sender, roomFromDb, roomStatus);
                        accessor.getSessionAttributes().put("roomId", roomId);
                    } else if (roomStatus == RoomStatus.WAIT) {
                        joinSecondPlayer(roomId, sender, roomFromDb, roomStatus);
                        accessor.getSessionAttributes().put("roomId", roomId);
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

    //FIXME: Relocate in service class
    private ChatMessage successReport(ChatMessage message, UUID roomId, String sender) {
        log.info("User '{}' join in room. Room id: {}", sender, roomId);
        ChatMessage successMessage = new ChatMessage();
        successMessage.setSender("Server");
        successMessage.setType(MessageType.JOIN);
        successMessage.setContent("Success join. Just enjoy");
        return successMessage;
    }

    //FIXME: Relocate in service class
    private ChatMessage reportRoomFieldOrDestroyed() {
        log.debug("Room filed or destroyed");
        ChatMessage errorResponseMessage = new ChatMessage();
        errorResponseMessage.setSender("Server");
        errorResponseMessage.setType(MessageType.CHAT);
        errorResponseMessage.setContent("Room filed or destroyed");
        return errorResponseMessage;
    }

    //FIXME: Relocate in service class
    private void joinFirstPlayer(UUID roomId, String sender, Room roomFromDb, RoomStatus roomStatus) throws IncorrectStatusOfTheCreatedRoomException {
        if (null == roomFromDb.getHost() && null == roomFromDb.getOpponent()) {
            roomFromDb.setHost(sender);
            roomFromDb.setRoomStatus(RoomStatus.WAIT);
            log.info("Add host players in new room. Player name: {}, room id: {}", sender, roomId);
            roomRepo.save(roomFromDb);
        } else {
            log.error("Incorrect status of the create room." +
                            " One or two place in room taken. Status: {}, room id: {}",
                    roomStatus, roomId);
            throw new IncorrectStatusOfTheCreatedRoomException();
        }
    }

    //FIXME: Relocate in service class
    private void joinSecondPlayer(UUID roomId, String sender, Room roomFromDb, RoomStatus roomStatus) throws IncorrectStatusOfTheCreatedRoomException {
        if (!sender.equals(roomFromDb.getHost()) && null == roomFromDb.getOpponent()) {
            roomFromDb.setOpponent(sender);
            roomFromDb.setRoomStatus(RoomStatus.FILED);
            roomRepo.save(roomFromDb);
        } else {
            log.error("Incorrect status of the create room." +
                            " Sender try to rejoin second time or room filed. Status: {}, room id: {}.",
                    roomStatus, roomId);
            throw new IncorrectStatusOfTheCreatedRoomException();
        }
    }


}
