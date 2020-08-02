package by.deathsmell.battleship.domain.desk;

import by.deathsmell.battleship.domain.Player;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;

    @OneToOne
    private BattleDesk deskPlayerOne;

    @OneToOne
    private BattleDesk deskPlayerTwo;

    @OneToOne
    private Player playerOne;

    @OneToOne
    private Player playerTwo;

    @OneToOne
    private Player won;
}
