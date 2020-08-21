package by.deathsmell.battleship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ship {

    private ShipType shipType;

    public enum ShipType {
        BATTLESHIP, CRUISER, DESTROYER, SUBMARINE
    }
}
