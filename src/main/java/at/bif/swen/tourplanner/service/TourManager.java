package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourManager {
    private final ObservableList<TourItem> tourList = FXCollections.observableArrayList(
            new TourItem("Default Route"),
            new TourItem("Tour 2")
    );

    public TourItem createTour(String name) {
        TourItem tour = new TourItem(name);
        tourList.add(tour);
        return tour;
    }

    public ObservableList<TourItem> getTourList() {
        return tourList;
    }
}
