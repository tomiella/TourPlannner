package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourLog;
import at.bif.swen.tourplanner.model.TransportType;
import at.bif.swen.tourplanner.openRouteService.OpenRouteServiceAgent;
import at.bif.swen.tourplanner.repository.TourItemRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TourManager {

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
        log.info("Created tour: {}", tour);
    }

    public void editTour(TourItem tour) {
        this.routeService.getTourInformation(tour);
        repository.save(tour);
        log.info("Edited tour: {}", tour);
    }

    public void deleteTour(TourItem tour) {
        repository.delete(tour);
        log.info("Deleted tour: {}", tour);
    }

    public ObservableList<TourItem> searchTours(ObservableList<TourItem> tours, String searchText) {
        ObservableList<TourItem> filteredTourList = FXCollections.observableArrayList();
        if (searchText == null || searchText.isEmpty()) {
            filteredTourList.addAll(tours);
        } else {
            for (TourItem tour : tours) {
                if (tour.getName().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredTourList.add(tour);
                } else if (tour.getDescription().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredTourList.add(tour);
                }
            }
        }
        lastSearchText = searchText;
        return filteredTourList;
    }
}
