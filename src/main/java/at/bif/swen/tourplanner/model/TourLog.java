package at.bif.swen.tourplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "logs")
public class TourLog {

    @Id
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