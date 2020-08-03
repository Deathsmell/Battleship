package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.Validators.BattleFieldValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting (String message){
        return message;
    }

}

