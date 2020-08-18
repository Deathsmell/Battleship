package by.deathsmell.battleship.exception;

public class IncorrectStatusMessageException extends IllegalSenderAction {
    public IncorrectStatusMessageException() {
        super();
    }

    public IncorrectStatusMessageException(String message) {
        super(message);
    }
}
