package at.bif.swen.tourplanner.repository;

import at.bif.swen.tourplanner.model.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourLogRepository extends JpaRepository<TourLog, Long> {
}
