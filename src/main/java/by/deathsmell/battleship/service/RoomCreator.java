package by.deathsmell.battleship.service;

import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.domain.User;
import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.exception.EmptySenderNameSpaceException;
import by.deathsmell.battleship.exception.IncorrectStatusMessageExeption;
import by.deathsmell.battleship.exception.IncorrectStatusOfTheCreatedRoomException;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

import java.util.UUID;

public interface RoomCreator {
    Room createRoom();
    void joinToRoom(UUID room, User user) throws IncorrectStatusMessageExeption, EmptySenderNameSpaceException, IncorrectStatusOfTheCreatedRoomException;
    void roomDestroy(String username);
}
