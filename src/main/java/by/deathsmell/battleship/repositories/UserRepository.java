package by.deathsmell.battleship.repositories;

import by.deathsmell.battleship.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,UserRepository> {

    User findByUsername (String username);

    boolean existsByUsername (String username);

    void deleteByUsername(String username);
}
