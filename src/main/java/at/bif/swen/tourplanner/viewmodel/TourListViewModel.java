package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.service.TourManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

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
        Dialog<TourItem> dialog = new Dialog<>();
        dialog.setTitle("Add Tour");
        dialog.setHeaderText("Add a new tour");

        ButtonType addType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Tour name");
        TextField descField = new TextField();
        descField.setPromptText("Description");

        grid.add(new Label("Name:"),    0, 0);
        grid.add(nameField,            1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descField,            1, 1);

        dialog.getDialogPane().setContent(grid);

        Node addButtonNode = dialog.getDialogPane().lookupButton(addType);
        addButtonNode.setDisable(true);
        nameField.textProperty().addListener((obs, oldVal, newVal) ->
                addButtonNode.setDisable(newVal.trim().isEmpty())
        );

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addType) {
                TourItem newTour = new TourItem(nameField.getText());
                newTour.setDescription(descField.getText());
                return newTour;
            }
            return null;
        });

        Optional<TourItem> result = dialog.showAndWait();

        result.ifPresent(tour -> {
            tourManager.createTour(tour.getName());
        });
    }

    public ObservableList<TourItem> getTourList() {
        return tourManager.getTourList();
    }

    public void searchTours() {
        // TODO: filter tours
    }

    public StringProperty searchTextProperty() {
        return searchText;
    }
}
