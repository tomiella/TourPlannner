package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.service.ExportService;
import at.bif.swen.tourplanner.service.ReportService;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class MenuViewModel {
    private final ReportService reportService;
    private final ExportService exportService;

    public MenuViewModel(ReportService reportService, ExportService exportService) {
        this.reportService = reportService;
        this.exportService = exportService;
    }

    public void createSummaryReport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose report file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            try {
                reportService.generateSummaryReport(selectedFile.getAbsolutePath());
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void createTourReport(TourItem tourItem) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose report file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            try {
                reportService.generateTourReport(tourItem, selectedFile.getAbsolutePath());
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void exportTour(TourItem tourItem) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save As");
            fileChooser.setInitialFileName("untitled.json");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("JSON Files", "*.json")
            );
            File selectedFile = fileChooser.showSaveDialog(null);

            if (selectedFile != null) {
                exportService.exportTour(selectedFile.getAbsolutePath(), tourItem);
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void importTour() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("JSON Files", "*.json")
            );
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                exportService.importTour(selectedFile.getAbsolutePath());
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
