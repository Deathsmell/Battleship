package by.deathsmell.battleship.repositories;

import by.deathsmell.battleship.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room,Long> {

    Room findByRoom(UUID roomId);

    List<Room> findAllByHostNotNullOrOpponentNotNull();
}
