package by.deathsmell.battleship.service;

import by.deathsmell.battleship.domain.Desk;
import by.deathsmell.battleship.domain.Room;
import by.deathsmell.battleship.domain.Ship;
import by.deathsmell.battleship.domain.User;
import by.deathsmell.battleship.dto.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Arrays;

import static by.deathsmell.battleship.domain.Ship.ShipType.*;

@Component
public class BattlefieldService implements DeskCreator, DeskValidator {

    private final ReportChatMessage reportChatMessage;

    private static final int battleship = 1;
    private static final int cruisers = 2;
    private static final int destroyers = 3;
    private static final int submarines = 4;
    private static final int[] array = {0, submarines, destroyers, cruisers, battleship};

    private boolean valid = true;

    private final Desk.DeskBuilder builder = Desk.builder();

    @Autowired
    public BattlefieldService(ReportChatMessage reportChatMessage) {
        this.reportChatMessage = reportChatMessage;
    }

    @Override
    public Desk createDesk(User user, Room room, int[][] field) {
        boolean valid = this.fieldValidator(field, true);
        if (valid) {
            return builder
                    .user(user)
                    .room(room)
                    .build();
        } else {
            String sentTo = room.getRoom().toString();
            String report = formatterErrorReport();
            reportChatMessage.createAndSendTo(
                    sentTo,
                    report,
                    "SERVER",
                    ChatMessage.MessageType.CHAT
            );
            throw new IllegalArgumentException(report);
        }
    }

    @Override
    public boolean isValid(int[][] field) {
//        return new BattlefieldService(reportChatMessage).fieldValidator(field, false);
        // TODO: 8/19/20 uncomment this row then use in Spring app
        return fieldValidator(field,false);
    }

    private String witchType(int i) {
        if (i == 0) {
            throw new IllegalArgumentException("Ship should not be 0 type");
        } else {
            switch (i) {
                case 1:
                    return SUBMARINE.toString();
                case 2:
                    return DESTROYER.toString();
                case 3:
                    return CRUISER.toString();
                case 4:
                    return BATTLESHIP.toString();
                default:
                    throw new IllegalArgumentException("Ship should not be 5 or more type");
            }
        }
    }

    protected String formatterErrorReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("Error! ");
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                if (i != array.length - 1 && i != 1) {
                    builder.append(" and ");
                }
                String report = String.format("%d %s ship(s)", array[i], witchType(i));
                builder.append(report);
            }
        }
        builder.append("lacks");
        return builder.toString();
    }

    protected void addShip(int typeShip, int startX, int startY, int endX, int endY) {
        if (typeShip < 0 || typeShip > 4) {
            throw new IllegalArgumentException("Ship should be one to four");
        }
        Point startPosition = new Point(startX, startY);
        Point endPosition;
        if (startX != endX) {
            endPosition = new Point(endX - 1, endY);
        } else {
            endPosition = new Point(endX, endY - 1);
        }

        switch (typeShip) {
            case 2:
                builder.destroyer(new Ship(startPosition, endPosition, DESTROYER));
                break;
            case 3:
                builder.cruiser(new Ship(startPosition, endPosition, CRUISER));
                break;
            case 4:
                builder.battleship(new Ship(startPosition, endPosition, BATTLESHIP));
                break;
        }
    }

    protected boolean fieldValidator(int[][] field, boolean buildDesk) {
        if (field.length != 10 && field[0].length != 10) {
            return false;
        }

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
                        if (buildDesk) {
                            Point point = new Point(fieldX, fieldY);
                            builder.submarine(new Ship(point, point, SUBMARINE));
                        }
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
                    if (valid && sumAreaBattleship < 5) {

                        if (buildDesk) addShip(sumAreaBattleship, fieldX, fieldY, endPositionX, endPositionY);

                        array[sumAreaBattleship]--;
                    }
                }
                if (array[1] < 0 || array[2] < 0 || array[3] < 0 || array[4] < 0) {
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
