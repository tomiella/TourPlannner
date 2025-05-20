package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TransportType;
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

    private final StringProperty searchText = new SimpleStringProperty("");

    private final PropertyChangeSupport tourCreatedEvent = new PropertyChangeSupport(this);

    public TourListViewModel(TourManager tourManager) {
        this.tourManager = tourManager;

        this.searchText.addListener((observable, oldValue, newValue) -> searchTours());
    }

    public void addTour() {
        TourItem result = showTourDialog(null);

        if (result != null) {
            tourManager.createTour(result.getName());
        }
    }

    public void editTour(TourItem tour) {
        TourItem result = showTourDialog(tour);

        if (result != null) {
            tourManager.editTour(result);
        }
    }

    public ObservableList<TourItem> getTourList() {
        return tourManager.getTourList();
    }

    public void searchTours() {
        tourManager.searchTours(searchText.get());
    }

    public StringProperty searchTextProperty() {
        return searchText;
    }

    private TourItem showTourDialog(TourItem existing) {
        boolean isEdit = existing != null;
        Dialog<TourItem> dialog = new Dialog<>();
        dialog.setTitle(isEdit ? "Edit Tour" : "Add New Tour");
        dialog.setHeaderText(isEdit
                ? "Modify the details of the tour"
                : "Enter details for the new tour");

        ButtonType saveType = new ButtonType(isEdit ? "Save" : "Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField      = new TextField();
        TextArea  descArea       = new TextArea();
        TextField fromField      = new TextField();
        TextField toField        = new TextField();
        ComboBox<TransportType> typeCombo = new ComboBox<>();

        typeCombo.getItems().setAll(TransportType.values());

        if (isEdit) {
            nameField.setText(existing.getName());
            descArea.setText(existing.getDescription());
            fromField.setText(existing.getFrom());
            toField.setText(existing.getTo());
            typeCombo.setValue(existing.getTransportType());
        }

        nameField.setPromptText("Name");
        descArea.setPromptText("Description");
        fromField.setPromptText("From");
        toField.setPromptText("To");
        typeCombo.setPromptText("Transport");

        grid.add(new Label("Name:"),        0, 0);
        grid.add(nameField,                 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descArea,                  1, 1);
        grid.add(new Label("From:"),        0, 2);
        grid.add(fromField,                 1, 2);
        grid.add(new Label("To:"),          0, 3);
        grid.add(toField,                   1, 3);
        grid.add(new Label("Transport:"),   0, 4);
        grid.add(typeCombo,                 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Validation: require name, from, to, transport
        Node okButton = dialog.getDialogPane().lookupButton(saveType);
        Runnable validate = () -> {
            boolean disable = nameField.getText().trim().isEmpty()
                    || fromField.getText().trim().isEmpty()
                    || toField.getText().trim().isEmpty()
                    || typeCombo.getValue() == null;
            okButton.setDisable(disable);
        };
        nameField.textProperty().addListener((o, v1, v2) -> validate.run());
        fromField.textProperty().addListener((o, v1, v2) -> validate.run());
        toField.textProperty().addListener((o, v1, v2) -> validate.run());
        typeCombo.valueProperty().addListener((o, v1, v2) -> validate.run());
        validate.run();

        // Build/Update result
        dialog.setResultConverter(btn -> {
            if (btn == saveType) {
                TourItem t = isEdit ? existing : new TourItem(nameField.getText());
                t.setName(nameField.getText());
                t.setDescription(descArea.getText());
                t.setFrom(fromField.getText());
                t.setTo(toField.getText());
                t.setTransportType(typeCombo.getValue());
                return t;
            }
            return null;
        });

        Optional<TourItem> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
