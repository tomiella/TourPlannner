package at.bif.swen.tourplanner.model;

import java.util.Date;

public class TourLog {
    private Date datetime;
    private String comment;
    private int difficulty;
    private int rating;
    private int duration;
    private TourItem route;

    public TourLog(Date datetime, String comment, int difficulty, int rating, int duration, TourItem route) {
        this.datetime = datetime;
        this.comment = comment;
        this.difficulty = difficulty;
        this.rating = rating;
        this.duration = duration;
        this.route = route;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public TourItem getRoute() {
        return route;
    }

    public void setRoute(TourItem route) {
        this.route = route;
    }
}