package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourLog;
import at.bif.swen.tourplanner.repository.TourItemRepository;
import at.bif.swen.tourplanner.repository.TourLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class DetailsService {

    @Autowired
    private TourLogRepository tourLogRepository;

    public String calculatePopularity(TourItem tourItem) {
        List<TourLog> logs = tourLogRepository.findAll().stream().filter(l -> l.getRoute().equals(tourItem)).toList();
        int size = logs.size();

        if (size <=1) {
            return "0";
        } else if (size <= 3) {
            return "1";
        } else if (size <= 5) {
            return "2";
        } else if (size <= 7) {
            return "3";
        } else if (size <= 9) {
            return "4";
        } else {
            return "5";
        }
    }

    public String calculateChildFriendliness(TourItem tourItem) {
        List<TourLog> logs = tourLogRepository.findAll().stream().filter(l -> l.getRoute().equals(tourItem)).toList();

        double avgDifficulty = logs.stream().mapToInt(TourLog::getDifficulty).average().orElse(0.0);
        double avgDuration = logs.stream().mapToInt(TourLog::getDuration).average().orElse(0);
        double distance = tourItem.getDistance();

        boolean easy = avgDifficulty <= 1.8 && avgDuration <= 90 && distance <= 5000.0;
        boolean moderate = avgDifficulty <= 3.0 && avgDuration <= 180 && distance <= 10000.0;

        if (easy) {
            return "Easy";
        } else if (moderate) {
            return "Moderate";
        } else {
            return "Hard";
        }
    }
}
