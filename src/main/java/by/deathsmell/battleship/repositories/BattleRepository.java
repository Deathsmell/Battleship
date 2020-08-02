package by.deathsmell.battleship.repositories;

import by.deathsmell.battleship.domain.desk.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleRepository extends JpaRepository<Battle,Long> {
}
