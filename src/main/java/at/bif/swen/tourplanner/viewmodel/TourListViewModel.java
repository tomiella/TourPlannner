package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.service.TourManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TourListViewModel {
    private final TourManager tourManager;

    private final StringProperty searchText = new SimpleStringProperty("Test");

    private final PropertyChangeSupport tourCreatedEvent = new PropertyChangeSupport(this);

    public TourListViewModel(TourManager tourManager) {
        this.tourManager = tourManager;

        this.searchText.addListener((observable, oldValue, newValue) -> searchTours());
    }

    public void addTourCreatedEvent(PropertyChangeListener listener) {
        tourCreatedEvent.addPropertyChangeListener(listener);
    }

    public void addTour() {
        // TODO: add tours
    }

    public void searchTours() {
        // TODO: filter tours
    }

    public StringProperty searchTextProperty() {
        return searchText;
    }
}
