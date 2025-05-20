package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TransportType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourManager {
    private final ObservableList<TourItem> tourList = FXCollections.observableArrayList(
            new TourItem("City Tour", "A guided tour through the city's main attractions.", "Central Park", "City Museum", TransportType.CAR)
    );
    private final ObservableList<TourItem> filteredTourList = FXCollections.observableArrayList();

    private String lastSearchText = null;

    public TourManager() {
        searchTours(null);
    }

    public void createTour(TourItem tour) {
        tourList.add(tour);
        searchTours(lastSearchText);
    }

    public void editTour(TourItem tour) {
        int index = tourList.indexOf(tour);
        tourList.set(index, tour);
        searchTours(lastSearchText);
    }

    public void deleteTour(TourItem tour) {
        tourList.remove(tour);
        searchTours(lastSearchText);
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
