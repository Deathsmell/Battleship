package by.deathsmell.battleship.domain.desk.ships;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.awt.*;

@Data
@Entity
public abstract class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Point startPosition;
    private Point endPosition;
}
