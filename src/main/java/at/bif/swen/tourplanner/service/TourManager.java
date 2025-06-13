package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TransportType;
import at.bif.swen.tourplanner.repository.TourItemRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourManager {
    protected static final Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    private final ObservableList<TourItem> tourList = FXCollections.observableArrayList(
            new TourItem("City Tour", "A guided tour through the city's main attractions.", "Central Park", "City Museum", TransportType.CAR)
    );
    private final ObservableList<TourItem> filteredTourList = FXCollections.observableArrayList();
    private String lastSearchText = null;
    TourItemRepository repository;

    public TourManager() {
        searchTours(null);
    }

    @Autowired
    public TourManager(TourItemRepository repository) {
        this.repository = repository;

    }

    public void createTour(TourItem tour) {
        repository.save(tour);
        tourList.add(tour);
        searchTours(lastSearchText);
        logger.info("Created tour: {}", tour);
    }

    public void editTour(TourItem tour) {
        int index = tourList.indexOf(tour);
        tourList.set(index, tour);
        searchTours(lastSearchText);
        logger.info("Edited tour: {}", tour);
    }

    public void deleteTour(TourItem tour) {
        tourList.remove(tour);
        searchTours(lastSearchText);
        logger.info("Deleted tour: {}", tour);
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
