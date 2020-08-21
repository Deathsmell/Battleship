package by.deathsmell.battleship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.util.*;
//import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Desk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection(targetClass = Point.class)
    private Set<Point> points;

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

        private Set<Point> points;

        private Room room;

        private User user;


        public DeskBuilder point(Point point){
            if (this.points == null){
                this.points = new HashSet<>();
            }
            this.points.add(point);
            return this;
        }

        public DeskBuilder points(Set<Point> points) {
            if (this.points == null){
                this.points = points;
            } else {
                this.points.addAll(points);
            }
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
            return new Desk(null, points, room, user);
        }


    }
}
