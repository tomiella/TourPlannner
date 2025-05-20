package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourManager {
    private final ObservableList<TourItem> tourList = FXCollections.observableArrayList(
            new TourItem("Default Route"),
            new TourItem("Tour 2")
    );
    private final ObservableList<TourItem> filteredTourList = FXCollections.observableArrayList();

    private String lastSearchText = null;

    public TourManager() {
        searchTours(null);
    }

    public void createTour(String name) {
        TourItem tour = new TourItem(name);
        tourList.add(tour);
        searchTours(lastSearchText);
    }

    public void editTour(TourItem tour) {
        int index = tourList.indexOf(tour);
        tourList.set(index, tour);
    }

    public void searchTours(String searchText) {
        filteredTourList.clear();
        if (searchText == null || searchText.isEmpty()) {
            filteredTourList.addAll(tourList);
        } else {
            for (TourItem tour : tourList) {
                if (tour.getName().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredTourList.add(tour);
                }
            }
        }
        lastSearchText = searchText;
    }

    public ObservableList<TourItem> getTourList() {
        return filteredTourList;
    }
}
