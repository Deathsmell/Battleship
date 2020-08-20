package by.deathsmell.battleship.service;

import by.deathsmell.battleship.domain.Desk;
import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.domain.User;

public interface DeskCreator {
    Desk createDesk(User user, Room room, int[][] field);
}
