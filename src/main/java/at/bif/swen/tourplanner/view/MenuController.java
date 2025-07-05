package at.bif.swen.tourplanner.view;

import at.bif.swen.tourplanner.viewmodel.MenuViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MenuController implements Initializable {
    TourListController tourListController;
    MenuViewModel menuViewModel;

    public MenuController(TourListController tourListController, MenuViewModel menuViewModel) {
        this.tourListController = tourListController;
        this.menuViewModel = menuViewModel;
    }

    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }

    @FXML
    protected void onTourReportButtonClick() {
        menuViewModel.createTourReport(tourListController.getSelectedTourItem());
    }

    @FXML
    protected void onSummaryReportButtonClick() {
        menuViewModel.createSummaryReport();
    }

    @FXML
    protected void onExportButtonClick() {
        menuViewModel.exportTour(tourListController.getSelectedTourItem());
    }

    @FXML
    protected void onImportButtonClick() {
        menuViewModel.importTour();
        tourListController.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        routesSummary.textProperty().bind(viewModel.routesSummaryProperty());
//        newRouteName.textProperty().bindBidirectional(viewModel.newRouteNameProperty());
    }
}
