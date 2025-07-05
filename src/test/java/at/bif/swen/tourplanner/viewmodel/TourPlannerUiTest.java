package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.TourPlannerApplication;
import at.bif.swen.tourplanner.service.ExportService;
import at.bif.swen.tourplanner.service.LogManager;
import at.bif.swen.tourplanner.service.ReportService;
import at.bif.swen.tourplanner.view.DetailsController;
import at.bif.swen.tourplanner.view.MenuController;
import at.bif.swen.tourplanner.view.TourListController;
import at.bif.swen.tourplanner.view.TourLogsController;
import at.bif.swen.tourplanner.viewmodel.DetailsViewModel;
import at.bif.swen.tourplanner.viewmodel.TourListViewModel;
import at.bif.swen.tourplanner.viewmodel.TourLogViewModel;
import at.bif.swen.tourplanner.service.TourManager;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class TourPlannerUiTest {

    private TourManager tourManager;
    private LogManager logManager;
    private TourListViewModel listViewModel;
    private DetailsViewModel detailsViewModel;
    private TourLogViewModel logViewModel;
    private Parent root;

    @Start
    private void start(Stage stage) throws Exception {
        // initialize real manager and view-models
        tourManager = new TourManager();
        listViewModel = new TourListViewModel(tourManager);
        detailsViewModel = new DetailsViewModel();
        logViewModel = new TourLogViewModel(logManager);

        DetailsController detailsController = new DetailsController(detailsViewModel);
        TourLogsController tourLogsController = new TourLogsController(logViewModel);
        TourListController tourListController = new TourListController(listViewModel, detailsController, tourLogsController);
        MenuController menuController = new MenuController(tourListController, new MenuViewModel(new ReportService(), new ExportService()));

        // load and show UI




    }

    @Test
    void whenInit_thenTourListNotEmpty(FxRobot robot) {
        // Assert that ListView has items
        ListView<?> tourList = robot.lookup("#tourList").queryAs(ListView.class);
        assertNotNull(tourList);
        assertTrue(tourList.getItems().size() > 0, "Tour list should be initialized with items");
    }

    @Test
    void whenSelectTour_thenDetailsPanePopulates(FxRobot robot) {
        // Select the first tour
        ListView<?> tourList = robot.lookup("#tourList").queryAs(ListView.class);
        Platform.runLater(() -> tourList.getSelectionModel().select(0));
        // wait for UI thread
        robot.sleep(500);

       /* // Get selected TourItem from manager to compare
        var selected = tourManager.getTourList().get(0);

        // Verify name label
        Label nameLabel = robot.lookup("#nameLabel").queryAs(Label.class);
        assertEquals(selected.getName(), nameLabel.getText());

        // Verify description area
        TextArea descArea = robot.lookup("#descriptionArea").queryAs(TextArea.class);
        assertEquals(selected.getDescription(), descArea.getText());

        // Verify route label ("from → to")
        Label routeLabel = robot.lookup("#routeLabel").queryAs(Label.class);
        String expectedRoute = selected.getFrom() + " → " + selected.getTo();
        assertEquals(expectedRoute, routeLabel.getText());

        // Verify transport label
        Label transportLabel = robot.lookup("#transportLabel").queryAs(Label.class);
        assertEquals(selected.getTransportType().name(), transportLabel.getText());*/
    }

}
