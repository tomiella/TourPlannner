package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.repository.TourItemRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TourManagerTest {

    @Mock
    private TourItemRepository tourItemRepository;

    @Mock
    private DetailsService detailsService;

    @Mock
    private RouteService routeService;

    @InjectMocks
    private TourManager tourManager;

    private TourItem tour;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tour = new TourItem();
        tour.setId(1);
        tour.setName("Alpen Wanderung");
        tour.setDescription("Schöne Wanderung durch die Alpen");
        tour.setDistance(8000.0);
    }

    @Test
    void testLoadTourItems() {
        when(tourItemRepository.findAll()).thenReturn(List.of(tour));

        ObservableList<TourItem> result = tourManager.loadTourItems();

        assertEquals(1, result.size());
        assertEquals(tour, result.get(0));
    }


    @Test
    void testDeleteTour_CallsRepositoryDelete() {
        tourManager.deleteTour(tour);

        verify(tourItemRepository).delete(tour);
    }

    @Test
    void testSearchTours_ByName() {
        tour.setName("Salzburg Trail");

        ObservableList<TourItem> tours = FXCollections.observableArrayList(tour);

        ObservableList<TourItem> result = tourManager.searchTours(tours, "salzburg");

        assertEquals(1, result.size());
        assertEquals(tour, result.get(0));
    }

    @Test
    void testSearchTours_ByDescription() {
        tour.setDescription("Bergwanderung");

        ObservableList<TourItem> tours = FXCollections.observableArrayList(tour);

        ObservableList<TourItem> result = tourManager.searchTours(tours, "berg");

        assertEquals(1, result.size());
    }


    @Test
    void testSearchTours_ByChildFriendliness() {
        when(detailsService.calculateChildFriendliness(tour)).thenReturn("Moderate");

        ObservableList<TourItem> tours = FXCollections.observableArrayList(tour);

        ObservableList<TourItem> result = tourManager.searchTours(tours, "moderate");

        assertEquals(1, result.size());
    }

    @Test
    void testSearchTours_EmptySearch_ReturnsAll() {
        ObservableList<TourItem> tours = FXCollections.observableArrayList(tour);

        ObservableList<TourItem> result = tourManager.searchTours(tours, "");

        assertEquals(1, result.size());
        assertEquals(tour, result.get(0));
    }

    @Test
    void testSearchTours_NoMatch_ReturnsEmpty() {
        ObservableList<TourItem> tours = FXCollections.observableArrayList(tour);

        when(detailsService.calculatePopularity(tour)).thenReturn("2");
        when(detailsService.calculateChildFriendliness(tour)).thenReturn("Hard");

        ObservableList<TourItem> result = tourManager.searchTours(tours, "xyz");

        assertTrue(result.isEmpty());
    }
}