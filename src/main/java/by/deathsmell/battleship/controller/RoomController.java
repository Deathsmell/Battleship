package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.exception.EmptySenderNameSpaceException;
import by.deathsmell.battleship.exception.IncorrectStatusMessageExeption;
import by.deathsmell.battleship.repositories.RoomRepository;
import by.deathsmell.battleship.service.RoomCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/room")
@Slf4j
public class RoomController {

    private final RoomRepository roomRepo;
    private final RoomCreator roomCreator;


    @Autowired
    public RoomController(RoomRepository roomRepo, RoomCreator roomCreator) {
        this.roomRepo = roomRepo;
        this.roomCreator = roomCreator;
    }

    @GetMapping("/create")
    public Room createRoom() {
        return roomCreator.createRoom();
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

    @MessageMapping("/room/{roomId}/join")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage joinToRoom(@Payload ChatMessage message,
                                  @DestinationVariable UUID roomId,
                                  SimpMessageHeaderAccessor headerAccessor)
            throws IncorrectStatusMessageExeption, EmptySenderNameSpaceException {
        return roomCreator.joinToRoom(message, roomId,headerAccessor);
    }


}
