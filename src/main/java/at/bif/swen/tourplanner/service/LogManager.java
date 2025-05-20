package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;
import java.util.Observer;

public class LogManager {

    private final ObservableList<TourLog> logList = FXCollections.observableArrayList(
    );

    private TourManager tourManager;


    public ObservableList<TourLog> getLogList() {
        return logList;
    }

    public void createTour(Date datetime, String comment, int difficulty, int rating, int duration) {
        TourLog tour = new TourLog(datetime, comment, difficulty,rating,duration);
        logList.add(tour);

    }

    public void editTour(TourLog tour) {
        int index = logList.indexOf(tour);
        logList.set(index, tour);
    }
}
