package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.service.ReportService;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;

@Component
public class MenuViewModel {
    private final ReportService reportService;

    public MenuViewModel(ReportService reportService) {
        this.reportService = reportService;
    }

    public void createSummaryReport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose report file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        File selectedFile = fileChooser.showOpenDialog(null);

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

        File selectedFile = fileChooser.showOpenDialog(null);

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
}
