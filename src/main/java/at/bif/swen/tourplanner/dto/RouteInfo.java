package at.bif.swen.tourplanner.dto;

import java.time.Duration;

public class RouteInfo {

    public final double distance;
    public final Duration duration;

    public RouteInfo(double distance, double durationInSeconds) {
        this.distance = distance;
        this.duration = Duration.ofSeconds((long) durationInSeconds);
    }

    public Duration getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }
}
