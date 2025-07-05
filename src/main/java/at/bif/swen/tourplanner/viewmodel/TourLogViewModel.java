package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourLog;
import at.bif.swen.tourplanner.service.LogManager;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Log4j2
public class TourLogViewModel {

    private final LogManager logManager;
    private TourItem selectedTour = null;

    private final ObservableList<TourLog> allTourLogs = FXCollections.observableArrayList();

    @Getter
    private final ObservableList<TourLog> filteredTourLogs = FXCollections.observableArrayList();

    public TourLogViewModel(LogManager logManager) {
        this.logManager = logManager;
    }

    public void addLog(Window owner) {
        TourLog result = showLogDialog(owner,null);
        if (result != null) {
            logManager.createTour(result.getDatetime(), result.getComment(), result.getDifficulty(), result.getRating(), result.getDuration(), selectedTour);
        }
    }

    public void editLog(Window owner, TourLog tour) {
        TourLog result = showLogDialog(owner, tour);

        if (result != null) {
            logManager.editTour(result);
        }
    }

    public void deleteLog(TourLog tour)
    {
        logManager.deleteLog(tour);
    }

    public ObservableList<TourLog> getLogList() {
        return filteredTourLogs;
    }

    private TourLog showLogDialog(Window owner, TourLog existing) {
        boolean isEdit = existing != null;
        Dialog<TourLog> dialog = new Dialog<>();
        dialog.initOwner(owner);
        dialog.setTitle(isEdit ? "Edit Log" : "Add New Log");
        dialog.setHeaderText(isEdit ? "Modify the log entry" : "Enter new log details");

        ButtonType saveButtonType = new ButtonType(isEdit ? "Save" : "Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker datePicker = new DatePicker();
        TextArea commentArea = new TextArea();
        Spinner<Integer> difficultySpinner = new Spinner<>(1, 5, 3);
        Spinner<Integer> ratingSpinner = new Spinner<>(1, 5, 3);
        Spinner<Integer> durationSpinner = new Spinner<>(1, 1440, 60); // duration in minutes


        if (isEdit) {
            commentArea.setText(existing.getComment());
            difficultySpinner.getValueFactory().setValue(existing.getDifficulty());
            ratingSpinner.getValueFactory().setValue(existing.getRating());
            durationSpinner.getValueFactory().setValue(existing.getDuration());
            if (existing.getDatetime() != null) {
                datePicker.setValue(existing.getDatetime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
        }


        commentArea.setPromptText("Enter a comment");
        commentArea.setWrapText(true);
        datePicker.setPromptText("Select date");
        difficultySpinner.setEditable(true);
        ratingSpinner.setEditable(true);
        durationSpinner.setEditable(true);


        grid.add(new Label("Date:"), 0, 0);
        grid.add(datePicker, 1, 0);
        grid.add(new Label("Comment:"), 0, 1);
        grid.add(commentArea, 1, 1);
        grid.add(new Label("Difficulty (1–5):"), 0, 2);
        grid.add(difficultySpinner, 1, 2);
        grid.add(new Label("Rating (1–5):"), 0, 3);
        grid.add(ratingSpinner, 1, 3);
        grid.add(new Label("Duration (min):"), 0, 4);
        grid.add(durationSpinner, 1, 4);

        dialog.getDialogPane().setContent(grid);


        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        Runnable validate = () -> {
            boolean disable = commentArea.getText().trim().isEmpty() || datePicker.getValue() == null;
            saveButton.setDisable(disable);
        };
        commentArea.textProperty().addListener((obs, oldVal, newVal) -> validate.run());
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> validate.run());
        validate.run();


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                TourLog log = isEdit ? existing : new TourLog(commentArea.getText().trim());
                log.setDatetime(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                log.setComment(commentArea.getText().trim());
                log.setDifficulty(difficultySpinner.getValue());
                log.setRating(ratingSpinner.getValue());
                log.setDuration(durationSpinner.getValue());
                return log;
            }
            return null;
        });

        Optional<TourLog> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public void setSelected(TourItem tour) {
        refresh();
        log.info("Selected tour: " + tour);
        selectedTour = tour;
        // filter to only show tourlogs taht includes the tour
        List<TourLog> logs = allTourLogs.stream().filter(l -> l.getRoute().equals(tour)).toList();
        log.info("Found " + logs.size() + " tour logs");
        filteredTourLogs.clear();
        filteredTourLogs.addAll(logs);
        log.info("Filtered tour logs: " + filteredTourLogs);
    }

    public void refresh() {
        allTourLogs.clear();
        allTourLogs.setAll(logManager.loadLogItems());

        log.info(allTourLogs.size() + " logs loaded");
        log.info(filteredTourLogs.size() + " logs loaded");
    }
}
