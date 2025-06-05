package at.bif.swen.tourplanner.repository;


import at.bif.swen.tourplanner.model.TourItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourItemRepository extends JpaRepository<TourItem, Long> {
}
