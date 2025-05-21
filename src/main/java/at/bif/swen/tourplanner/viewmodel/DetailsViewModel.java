package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DetailsViewModel {
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty route = new SimpleStringProperty("");
    private final StringProperty transport = new SimpleStringProperty("");

    public void setSelected(TourItem tour) {
        if (tour == null) {
           return;
        }
        name.set(tour.getName());
        description.set(tour.getDescription());
        route.set(tour.getFrom() + " → " + tour.getTo());
        transport.set(tour.getTransportType().toString());

    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty routeProperty() {
        return route;
    }

    public StringProperty transportProperty() {
        return transport;
    }
}
