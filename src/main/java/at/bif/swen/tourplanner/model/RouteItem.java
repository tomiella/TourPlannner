package at.bif.swen.tourplanner.model;

public record RouteItem(String name) {
    @Override
    public String toString() {
        return name;
    }
}
