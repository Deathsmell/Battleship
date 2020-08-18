package by.deathsmell.battleship.service;

import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.domain.User;
import by.deathsmell.battleship.exception.EmptySenderNameSpaceException;
import by.deathsmell.battleship.exception.IncorrectStatusMessageException;
import by.deathsmell.battleship.exception.IncorrectStatusOfTheCreatedRoomException;

import java.util.UUID;

public interface RoomCreator {
    Room createRoom();
    void joinToRoom(UUID room, User user) throws IncorrectStatusMessageException, EmptySenderNameSpaceException, IncorrectStatusOfTheCreatedRoomException;
    void roomDestroy(String username);
}
