package by.deathsmell.battleship.service;

import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.domain.Room.RoomStatus;
import by.deathsmell.battleship.domain.User;
import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.exception.EmptySenderNameSpaceException;
import by.deathsmell.battleship.exception.IllegalRoomStateException;
import by.deathsmell.battleship.exception.IllegalSenderAction;
import by.deathsmell.battleship.exception.IncorrectStatusOfTheCreatedRoomException;
import by.deathsmell.battleship.repositories.RoomRepository;
import com.sun.istack.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static by.deathsmell.battleship.domain.Room.RoomStatus.*;
import static by.deathsmell.battleship.dto.ChatMessage.MessageType.JOIN;
import static by.deathsmell.battleship.dto.ChatMessage.MessageType.LEAVE;

@Slf4j
@Service
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
        Room newRoom = Room
                .withUUID(UUID.randomUUID())
                .status(CREATE)
                .createTime(LocalDateTime.now())
                .build();
        roomRepo.save(newRoom);
        log.info("Create new room and save in database. {}", newRoom);
        return newRoom;
    }

    @Override
    public void joinToRoom(@Nullable UUID room, @Nullable User user)
            throws IncorrectStatusOfTheCreatedRoomException, EmptySenderNameSpaceException {
        log.debug("Start joining into room");
        String sender = user.getUsername();
        if (null != sender) {
            if (null != room) {
                Room roomFromDb = roomRepo.findByRoom(room);
                log.debug("Find room in DB. Room: {}", roomFromDb);
                RoomStatus roomStatus = roomFromDb.getRoomStatus();
                if (roomStatus == CREATE) {
                    joinFirstPlayer(room, sender, roomFromDb);
                } else if (roomStatus == WAIT) {
                    joinSecondPlayer(sender, roomFromDb);
                } else {
                    log.error("While joining to room a room status be or become illegal");
                    throw new IllegalRoomStateException("While joining to room a room status be or become illegal");
                }
                messageCreator.createAndSendTo(
                        "/topic/room/" + room.toString(),
                        "Success join. Just enjoy",
                        "SERVER", JOIN
                );
            } else {
                log.error("Room uuid illegal or room not exist");
                throw new IllegalRoomStateException("Room uuid illegal or room not exist");
            }
        } else {
            log.error("User name empty");
            throw new EmptySenderNameSpaceException("User name empty");
        }
    }

    @Override
    public void roomDestroy(@Nullable String username) {
        log.debug("Starting room destroy event. Input values: username - {}",
                username);

        Room room = roomRepo.findRoomByHostOrOpponent(username);

        log.debug("Move in db and get {}", room);
        if (room != null) {
            boolean hasHost = username.equals(room.getHost());
            boolean hasOpponent = username.equals(room.getOpponent());
            if (hasHost && hasOpponent) {
                log.error("Sender listed in two room slots. Illegal room state. Sender : {}", username);
                throw new IllegalRoomStateException();
            }
            if (!(hasHost || hasOpponent)) {
                log.error("Player not belongs that room. Sender : {} expect {} or {} , room uuid: {}",
                        username,
                        room.getHost(),
                        room.getOpponent(),
                        room.getRoom());
                throw new IllegalSenderAction("Player not belongs that room");
            }
            if (room.getRoomStatus().equals(DESTROY)) {
                log.info("Room is empty. Deleting: {}", room);
                roomRepo.delete(room);
            } else {
                log.debug("Room have give status DESTROY");
                room.setRoomStatus(DESTROY);
                roomRepo.save(room);
                String sendTo = room.getRoom().toString();
                String text = String.format("Player %s leave room", username);
                messageCreator.createAndSendTo(sendTo, text, "SERVER", LEAVE);
            }
        } else {
            log.error("Not found room in DB. Room not exist or input value illegal or incorrect");
            throw new IllegalArgumentException("Not found room in DB. Room not exist or input value illegal or incorrect");
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
            log.error("Incorrect status of the create room." +
                            " One or two place in room taken. {}",
                    roomFromDb);
            throw new IncorrectStatusOfTheCreatedRoomException("Incorrect status of the create room.One or two place in room taken");
        }
    }

    private void joinSecondPlayer(String sender, Room roomFromDb)
            throws IncorrectStatusOfTheCreatedRoomException {
        if (!sender.equals(roomFromDb.getHost()) && null == roomFromDb.getOpponent()) {
            roomFromDb.setOpponent(sender);
            roomFromDb.setRoomStatus(FILED);
            roomRepo.save(roomFromDb);
        } else {
            log.error("Incorrect status of the create room." +
                            " Sender try to rejoin second time or room filed. {}.",
                    roomFromDb);
            throw new IncorrectStatusOfTheCreatedRoomException("Incorrect status of the create room.One or two place in room taken");
        }
    }


}
