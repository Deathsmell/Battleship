package by.deathsmell.battleship.service;

import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.domain.Room.RoomStatus;
import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.exception.*;
import by.deathsmell.battleship.repositories.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static by.deathsmell.battleship.domain.Room.RoomStatus.*;
import static by.deathsmell.battleship.dto.ChatMessage.MessageType.*;

@Slf4j
@Component
public class RoomService implements RoomCreator {

    private final RoomRepository roomRepo;
    private final ReportMessageCreator<ChatMessage> messageCreator;

    @Autowired
    public RoomService(RoomRepository roomRepo, ReportMessageCreator<ChatMessage> messageCreator) {
        this.roomRepo = roomRepo;
        this.messageCreator = messageCreator;
    }

    @Override
    public Room createRoom() {
        UUID roomID = UUID.randomUUID();
        Room newRoom = new Room();
        newRoom.setRoom(roomID);
        newRoom.setRoomStatus(CREATE);
        newRoom.setCreateTime(LocalDateTime.now());
        roomRepo.save(newRoom);
        log.info("Create new room. RoomCreate Room {}", newRoom);
        return newRoom;
    }

    @Override
    public ChatMessage joinToRoom(ChatMessage chatMessage, UUID roomId, SimpMessageHeaderAccessor headerAccessor) throws IncorrectStatusMessageExeption, EmptySenderNameSpaceException {
        log.debug("Start joining into room");
        String sender = chatMessage.getSender();
        if (null != sender) {
            if (chatMessage.getType() == JOIN) {
                if (null != roomId) {
                    Room roomFromDb = roomRepo.findByRoom(roomId);
                    log.debug("Find room in DB. Room: {}", roomFromDb);
                    RoomStatus roomStatus = roomFromDb.getRoomStatus();
                    if (roomStatus == CREATE) {
                        try {
                            joinFirstPlayer(roomId, sender, roomFromDb);
                        } catch (IncorrectStatusOfTheCreatedRoomException e) {
                            // TODO: create report to User
                            log.error("Incorrect status of the create room." +
                                            " One or two place in room taken. Status: {}, room id: {}",
                                    roomStatus, roomId);
                            e.printStackTrace();
                        }
                        // TODO: create another method saving room ID
                        headerAccessor.getSessionAttributes().put("roomId", roomId);
                    } else if (roomStatus == WAIT) {
                        try {
                            joinSecondPlayer( sender, roomFromDb);
                        } catch (IncorrectStatusOfTheCreatedRoomException e) {
                            // TODO: create report to User
                            log.error("Incorrect status of the create room." +
                                            " Sender try to rejoin second time or room filed. Status: {}, room id: {}.",
                                    roomStatus, roomId);
                            e.printStackTrace();
                        }
                        // TODO: create another method saving room ID
                        headerAccessor.getSessionAttributes().put("roomId", roomId);
                    } else {
                        return reportRoomFieldOrDestroyed();
                    }
                    return successReport( roomId, sender);
                }
            }
            // TODO: create report to User
            log.error("Incorrect status represent message. Message status: {}", chatMessage.getType());
            throw new IncorrectStatusMessageExeption();
        }
        // TODO: create report to User
        log.error("User name empty");
        throw new EmptySenderNameSpaceException();
    }

    @Override
    public void roomDestroy(AbstractSubProtocolEvent event, String username, UUID roomUUID, Long id, boolean removeAttributes) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Room room = null;
        log.debug("Starting room destroy event. Input values: room id - {} , username - {}",
                roomUUID,
                username);

        roomUUID = roomUUID == null ?
                (UUID) Objects.requireNonNull(accessor.getSessionAttributes()).get("roomId")
                : roomUUID;

        username = username == null ?
                (String) Objects.requireNonNull(accessor.getSessionAttributes()).get("sender")
                : username;

        if (null != id){
            room = roomRepo.getOne(id);
        } else {
            room = roomRepo.findByRoom(roomUUID);
        }

        log.debug("Move in db and get {}", room);
        if (room != null) {
            if (!username.isEmpty()) {
                boolean hasHost = username.equals(room.getHost());
                boolean hasOpponent = username.equals(room.getOpponent());
                if (hasHost && hasOpponent) {
                    log.error("Sender listed in two room slots. Illegal room state. Sender : {}, {}", username, roomUUID);
                    throw new IllegalRoomStateException();
                }
                if (hasHost) {
                    room.setHost(null);
                    if (removeAttributes) {
                        Objects.requireNonNull(accessor.getSessionAttributes()).remove("roomId");
                    }
                } else {
                    if (hasOpponent) {
                        room.setOpponent(null);
                        if (removeAttributes) {
                            Objects.requireNonNull(accessor.getSessionAttributes()).remove("roomId");
                        }
                    } else {
                        log.error("Player not belongs that room. Sender : {} expect {} or {} , room id: {}",
                                username,
                                room.getHost(),
                                room.getOpponent(),
                                roomUUID);
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
                    String sendTo = room.getRoom().toString();
                    String text = String.format("Player %s leave room", username);
                    messageCreator.createAndSendTo(sendTo,text,"SERVER",LEAVE);
                }
            }

        }
    }

    private void joinFirstPlayer(UUID roomId, String sender, Room roomFromDb)
            throws IncorrectStatusOfTheCreatedRoomException {
        if (null == roomFromDb.getHost() && null == roomFromDb.getOpponent()) {
            roomFromDb.setHost(sender);
            roomFromDb.setRoomStatus(WAIT);
            log.info("Add host players in new room. Player name: {}, room id: {}", sender, roomId);
            roomRepo.save(roomFromDb);
        } else {
            throw new IncorrectStatusOfTheCreatedRoomException();
        }
    }

    private void joinSecondPlayer( String sender, Room roomFromDb)
            throws IncorrectStatusOfTheCreatedRoomException {
        if (!sender.equals(roomFromDb.getHost()) && null == roomFromDb.getOpponent()) {
            roomFromDb.setOpponent(sender);
            roomFromDb.setRoomStatus(FILED);
            roomRepo.save(roomFromDb);
        } else {
            throw new IncorrectStatusOfTheCreatedRoomException();
        }
    }

    private ChatMessage successReport( UUID roomId, String sender) {
        log.info("User '{}' join in room. Room id: {}", sender, roomId);
        return messageCreator.create("Success join. Just enjoy","SERVER", JOIN);
    }

    private ChatMessage reportRoomFieldOrDestroyed() {
        log.debug("Room filed or destroyed");
        return messageCreator.create("Room filed or destroyed","SERVER", CHAT);
    }

}
