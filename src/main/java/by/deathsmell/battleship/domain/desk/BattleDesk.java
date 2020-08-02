package by.deathsmell.battleship.domain.desk;


import by.deathsmell.battleship.domain.desk.ships.Battleship;
import by.deathsmell.battleship.domain.desk.ships.Cruiser;
import by.deathsmell.battleship.domain.desk.ships.Destroyer;
import by.deathsmell.battleship.domain.desk.ships.Submarine;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.List;

@Data
@Entity
public class BattleDesk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Battleship battleships;

    @OneToMany
    @Max(value = 2)
    private List<Cruiser> cruisers;

    @OneToMany
    @Max(value = 3)
    private List<Destroyer> destroyers;

    @OneToMany
    @Max(value = 4)
    private List<Submarine> submarines;


}
