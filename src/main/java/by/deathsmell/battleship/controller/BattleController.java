package by.deathsmell.battleship.controller;


import by.deathsmell.battleship.domain.Player;
import by.deathsmell.battleship.domain.desk.Battle;
import by.deathsmell.battleship.domain.desk.BattleDesk;
import by.deathsmell.battleship.repositories.BattleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/battle")
public class BattleController {

    private final BattleRepository battleRepo;

    @Autowired
    public BattleController(BattleRepository battleRepo) {
        this.battleRepo = battleRepo;
    }

    @PostMapping("/create")
    public Long createBattleLobby (final @RequestBody Player playerOne, final @RequestBody Player playerTwo){
        Battle battle = new Battle();
        battle.setStart(LocalDateTime.now());

        battle.setPlayerOne(playerOne);
        battle.setPlayerTwo(playerTwo);

        battle.setDeskPlayerOne(new BattleDesk());
        battle.setDeskPlayerTwo(new BattleDesk());

        return battleRepo.save(battle).getId();
    }

    @PostMapping("/start")
    public void startBattle (final @RequestBody Long id,final BattleDesk playerOneDesk, final @RequestBody BattleDesk playerTwoDesk){
        battleRepo.findById(id)
                .ifPresent(battle -> {
                    battle.setDeskPlayerOne(playerOneDesk);
                    battle.setDeskPlayerTwo(playerTwoDesk);
                });
    }

    @PostMapping("/stop")
    public void stopBattle (final @RequestBody Long battleId){
        battleRepo.findById(battleId).ifPresent(battle -> battle.setEnd(LocalDateTime.now()));
    }

}
