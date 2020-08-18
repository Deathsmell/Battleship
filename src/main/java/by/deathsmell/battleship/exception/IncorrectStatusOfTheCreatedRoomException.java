package by.deathsmell.battleship.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class IncorrectStatusOfTheCreatedRoomException extends IllegalSenderAction {
    public IncorrectStatusOfTheCreatedRoomException() {
        super();
    }

    public IncorrectStatusOfTheCreatedRoomException(String message) {
        super(message);
    }
}
