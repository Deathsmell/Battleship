package by.deathsmell.battleship.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RestController
@RequestMapping("/room")
@Slf4j
public class RoomController {

    private static final String API = "http://localhost:8080/room/";

    @GetMapping("/new")
    public ModelAndView createRoom(){
        return new ModelAndView("redirect:" + API + UUID.randomUUID());
    }

    @GetMapping("/{roomId}")
    public ModelAndView joinToRoom(@PathVariable String roomId){
        return new ModelAndView("redirect:" + API + roomId);
    }

}
