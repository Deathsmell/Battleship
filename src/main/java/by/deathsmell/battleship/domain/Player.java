package by.deathsmell.battleship.domain;


import by.deathsmell.battleship.domain.desk.Battle;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class Player {

    public Player(){};

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "player",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<Battle> battles;
}

