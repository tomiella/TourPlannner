package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourLog;
import at.bif.swen.tourplanner.model.TransportType;
import at.bif.swen.tourplanner.repository.TourLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetailsServiceTest {

    @Mock
    private TourLogRepository tourLogRepository;

    private DetailsService detailsService;

    private TourItem testTourItem;
    private TourItem anotherTourItem;

    @BeforeEach
    void setUp() {
        detailsService = new DetailsService();

        try {
            java.lang.reflect.Field repositoryField = DetailsService.class.getDeclaredField("tourLogRepository");
            repositoryField.setAccessible(true);
            repositoryField.set(detailsService, tourLogRepository);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock repository", e);
        }

        testTourItem = new TourItem("Test Tour", "Test Description", "Vienna", "Salzburg", TransportType.WALKING, 5000.0, 3600);
        anotherTourItem = new TourItem("Another Tour", "Another Description", "Graz", "Linz", TransportType.CAR, 8000.0, 7200);
    }



    @Test
    @DisplayName("Should return 0 for tour with no logs")
    void testCalculatePopularity_WithNoLogs() {
        when(tourLogRepository.findAll()).thenReturn(Collections.emptyList());

        String result = detailsService.calculatePopularity(testTourItem);

        assertEquals("0", result);
        verify(tourLogRepository).findAll();
    }

    @Test
    @DisplayName("Should return 0 for tour with 1 log")
    void testCalculatePopularity_WithOneLog() {
        TourLog log = new TourLog(new Date(), "Test log", 3, 4, 120, testTourItem);
        when(tourLogRepository.findAll()).thenReturn(Arrays.asList(log));

        String result = detailsService.calculatePopularity(testTourItem);

        assertEquals("0", result);
    }

    @Test
    @DisplayName("Should return 1 for tour with 2 logs")
    void testCalculatePopularity_WithTwoLogs() {
        TourLog log1 = new TourLog(new Date(), "Test log 1", 3, 4, 120, testTourItem);
        TourLog log2 = new TourLog(new Date(), "Test log 2", 2, 5, 90, testTourItem);
        when(tourLogRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        String result = detailsService.calculatePopularity(testTourItem);

        assertEquals("1", result);
    }

    @Test
    @DisplayName("Should return 1 for tour with 3 logs")
    void testCalculatePopularity_WithThreeLogs() {
        TourLog log1 = new TourLog(new Date(), "Test log 1", 3, 4, 120, testTourItem);
        TourLog log2 = new TourLog(new Date(), "Test log 2", 2, 5, 90, testTourItem);
        TourLog log3 = new TourLog(new Date(), "Test log 3", 4, 3, 150, testTourItem);
        when(tourLogRepository.findAll()).thenReturn(Arrays.asList(log1, log2, log3));

        String result = detailsService.calculatePopularity(testTourItem);

        assertEquals("1", result);
    }

    @Test
    @DisplayName("Should return 2 for tour with 4 logs")
    void testCalculatePopularity_WithFourLogs() {
        TourLog log1 = new TourLog(new Date(), "Test log 1", 3, 4, 120, testTourItem);
        TourLog log2 = new TourLog(new Date(), "Test log 2", 2, 5, 90, testTourItem);
        TourLog log3 = new TourLog(new Date(), "Test log 3", 4, 3, 150, testTourItem);
        TourLog log4 = new TourLog(new Date(), "Test log 4", 1, 5, 60, testTourItem);
        when(tourLogRepository.findAll()).thenReturn(Arrays.asList(log1, log2, log3, log4));

        String result = detailsService.calculatePopularity(testTourItem);

        assertEquals("2", result);
    }

    @Test
    @DisplayName("Should return 2 for tour with 5 logs")
    void testCalculatePopularity_WithFiveLogs() {
        List<TourLog> logs = Arrays.asList(
            new TourLog(new Date(), "Test log 1", 3, 4, 120, testTourItem),
            new TourLog(new Date(), "Test log 2", 2, 5, 90, testTourItem),
            new TourLog(new Date(), "Test log 3", 4, 3, 150, testTourItem),
            new TourLog(new Date(), "Test log 4", 1, 5, 60, testTourItem),
            new TourLog(new Date(), "Test log 5", 3, 4, 100, testTourItem)
        );
        when(tourLogRepository.findAll()).thenReturn(logs);

        String result = detailsService.calculatePopularity(testTourItem);

        assertEquals("2", result);
    }

    @Test
    @DisplayName("Should return 3 for tour with 6 logs")
    void testCalculatePopularity_WithSixLogs() {
        List<TourLog> logs = Arrays.asList(
            new TourLog(new Date(), "Test log 1", 3, 4, 120, testTourItem),
            new TourLog(new Date(), "Test log 2", 2, 5, 90, testTourItem),
            new TourLog(new Date(), "Test log 3", 4, 3, 150, testTourItem),
            new TourLog(new Date(), "Test log 4", 1, 5, 60, testTourItem),
            new TourLog(new Date(), "Test log 5", 3, 4, 100, testTourItem),
            new TourLog(new Date(), "Test log 6", 2, 5, 80, testTourItem)
        );
        when(tourLogRepository.findAll()).thenReturn(logs);

        String result = detailsService.calculatePopularity(testTourItem);

        assertEquals("3", result);
    }





    @Test
    @DisplayName("Should return 5 for tour with more than 10 logs")
    void testCalculatePopularity_WithManyLogs() {
        List<TourLog> logs = Arrays.asList(
            new TourLog(new Date(), "Test log 1", 3, 4, 120, testTourItem),
            new TourLog(new Date(), "Test log 2", 2, 5, 90, testTourItem),
            new TourLog(new Date(), "Test log 3", 4, 3, 150, testTourItem),
            new TourLog(new Date(), "Test log 4", 1, 5, 60, testTourItem),
            new TourLog(new Date(), "Test log 5", 3, 4, 100, testTourItem),
            new TourLog(new Date(), "Test log 6", 2, 5, 80, testTourItem),
            new TourLog(new Date(), "Test log 7", 4, 3, 140, testTourItem),
            new TourLog(new Date(), "Test log 8", 1, 5, 70, testTourItem),
            new TourLog(new Date(), "Test log 9", 3, 4, 110, testTourItem),
            new TourLog(new Date(), "Test log 10", 2, 5, 85, testTourItem),
            new TourLog(new Date(), "Test log 11", 4, 3, 160, testTourItem),
            new TourLog(new Date(), "Test log 12", 1, 5, 75, testTourItem)
        );
        when(tourLogRepository.findAll()).thenReturn(logs);

        String result = detailsService.calculatePopularity(testTourItem);

        assertEquals("5", result);
    }

    @Test
    @DisplayName("Should filter logs for specific tour only")
    void testCalculatePopularity_FiltersByTour() {
        TourLog logForTestTour = new TourLog(new Date(), "Test log", 3, 4, 120, testTourItem);
        TourLog logForAnotherTour = new TourLog(new Date(), "Another log", 2, 5, 90, anotherTourItem);
        when(tourLogRepository.findAll()).thenReturn(Arrays.asList(logForTestTour, logForAnotherTour));

        String result = detailsService.calculatePopularity(testTourItem);

        assertEquals("0", result);
    }



    @Test
    @DisplayName("Should return Easy for child-friendly tour")
    void testCalculateChildFriendliness_Easy() {
        // Easy criteria: avgDifficulty <= 1.8, avgDuration <= 90, distance <= 5000.0
        TourLog log1 = new TourLog(new Date(), "Easy log 1", 1, 5, 60, testTourItem); // difficulty=1, duration=60
        TourLog log2 = new TourLog(new Date(), "Easy log 2", 2, 4, 90, testTourItem); // difficulty=2, duration=90
        testTourItem.setDistance(4000.0); // distance=4000m
        
        when(tourLogRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        String result = detailsService.calculateChildFriendliness(testTourItem);

        assertEquals("Easy", result);
    }

    @Test
    @DisplayName("Should return Moderate for moderately difficult tour")
    void testCalculateChildFriendliness_Moderate() {
        // Moderate criteria: avgDifficulty <= 3.0, avgDuration <= 180, distance <= 10000.0
        TourLog log1 = new TourLog(new Date(), "Moderate log 1", 3, 3, 120, testTourItem); // difficulty=3, duration=120
        TourLog log2 = new TourLog(new Date(), "Moderate log 2", 2, 4, 180, testTourItem); // difficulty=2, duration=180
        testTourItem.setDistance(8000.0); // distance=8000m
        
        when(tourLogRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        String result = detailsService.calculateChildFriendliness(testTourItem);

        assertEquals("Moderate", result);
    }

    @Test
    @DisplayName("Should return Hard for difficult tour")
    void testCalculateChildFriendliness_Hard() {
        // Hard: exceeds moderate criteria
        TourLog log1 = new TourLog(new Date(), "Hard log 1", 4, 2, 200, testTourItem); // difficulty=4, duration=200
        TourLog log2 = new TourLog(new Date(), "Hard log 2", 5, 1, 240, testTourItem); // difficulty=5, duration=240
        testTourItem.setDistance(15000.0); // distance=15000m
        
        when(tourLogRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        String result = detailsService.calculateChildFriendliness(testTourItem);

        assertEquals("Hard", result);
    }





    @Test
    @DisplayName("Should filter logs for specific tour only in child friendliness")
    void testCalculateChildFriendliness_FiltersByTour() {
        TourLog logForTestTour = new TourLog(new Date(), "Test log", 1, 5, 60, testTourItem);
        TourLog logForAnotherTour = new TourLog(new Date(), "Another log", 5, 1, 300, anotherTourItem);
        testTourItem.setDistance(3000.0);
        
        when(tourLogRepository.findAll()).thenReturn(Arrays.asList(logForTestTour, logForAnotherTour));

        String result = detailsService.calculateChildFriendliness(testTourItem);

        assertEquals("Easy", result); // Only considers logForTestTour
    }





} 