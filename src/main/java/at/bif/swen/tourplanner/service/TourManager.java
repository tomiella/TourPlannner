package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourLog;
import at.bif.swen.tourplanner.model.TransportType;
import at.bif.swen.tourplanner.openRouteService.OpenRouteServiceAgent;
import at.bif.swen.tourplanner.repository.TourItemRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourManager {
    protected static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(TourManager.class);

    private String lastSearchText = null;
    TourItemRepository repository;

    @Autowired
    private RouteService routeService;

    public TourManager() {
    }

    @Autowired
    public TourManager(TourItemRepository repository) {
        this.repository = repository;
    }


    public ObservableList<TourItem> loadTourItems() {
        return FXCollections.observableArrayList(repository.findAll());
    }

    public void createTour(TourItem tour) {
        this.routeService.getTourInformation(tour);
        System.out.println(tour.getEstimatedDuration());
        repository.save(tour);
        logger.info("Created tour: {}", tour);
    }

    public void editTour(TourItem tour) {
        this.routeService.getTourInformation(tour);
        repository.save(tour);
        logger.info("Edited tour: {}", tour);
    }

    public void deleteTour(TourItem tour) {
        repository.delete(tour);
        logger.info("Deleted tour: {}", tour);
    }

    public ObservableList<TourItem> searchTours(ObservableList<TourItem> tours, String searchText) {
        ObservableList<TourItem> filteredTourList = FXCollections.observableArrayList();
        if (searchText == null || searchText.isEmpty()) {
            filteredTourList.addAll(tours);
        } else {
            for (TourItem tour : tours) {
                if (tour.getName().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredTourList.add(tour);
                }
            }
        }
        lastSearchText = searchText;
        return filteredTourList;
    }
}
