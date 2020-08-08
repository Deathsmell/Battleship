package by.deathsmell.battleship.dto;


import by.deathsmell.battleship.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    private MessageType type;
    private String content;
    private String sender;

//    @OneToOne(fetch = FetchType.LAZY)
//    private Room room;

    public enum MessageType {
        CHAT, JOIN, LEAVE, DISCONNECT
    }

}
