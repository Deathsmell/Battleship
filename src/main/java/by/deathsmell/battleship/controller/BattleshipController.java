package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.service.DeskCreator;
import by.deathsmell.battleship.service.DeskValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BattleshipController {

    private final DeskValidator validator;
    private final DeskCreator creator;

    public BattleshipController(DeskValidator validator, DeskCreator creator) {
        this.validator = validator;
        this.creator = creator;
    }

    @PostMapping("/validationField")
    public ResponseEntity<HttpStatus> validationField(@RequestBody int[][] field) {
        return validator.isValid(field) ?
                ResponseEntity.ok(HttpStatus.OK) :
                ResponseEntity.badRequest().build();
    }

    @MessageMapping("/room/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public String checkField(@DestinationVariable UUID roomId) {
        return null;
    }

}

