package by.deathsmell.battleship.service;

import by.deathsmell.battleship.domain.User;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Set;

public interface UserManager extends UserDetailsManager {
    void createUser(String username, Set<User.UserRole> roles);
}
