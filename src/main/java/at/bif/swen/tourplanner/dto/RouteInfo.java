package at.bif.swen.tourplanner.dto;

import java.time.Duration;

public class RouteInfo {

    public final double distance;
    public final long duration;

    public RouteInfo(double distance, long durationInSeconds) {
        this.distance = distance;
        this.duration = durationInSeconds;
        System.out.println("here is the duration" + this.duration);

    }

    public long getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }
}
