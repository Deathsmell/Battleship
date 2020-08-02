package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.validators.BattleFieldValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BattleshipController {

    @GetMapping("/field")
    public int[][] getField() {
        return new int[][]{
                {1, 0, 0, 0, 0, 1, 1, 0, 0, 0}, // 0
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 0}, // 1
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 0}, // 2
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 3
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // 4
                {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, // 5
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // 6
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, // 7
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, // 8
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}  // 9
        };
    }

    @PostMapping("/validationField")
    public ResponseEntity<HttpStatus> validationField(@RequestBody int[][] field) {
        boolean valid = BattleFieldValidator.fieldValidator(field);
        return valid ? ResponseEntity.ok(HttpStatus.OK) : ResponseEntity.badRequest().build();
    }
}

