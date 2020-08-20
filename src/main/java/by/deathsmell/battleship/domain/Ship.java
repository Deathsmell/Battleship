package by.deathsmell.battleship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.awt.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ship {

    private Point startPosition;

    private Point endPosition;

    private ShipType shipType;

    public enum ShipType {
        BATTLESHIP, CRUISER, DESTROYER, SUBMARINE
    }
}
