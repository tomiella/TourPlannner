package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.dto.TourItemLogs;
import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourLog;
import at.bif.swen.tourplanner.repository.TourItemRepository;
import at.bif.swen.tourplanner.repository.TourLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ExportService {

    @Autowired
    private TourItemRepository tourItemRepository;

    @Autowired
    private TourLogRepository tourLogRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    public void exportTour(String path, TourItem tourItem) throws IOException {
        log.info("Exporting {}", tourItem.getName());
        List<TourLog> tourLogs = tourLogRepository.findAll().stream().filter(l -> l.getRoute().getId() == tourItem.getId()).toList();
        TourItemLogs tourItemLogs = new TourItemLogs(tourItem, tourLogs);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), tourItemLogs);
    }

    public void importTour(String path) throws IOException {
        log.info("Importing {}", path);
        TourItemLogs tourItemLogs = mapper.readValue(new File(path), TourItemLogs.class);
        log.info(tourItemLogs.getTourItem().toPrettyString());
        tourItemLogs.getTourItem().setId(null);
        TourItem tour = tourItemRepository.save(tourItemLogs.getTourItem());
        for (TourLog tourLog : tourItemLogs.getTourLogs()) {
            tourLog.setId(null);
            tourLog.setRoute(tour);
        }
        tourLogRepository.saveAll(tourItemLogs.getTourLogs());
    }
}