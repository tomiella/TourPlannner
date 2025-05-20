package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourItemList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainViewModel {
    private TourItemList tourItemList = new TourItemList();
    private TourItem tourItem = new TourItem("Default Route");

    private final StringProperty newRouteName = new SimpleStringProperty("Default Route");
    private final StringProperty routesSummary = new SimpleStringProperty("No routes yet");

    public MainViewModel() {
        newRouteName.addListener((observable, oldValue, newValue) -> {
            tourItem = new TourItem(newValue);
        });
    }

    public StringProperty newRouteNameProperty() {
        return newRouteName;
    }

    public void createNewRoute() {
        tourItemList.setRoute(tourItem);
        routesSummaryProperty().setValue(tourItemList.toString());

        newRouteNameProperty().setValue("");
    }

    public StringProperty routesSummaryProperty() {
        return routesSummary;
    }

    public TourItemList getRouteItemList() {
        return tourItemList;
    }

    public void setRouteItemList(TourItemList tourItemList) {
        this.tourItemList = tourItemList;
    }
}
