package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.RouteItem;
import at.bif.swen.tourplanner.model.RouteItemList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainViewModel {
    private RouteItemList routeItemList = new RouteItemList();
    private RouteItem routeItem = new RouteItem("Default Route");

    private final StringProperty newRouteName = new SimpleStringProperty("Default Route");
    private final StringProperty routesSummary = new SimpleStringProperty("No routes yet");

    public MainViewModel() {
        newRouteName.addListener((observable, oldValue, newValue) -> {
            routeItem = new RouteItem(newValue);
        });
    }

    public StringProperty newRouteNameProperty() {
        return newRouteName;
    }

    public void createNewRoute() {
        routeItemList.setRoute(routeItem);
        routesSummaryProperty().setValue(routeItemList.toString());

        newRouteNameProperty().setValue("");
    }

    public StringProperty routesSummaryProperty() {
        return routesSummary;
    }

    public RouteItemList getRouteItemList() {
        return routeItemList;
    }

    public void setRouteItemList(RouteItemList routeItemList) {
        this.routeItemList = routeItemList;
    }
}
