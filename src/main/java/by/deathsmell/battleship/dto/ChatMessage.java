package by.deathsmell.battleship.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ChatMessage implements Serializable {

    private MessageType type;
    private String content;
    private String sender;
    private UUID room;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

}
