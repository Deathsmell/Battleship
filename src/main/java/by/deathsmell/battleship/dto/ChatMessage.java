package by.deathsmell.battleship.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
//    private Room room;


    public enum MessageType implements ReportType {
        CHAT, JOIN, LEAVE, DISCONNECT
    }

}
