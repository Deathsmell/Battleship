package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.service.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class UserController {
    private final UserManager userManager;

    @Autowired
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(String username){
        log.debug("REGISTRATION " + username);
        try {
            userManager.createUser(username,null);
        } catch (IllegalArgumentException e){
            log.error("Registration controller exception");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Registration controller exception");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Successful registration. Enjoy!");
    }

}
