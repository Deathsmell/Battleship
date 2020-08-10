package by.deathsmell.battleship.domain;

import by.deathsmell.battleship.converter.CustomUuidDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonDeserialize(using = CustomUuidDeserializer.class)
    private UUID room;
    private String host;
    private String opponent;
    private RoomStatus roomStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalDateTime createTime;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ChatMessage> chat = new ArrayList<>();

    public enum RoomStatus {
        CREATE, WAIT, FILED, DESTROY
    }

}
