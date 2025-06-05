package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import org.springframework.stereotype.Component;

@Component
public class DetailsViewModel {
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty route = new SimpleStringProperty("");
    private final StringProperty transport = new SimpleStringProperty("");
    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();

    public void setSelected(TourItem tour) {
        if (tour == null) {
            name.set("");
            description.set("");
            route.set("");
            transport.set("");
            image.set(null);
            return;
        }
        name.set(tour.getName());
        description.set(tour.getDescription());
        route.set(tour.getFrom() + " → " + tour.getTo());
        transport.set(tour.getTransportType().toString());

        if (tour.getImagePath() != null && !tour.getImagePath().isBlank()) {
            image.set(new Image(tour.getImagePath()));
        } else {
            image.set(null);
        }
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

    public ObjectProperty<Image> imageProperty() {
        return image;
    }
}
