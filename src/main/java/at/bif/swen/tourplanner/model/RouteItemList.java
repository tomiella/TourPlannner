package at.bif.swen.tourplanner.model;

import java.util.ArrayList;
import java.util.List;

public class RouteItemList {
    private final List<RouteItem> routes = new ArrayList<>();

    public RouteItemList() {

    }

    public List<RouteItem> getRoutes() {
        return routes;
    }

    public void setRoute(RouteItem route) {
        routes.add(route);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (RouteItem route : routes) {
            result.append(route.toString()).append("\n");
        }

        return result.toString();
    }
}
