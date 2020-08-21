package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.domain.Desk;
import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.domain.User;
import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.repositories.DeskRepository;
import by.deathsmell.battleship.repositories.RoomRepository;
import by.deathsmell.battleship.service.DeskCreator;
import by.deathsmell.battleship.service.DeskValidator;
import by.deathsmell.battleship.service.ReportMessageCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
public class BattleshipController {

    private final DeskValidator validator;
    private final DeskCreator creator;
    private final RoomRepository roomRepo;
    private final ReportMessageCreator<ChatMessage> messageCreator;
    private final DeskRepository deskRepo;

    public BattleshipController(DeskValidator validator, DeskCreator creator, RoomRepository roomRepo, ReportMessageCreator<ChatMessage> messageCreator, DeskRepository deskRepo) {
        this.validator = validator;
        this.creator = creator;
        this.roomRepo = roomRepo;
        this.messageCreator = messageCreator;
        this.deskRepo = deskRepo;
    }

    @PostMapping("/validationField")
    public ResponseEntity<HttpStatus> validationField(@RequestBody int[][] field) {
        return validator.isValid(field) ?
                ResponseEntity.ok(HttpStatus.OK) :
                ResponseEntity.badRequest().build();
    }

    @PostMapping("desk/create")
    @Transactional
    public ResponseEntity<HttpStatus> createDesk(@AuthenticationPrincipal User user,
                                                 @RequestParam UUID roomId,
                                                 @RequestBody int[][] field) {
        Room roomByDb = roomRepo.findByRoom(roomId);
        Desk desk;
        try {
            desk = creator.createDesk(user, roomByDb, field);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        deskRepo.save(desk);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @MessageMapping("/room/{roomId}/check.{point}")
    @SendTo("/topic/room/{roomId}")
    @Transactional
    public ChatMessage checkField(@AuthenticationPrincipal User user,
                                  @DestinationVariable UUID roomId,
                                  @DestinationVariable Point point) {

        // TODO: 8/21/20 Create common query to DB
        Room roomFromDb = roomRepo.findByRoom(roomId);
        Desk opponentDesk = deskRepo.findByUserNotAndRoom(user, roomFromDb);

        if (opponentDesk != null) {

            Set<Point> points = opponentDesk.getPoints();
            boolean contains = points.contains(point);

            if (contains) return messageCreator.create("shoot", "SERVER", ChatMessage.MessageType.CHAT);

        }

        return messageCreator.create("miss", "SERVER", ChatMessage.MessageType.CHAT);

    }

}

