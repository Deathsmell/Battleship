package by.deathsmell.battleship.exception;

public class IllegalRoomStateException extends RuntimeException {

    public IllegalRoomStateException() {
        super();
    }

    public IllegalRoomStateException(String message) {
        super(message);
    }
}
