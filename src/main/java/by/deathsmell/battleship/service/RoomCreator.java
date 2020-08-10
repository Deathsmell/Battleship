package by.deathsmell.battleship.service;

import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.dto.ChatMessage;
import by.deathsmell.battleship.exception.EmptySenderNameSpaceException;
import by.deathsmell.battleship.exception.IncorrectStatusMessageExeption;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

import java.util.UUID;

public interface RoomCreator {
    Room createRoom();
    ChatMessage joinToRoom(ChatMessage chatMessage, UUID roomId,SimpMessageHeaderAccessor headerAccessor) throws IncorrectStatusMessageExeption, EmptySenderNameSpaceException;
    void roomDestroy(final AbstractSubProtocolEvent event, String username, UUID roomUUID, Long id, boolean removeAttributes);
}
