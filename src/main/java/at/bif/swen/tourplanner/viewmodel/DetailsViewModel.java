package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.service.DetailsService;
import at.bif.swen.tourplanner.utils.caloriesUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import org.springframework.stereotype.Component;
import at.bif.swen.tourplanner.utils.formatUtils;

@Component
public class DetailsViewModel {
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty route = new SimpleStringProperty("");
    private final StringProperty transport = new SimpleStringProperty("");
    private final StringProperty duration = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty child = new SimpleStringProperty("");
    private final StringProperty popularity = new SimpleStringProperty("");
    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();
    private final StringProperty calories = new SimpleStringProperty("");

    private final DetailsService detailsService;

    public DetailsViewModel(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    public void setSelected(TourItem tour) {
        if (tour == null) {
            name.set("");
            description.set("");
            route.set("");
            transport.set("");
            duration.set("");
            distance.set("");
            child.set("");
            popularity.set("");
            calories.set("");
            image.set(null);
            return;
        }
        name.set(tour.getName());
        description.set(tour.getDescription());
        route.set(tour.getFrom() + " → " + tour.getTo());
        transport.set(tour.getTransportType().toString());
        duration.set(formatUtils.formatTime(tour.getEstimatedDuration()));
        distance.set(formatUtils.formatDistance(tour.getDistance()));
        calories.set(caloriesUtils.getCaloriesAsString(tour));
        popularity.set(detailsService.calculatePopularity(tour));
        child.set(detailsService.calculateChildFriendliness(tour));


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

    public StringProperty durationProperty() {
        return duration;
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public StringProperty childProperty() {
        return child;
    }

    public StringProperty popularityProperty() {
        return popularity;
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public StringProperty caloriesProperty() {
        return calories;
    }
}
