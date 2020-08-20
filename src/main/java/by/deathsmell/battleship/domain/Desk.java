package by.deathsmell.battleship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Desk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Ship battleship;

    @ElementCollection(targetClass = Ship.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "cruisers", joinColumns = @JoinColumn(name = "desk_id"), foreignKey = @ForeignKey(name = "cruisers_desk_id_fk"))
    private List<Ship> cruisers;

    @ElementCollection(targetClass = Ship.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "destroyers", joinColumns = @JoinColumn(name = "desk_id"), foreignKey = @ForeignKey(name = "destroyers_desk_id_fk"))
    private List<Ship> destroyers;

    @ElementCollection(targetClass = Ship.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "submarines", joinColumns = @JoinColumn(name = "desk_id"), foreignKey = @ForeignKey(name = "submarines_desk_id_fk"))
    private List<Ship> submarines;

    @OneToOne
    private Room room;

    @OneToOne
    private User user;

    public static DeskBuilder builder() {
        return new DeskBuilder();
    }

    public static DeskBuilder withRoom(Room room) {
        return builder().room(room);
    }

    public static DeskBuilder withUser(User user) {
        return builder().user(user);
    }

//    public static DeskBuilder withUUID(UUID uuid) {
//        return builder().roomId(uuid);
//    }

    public static class DeskBuilder {

        private Ship battleship;

        private List<Ship> cruisers;

        private List<Ship> destroyers;

        private List<Ship> submarines;

        private Room room;

        private User user;

        public DeskBuilder battleship(Ship battleship) {
            this.battleship = battleship;
            return this;
        }

        public DeskBuilder cruisers(List<Ship> cruisers) {
            this.cruisers = cruisers;
            return this;
        }

        public DeskBuilder cruiser(Ship cruiser) {
            if (this.cruisers == null) {
                this.cruisers = new ArrayList<>();
            }
            this.cruisers.add(cruiser);
            return this;
        }

        public DeskBuilder destroyers(List<Ship> destroyers) {
            this.destroyers = destroyers;
            return this;
        }

        public DeskBuilder destroyer(Ship destroyer) {
            if (this.destroyers == null){
                this.destroyers = new ArrayList<>();
            }
            this.destroyers.add(destroyer);
            return this;
        }

        public DeskBuilder submarines(List<Ship> submarines) {
            this.submarines = submarines;
            return this;
        }

        public DeskBuilder submarine(Ship submarine) {
            if (this.submarines == null) {
                this.submarines = new ArrayList<>();
            }
            this.submarines.add(submarine);
            return this;
        }


        public DeskBuilder room(Room room) {
            this.room = room;
            return this;
        }

        public DeskBuilder user(User user) {
            this.user = user;
            return this;
        }

//        public DeskBuilder roomId(UUID roomId) {
//            this.roomId = roomId;
//            return this;
//        }

        public Desk build() {
            return new Desk(null, battleship, cruisers, destroyers, submarines, room, user);
        }
    }
}
