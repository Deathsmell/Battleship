package by.deathsmell.battleship.Validators;

import java.util.Arrays;

public class BattleFieldValidator {
    public static boolean fieldValidator(int[][] field) {

        if (field.length != 10 && field[0].length != 10) {
            return false;
        }

        int battleship = 1;
        int cruisers = 2;
        int destroyers = 3;
        int submarines = 4;
        int [] array = {0,submarines,destroyers,cruisers,battleship};

        boolean valid = true;

        for (int fieldY = 0; fieldY < field.length; fieldY++) {
            for (int fieldX = 0; fieldX < field[fieldY].length; fieldX++) {
                if (field[fieldY][fieldX] == 1) {
                    int minDotYOnField = fieldY != 0 ? fieldY - 1 : fieldY;
                    int minDotXOnField = fieldX != 0 ? fieldX - 1 : fieldX;


                    int maxDotYOnField = fieldY != 9 ? fieldY + 1 : fieldY;
                    int maxDotXonField = fieldX != 9 ? fieldX + 1 : fieldX;

                    int count = 0;
                    int[][] dots = new int[3][2];

                    int i = 0;

                    for (int markY = minDotYOnField; markY <= maxDotYOnField; markY++) {
                        for (int markX = minDotXOnField; markX <= maxDotXonField; markX++) {
                            if (field[markY][markX] == 1) {
                                if (markX != fieldX || markY != fieldY) {
                                    dots[i][0] = markX;
                                    dots[i][1] = markY;
                                    i++;
                                }
                                count++;
                            }
                            if (count > 3) {
                                return false;
                            }
                        }
                    }

                    if (count == 1) {
                        array[1]--;
                        continue;
                    }

                    if (count == 3) {
                        if ((dots[0][1] ^ dots[1][1]) == 0 || (dots[0][0] ^ dots[1][0]) == 0) continue;
                        return false;
                    }

                    int upOrDown = 0, leftOrRight = 0;
                    if (count == 2) {
                        leftOrRight = fieldX != dots[0][0] ? (fieldX > dots[0][0] ? -1 : 1) : 0;
                        upOrDown = fieldY != dots[0][1] ? (fieldY > dots[0][1] ? -1 : 1) : 0;
                    }
                    if (leftOrRight == -1 || upOrDown == -1) continue;

                    int endPositionY = fieldY;
                    int endPositionX = fieldX;

                    int sumAreaBattleship = 0;

                    while (field[endPositionY][endPositionX] == 1) {
                        sumAreaBattleship++;
                        if ((endPositionX == 9 && leftOrRight == 1) || (endPositionY == 9 && upOrDown == 1)) {
                            break;
                        }
                        endPositionX += leftOrRight;
                        endPositionY += upOrDown;
                    }
                    int sumAreaWithOutBattleship = 0;

                    for (int y = fieldY; y <= endPositionY; y++) {
                        for (int x = fieldX; x <= endPositionX; x++) {
                            sumAreaWithOutBattleship += field[y][x];
                        }
                    }

                    valid = sumAreaBattleship == sumAreaWithOutBattleship;
                    if (valid && sumAreaBattleship < 5){
                        array[sumAreaBattleship]--;
                    }
                }
                if (array[1] < 0 || array[2] < 0 || array[3] < 0 || array[4] < 0 ){
                    return false;
                }
                if (!valid) {
                    System.err.println("Y = " + fieldY + " X = " + fieldX);
                    System.out.println(Arrays.deepToString(field));
                    return false;
                }
            }
        }
        return array[1] <= 0 && array[2] <= 0 && array[3] <= 0 && array[4] <= 0;
    }

}
