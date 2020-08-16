package by.deathsmell.battleship.service;

import by.deathsmell.battleship.domain.User;
import by.deathsmell.battleship.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
public class UserService implements UserManager {

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public void createUser(String username, Set<User.UserRole> roles) {
        log.info("Create new user");
        User user = User
                .withUsername(username)
                .roles(roles)
                .time(LocalDateTime.now())
                .build();
        createUser(user);
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isTrue(!userExists(user.getUsername()), "User should not exist!");
        log.debug("{}",user);
        log.debug("{}",(User) user);
        userRepo.save((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        userRepo.save((User) user);
    }

    @Override
    public void deleteUser(String username) {
        userRepo.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        log.error("This app unsupported password authentication.");
        throw new UnsupportedOperationException("This app unsupported password authentication");
    }

    @Override
    public boolean userExists(String username) {
        return userRepo.existsByUsername(username);
    }


}
