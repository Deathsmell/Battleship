package by.deathsmell.battleship.repositories;

import by.deathsmell.battleship.domain.Desk;
import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeskRepository extends JpaRepository<Desk,Long> {

    Desk findByUserAndRoom(User user, UUID room);

    Desk findByUserNotAndRoom(User user, Room room);

}
