package at.bif.swen.tourplanner.utils;

import at.bif.swen.tourplanner.model.TourItem;

public class caloriesUtils {


    public static String getCaloriesAsString(TourItem tourItem) {
        if (tourItem.getTransportType() == null || tourItem.getDistance() == null || tourItem.getDistance() <= 0) {
            return "0 kcal";
        }

        final double WALKING_CALORIES_PER_KM = 0.05;
        final double BIKING_CALORIES_PER_KM = 0.03;
        double calories;

        switch (tourItem.getTransportType()) {
            case WALKING -> calories = tourItem.getDistance() * WALKING_CALORIES_PER_KM;
            case BIKE -> calories = tourItem.getDistance() * BIKING_CALORIES_PER_KM;
            default -> calories = 0;
        }

        return String.format("%.0f kcal", calories);
    }

}
