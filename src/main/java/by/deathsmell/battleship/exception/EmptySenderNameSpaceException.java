package by.deathsmell.battleship.exception;

public class EmptySenderNameSpaceException extends IllegalSenderAction {
    public EmptySenderNameSpaceException() {
        super();
    }

    public EmptySenderNameSpaceException(String message) {
        super(message);
    }
}
