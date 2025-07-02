package at.bif.swen.tourplanner.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class TourLog {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private Date datetime;
    private String comment;
    private int difficulty;
    private int rating;
    private int duration;

    @ManyToOne
    private TourItem route;

    public TourLog(Date datetime, String comment, int difficulty, int rating, int duration, TourItem route) {
        this.datetime = datetime;
        this.comment = comment;
        this.difficulty = difficulty;
        this.rating = rating;
        this.duration = duration;
        this.route = route;
    }
    public TourLog(String comment) {
        this.comment = comment;
    }

}