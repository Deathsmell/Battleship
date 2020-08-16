package by.deathsmell.battleship.domain;

import by.deathsmell.battleship.converter.CustomUuidDeserializer;
import by.deathsmell.battleship.dto.ReportType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public enum RoomStatus implements ReportType {
        CREATE, WAIT, FILED, DESTROY
    }

    public static RoomBuilder builder(){
        return new RoomBuilder();
    }

    public static RoomBuilder withUUID(UUID uuid){
        return builder().uuid(uuid);
    }

    public static class RoomBuilder {
        private Long id;
        private UUID room;
        private String host;
        private String opponent;
        private RoomStatus roomStatus;
        private LocalDateTime createTime;

        public RoomBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RoomBuilder uuid(UUID room) {
            Assert.notNull(room,"uuid cannot be null");
            this.room = room;
            return this;
        }

        public RoomBuilder host(String host) {
            Assert.notNull(host,"host cannot be null");
            this.host = host;
            return this;
        }

        public RoomBuilder opponent(String opponent) {
            Assert.notNull(opponent,"opponent cannot be null");
            this.opponent = opponent;
            return this;
        }

        public RoomBuilder status(RoomStatus roomStatus) {
            Assert.notNull(roomStatus,"status cannot be null");
            this.roomStatus = roomStatus;
            return this;
        }

        public RoomBuilder createTime(LocalDateTime createTime) {
            Assert.notNull(createTime,"create time cannot be null");
            this.createTime = createTime;
            return this;
        }

        public Room build(){
            return new Room(id,room,host,opponent,roomStatus,createTime);
        }
    }

}
