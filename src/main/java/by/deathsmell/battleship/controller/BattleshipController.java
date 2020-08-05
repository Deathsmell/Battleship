package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.validators.BattleFieldValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BattleshipController {

    @PostMapping("/validationField")
    public ResponseEntity<HttpStatus> validationField(@RequestBody int[][] field) {
        boolean valid = BattleFieldValidator.fieldValidator(field);
        return valid ? ResponseEntity.ok(HttpStatus.OK) : ResponseEntity.badRequest().build();
    }

    @MessageMapping("/room.{roomId}")
    @SendTo("/topic/room.{roomId}")
    public String checkField(@DestinationVariable String roomId) {
        return null;
    }

}

