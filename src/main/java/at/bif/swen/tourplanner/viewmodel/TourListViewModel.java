package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TransportType;
import at.bif.swen.tourplanner.service.LogManager;
import at.bif.swen.tourplanner.service.TourManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Optional;


@Component
public class TourListViewModel {
    protected static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(TourListViewModel.class);

    private final TourManager tourManager;

    private final ObservableList<TourItem> allTours = FXCollections.observableArrayList();

    @Getter
    private final ObservableList<TourItem> filteredTours = FXCollections.observableArrayList();

    private final StringProperty searchText = new SimpleStringProperty("");

    private final PropertyChangeSupport tourCreatedEvent = new PropertyChangeSupport(this);

    public TourListViewModel(TourManager tourManager) {
        this.tourManager = tourManager;

        this.searchText.addListener((observable, oldValue, newValue) -> searchTours());

        refresh();
    }

    public void addTour(Window owner) {
        TourItem result = showTourDialog(owner, null);

        if (result != null) {
            tourManager.createTour(result);
        }
        refresh();
    }

    public void editTour(Window owner, TourItem tour) {
        TourItem result = showTourDialog(owner, tour);

        if (result != null) {
            tourManager.editTour(result);
        }
        refresh();
    }

    public void deleteTour(TourItem tour) {
        tourManager.deleteTour(tour);
        refresh();
    }

    public void searchTours() {
        filteredTours.setAll(tourManager.searchTours(allTours, searchText.get()));
    }

    public StringProperty searchTextProperty() {
        return searchText;
    }

    private TourItem showTourDialog(Window owner, TourItem existing) {
        boolean isEdit = existing != null;
        Dialog<TourItem> dialog = new Dialog<>();
        dialog.initOwner(owner);
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
        Button chooseImg = new Button("Choose Image…");
        ImageView preview = new ImageView();
        preview.setFitWidth(100);
        preview.setFitHeight(75);
        preview.setPreserveRatio(true);

        typeCombo.getItems().setAll(TransportType.values());

        if (isEdit) {
            nameField.setText(existing.getName());
            descArea.setText(existing.getDescription());
            fromField.setText(existing.getFrom());
            toField.setText(existing.getTo());
            typeCombo.setValue(existing.getTransportType());
            String path = existing.getImagePath();
            if (path != null) {
                preview.setImage(new Image(path));
                chooseImg.setUserData(path);
            }
        }

        nameField.setPromptText("Name");
        descArea.setPromptText("Description");
        fromField.setPromptText("From");
        toField.setPromptText("To");
        typeCombo.setPromptText("Transport");

        chooseImg.setOnAction(evt -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Select Tour Image");
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            File file = fc.showOpenDialog(owner);
            if (file != null) {
                String path = file.toURI().toString();
                preview.setImage(new Image(path));
                chooseImg.setUserData(path);
            }
        });


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
        grid.add(new Label("Image:"),        0, 5);
        HBox imgBox = new HBox(10, chooseImg, preview);
        grid.add(imgBox,                      1, 5);

        dialog.getDialogPane().setContent(grid);

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

        dialog.setResultConverter(btn -> {
            if (btn == saveType) {
                TourItem t = isEdit ? existing : new TourItem(nameField.getText());
                t.setName(nameField.getText());
                t.setDescription(descArea.getText());
                t.setFrom(fromField.getText());
                t.setTo(toField.getText());
                t.setTransportType(typeCombo.getValue());
                Object ud = chooseImg.getUserData();
                if (ud instanceof String) {
                    t.setImagePath((String)ud);
                }
                return t;
            }
            return null;
        });

        Optional<TourItem> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public void refresh() {
        allTours.clear();
        allTours.setAll(tourManager.loadTourItems());
        searchTours();

        logger.info(allTours.size() + " tours loaded");
        logger.info(filteredTours.size() + " tours loaded");
    }
}
