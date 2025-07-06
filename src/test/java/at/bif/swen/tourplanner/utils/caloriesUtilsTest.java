package at.bif.swen.tourplanner.utils;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TransportType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class caloriesUtilsTest {

    @Test
    @DisplayName("Should return 0 kcal when transport type is null")
    void testGetCaloriesAsString_WithNullTransportType() {
        TourItem tourItem = new TourItem("Test Tour");
        tourItem.setTransportType(null);
        tourItem.setDistance(100.0);

        String result = caloriesUtils.getCaloriesAsString(tourItem);

        assertEquals("0 kcal", result);
    }

    @Test
    @DisplayName("Should return 0 kcal when distance is null")
    void testGetCaloriesAsString_WithNullDistance() {
        TourItem tourItem = new TourItem("Test Tour");
        tourItem.setTransportType(TransportType.WALKING);
        tourItem.setDistance(null);

        String result = caloriesUtils.getCaloriesAsString(tourItem);

        assertEquals("0 kcal", result);
    }

    @Test
    @DisplayName("Should return 0 kcal when distance is zero")
    void testGetCaloriesAsString_WithZeroDistance() {
        TourItem tourItem = new TourItem("Test Tour");
        tourItem.setTransportType(TransportType.WALKING);
        tourItem.setDistance(0.0);

        String result = caloriesUtils.getCaloriesAsString(tourItem);

        assertEquals("0 kcal", result);
    }

    @Test
    @DisplayName("Should return 0 kcal when distance is negative")
    void testGetCaloriesAsString_WithNegativeDistance() {
        TourItem tourItem = new TourItem("Test Tour");
        tourItem.setTransportType(TransportType.WALKING);
        tourItem.setDistance(-10.0);

        String result = caloriesUtils.getCaloriesAsString(tourItem);

        assertEquals("0 kcal", result);
    }

    @Test
    @DisplayName("Should calculate calories correctly for walking")
    void testGetCaloriesAsString_ForWalking() {
        TourItem tourItem = new TourItem("Walking Tour");
        tourItem.setTransportType(TransportType.WALKING);
        tourItem.setDistance(1000.0); // 10 km

        String result = caloriesUtils.getCaloriesAsString(tourItem);


        assertEquals("50 kcal", result);
    }

    @Test
    @DisplayName("Should calculate calories correctly for biking")
    void testGetCaloriesAsString_ForBiking() {
        TourItem tourItem = new TourItem("Biking Tour");
        tourItem.setTransportType(TransportType.BIKE);
        tourItem.setDistance(2000.0);

        String result = caloriesUtils.getCaloriesAsString(tourItem);


        assertEquals("60 kcal", result);
    }

    @Test
    @DisplayName("Should return 0 kcal for car transport")
    void testGetCaloriesAsString_ForCar() {
        TourItem tourItem = new TourItem("Car Tour");
        tourItem.setTransportType(TransportType.CAR);
        tourItem.setDistance(50.0);

        String result = caloriesUtils.getCaloriesAsString(tourItem);

        assertEquals("0 kcal", result);
    }



    @Test
    @DisplayName("Should handle large distances for walking")
    void testGetCaloriesAsString_ForLargeWalkingDistance() {
        TourItem tourItem = new TourItem("Long Walking Tour");
        tourItem.setTransportType(TransportType.WALKING);
        tourItem.setDistance(5000.0);

        String result = caloriesUtils.getCaloriesAsString(tourItem);


        assertEquals("250 kcal", result);
    }

    @Test
    @DisplayName("Should handle large distances for biking")
    void testGetCaloriesAsString_ForLargeBikingDistance() {
        TourItem tourItem = new TourItem("Long Biking Tour");
        tourItem.setTransportType(TransportType.BIKE);
        tourItem.setDistance(200.0);

        String result = caloriesUtils.getCaloriesAsString(tourItem);


        assertEquals("6 kcal", result);
    }

    @Test
    @DisplayName("Should handle decimal distances for walking")
    void testGetCaloriesAsString_ForDecimalWalkingDistance() {
        TourItem tourItem = new TourItem("Short Walking Tour");
        tourItem.setTransportType(TransportType.WALKING);
        tourItem.setDistance(2.5);

        String result = caloriesUtils.getCaloriesAsString(tourItem);


        assertEquals("0 kcal", result);
    }



    @Test
    @DisplayName("Should round up calories correctly")
    void testGetCaloriesAsString_WithRoundingUp() {
        TourItem tourItem = new TourItem("Walking Tour");
        tourItem.setTransportType(TransportType.WALKING);
        tourItem.setDistance(15.0);

        String result = caloriesUtils.getCaloriesAsString(tourItem);


        assertEquals("1 kcal", result);
    }


} 