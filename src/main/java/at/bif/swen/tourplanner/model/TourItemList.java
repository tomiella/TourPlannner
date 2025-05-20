package at.bif.swen.tourplanner.model;

import java.util.ArrayList;
import java.util.List;

public class TourItemList {
    private final List<TourItem> routes = new ArrayList<>();

    public TourItemList() {

    }

    public List<TourItem> getRoutes() {
        return routes;
    }

    public void setRoute(TourItem route) {
        routes.add(route);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (TourItem route : routes) {
            result.append(route.toString()).append("\n");
        }

        return result.toString();
    }
}
