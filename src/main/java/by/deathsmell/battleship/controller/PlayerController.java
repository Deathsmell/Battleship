package by.deathsmell.battleship.controller;

import by.deathsmell.battleship.domain.Player;
import by.deathsmell.battleship.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/player")
public class PlayerController {

    private final PlayerRepository playerRepo;

    @Autowired
    public PlayerController(PlayerRepository playerRepo) {
        this.playerRepo = playerRepo;
    }

    @GetMapping("/all")
    public List<Player> getAllPlayers() {
        return playerRepo.findAll();
    }

    @GetMapping("/{id}")
    public Player getOne (final @PathVariable("id") Long id){
        return playerRepo.getOne(id);
    }

    @PostMapping("/new")
    public Player createNewPlayer(final @RequestBody String username) {
        Player player = new Player();
        player.setName(username);
        player.setBattles(new ArrayList<>());
        return playerRepo.save(player);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deletePlayer(final @PathVariable("id") Long id) {
        playerRepo.deleteById(id);
        return true;
    }

    @PutMapping("/alter/{id}")
    public Player alterPlayer(final @PathVariable("id") Long id, final @RequestBody String username) {
        Player player = playerRepo.getOne(id);
        player.setName(username);
        return player;
    }

}
