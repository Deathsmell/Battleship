import Validators.BattleFieldValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BattleFieldTest {

    private static final int[][] battleField = {
            //  1  2  3  4  5  6  7  8  9   X  Y
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0}, // 0
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 0}, // 1
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 0}, // 2
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 3
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // 4
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // 6
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, // 7
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, // 8
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};// 9

    private static final int[][] errorField = {
            //  1  2  3  4  5  6  7  8  9   X  Y
            {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, //0
            {0, 0, 1, 0, 0, 0, 0, 0, 1, 0}, //1
            {0, 0, 1, 0, 1, 1, 1, 0, 1, 0}, //2
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //3
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, //4
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, //5
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, //6
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, //7
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, //8
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};//9


    @Test
    public void returnTrueIfFieldValid() {
        assertTrue(BattleFieldValidator.fieldValidator(battleField));
    }

    @Test
    public void returnFalse() {
        assertFalse(BattleFieldValidator.fieldValidator(errorField));
    }
}