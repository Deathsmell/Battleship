package by.deathsmell.battleship.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID room;
    private String player1;
    private String player2;
    private State state;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ChatMessage> chat = new ArrayList<>();

    public enum State {
        CREATE, WAIT,FILED, DESTROY
    }

//    @JsonIgnore
//    public boolean isEmptyRoom(){
//        return (player1.isEmpty() && player2.isEmpty());
//    }
}
