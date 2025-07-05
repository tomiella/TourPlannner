package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourLog;
import at.bif.swen.tourplanner.repository.TourItemRepository;
import at.bif.swen.tourplanner.repository.TourLogRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Observer;

@Service
@Log4j2
public class LogManager {

    private String lastSearchText = null;

    private TourManager tourManager;

    TourLogRepository repository;

    @Autowired
    public LogManager(TourLogRepository repository) {
        this.repository = repository;
    }

    public ObservableList<TourLog> searchLogs(ObservableList<TourLog> logs, String searchText) {
        ObservableList<TourLog> filteredTourLogs = FXCollections.observableArrayList();
        if (searchText == null || searchText.isEmpty()) {
            filteredTourLogs.addAll(logs);
        } else {
            for (TourLog log : logs) {
                // TODO: add all filter criteriums
                if (log.getComment().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredTourLogs.add(log);
                }
            }
        }
        lastSearchText = searchText;
        return filteredTourLogs;
    }

    public ObservableList<TourLog> loadLogItems() {
        return FXCollections.observableArrayList(repository.findAll());
    }

    public void createTour(Date datetime, String comment, int difficulty, int rating, int duration, TourItem tour) {
        TourLog tourLog = new TourLog(datetime, comment, difficulty,rating,duration, tour);
        repository.save(tourLog);
        log.info("Created tour log: {}", tourLog.getId());
    }

    public void editTour(TourLog tour) {
        repository.save(tour);
        log.info("Edited tour log: {}", tour.getId());
    }

    public void deleteLog(TourLog tour){
        repository.save(tour);
        log.info("Deleted tour log: {}", tour.getId());
    }
}
