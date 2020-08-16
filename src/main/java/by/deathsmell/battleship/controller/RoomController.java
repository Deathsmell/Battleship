package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.domain.User;
import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.exception.EmptySenderNameSpaceException;
import by.deathsmell.battleship.exception.IllegalRoomStateException;
import by.deathsmell.battleship.exception.IncorrectStatusMessageExeption;
import by.deathsmell.battleship.exception.IncorrectStatusOfTheCreatedRoomException;
import by.deathsmell.battleship.repositories.RoomRepository;
import by.deathsmell.battleship.service.ReportMessageCreator;
import by.deathsmell.battleship.service.RoomCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/get")
    public Room getRoom (@RequestParam UUID uuid) {
        return roomRepo.findByRoom(uuid);
    }

    @GetMapping("/join")
    public ResponseEntity<String> joinToRoom(@AuthenticationPrincipal User user,
                                             @RequestParam UUID room) {
        try {
            roomCreator.joinToRoom(room, user);
        } catch (IncorrectStatusOfTheCreatedRoomException |
                IncorrectStatusMessageExeption |
                IllegalRoomStateException |
                EmptySenderNameSpaceException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(e.getMessage());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Success! Enjoying game");
    }

    // FIXME: Name method dont right. Create get method
    @PutMapping
    public Room updateRoom(@RequestBody Room room) {
        Room roomFromDb = roomRepo.findByRoom(room.getRoom());
        log.debug("Update room : {}", roomFromDb);
        return roomFromDb;
    }

}
