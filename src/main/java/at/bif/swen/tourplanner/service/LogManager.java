package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourLog;
import at.bif.swen.tourplanner.repository.TourItemRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Observer;

@Service
public class LogManager {
    protected static final Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    TourItemRepository repository;
    private final ObservableList<TourLog> logList = FXCollections.observableArrayList();
    private final ObservableList<TourLog> filteredTourLog = FXCollections.observableArrayList();

    private TourItem selectedTour = null;

    private TourManager tourManager;

    public ObservableList<TourLog> getLogList() {
        return filteredTourLog;
    }



    public void createTour(Date datetime, String comment, int difficulty, int rating, int duration, TourItem tour) {
        TourLog tourLog = new TourLog(datetime, comment, difficulty,rating,duration, tour);
        logList.add(tourLog);
        setSelectedTour(tour);
        logger.info("Created tour log: {}", tourLog.getId());
    }

    public void editTour(TourLog tour) {
        int index = logList.indexOf(tour);
        logList.set(index, tour);
        setSelectedTour(selectedTour);
        logger.info("Edited tour log: {}", tour.getId());
    }

    public void deleteLog(TourLog tour){
        logList.remove(tour);
        setSelectedTour(selectedTour);
        logger.info("Deleted tour log: {}", tour.getId());
    }

    public void setSelectedTour(TourItem tour) {
        System.out.println("Hashcode: " + this.hashCode());
        filteredTourLog.clear();
        if (tour != null) {
            for (TourLog log : logList) {
                if (log.getRoute().equals(tour)) {
                    filteredTourLog.add(log);
                }
            }
            selectedTour = tour;
        }
    }
}
