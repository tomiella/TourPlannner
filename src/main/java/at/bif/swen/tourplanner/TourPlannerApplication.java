package at.bif.swen.tourplanner;

import at.bif.swen.tourplanner.service.TourManager;
import at.bif.swen.tourplanner.view.DetailsController;
import at.bif.swen.tourplanner.view.MenuController;
import at.bif.swen.tourplanner.view.TourListController;
import at.bif.swen.tourplanner.viewmodel.DetailsViewModel;
import at.bif.swen.tourplanner.viewmodel.TourListViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.application.Application;


public class TourPlannerApplication extends Application {
    private final TourManager tourManager;

    private final TourListViewModel tourListViewModel;
    private final DetailsViewModel detailsViewModel;

    public TourPlannerApplication() {
        this.tourManager = new TourManager();

        this.detailsViewModel = new DetailsViewModel();
        this.tourListViewModel = new TourListViewModel(tourManager);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadRootNode(tourListViewModel, detailsViewModel);
        showStage(stage, root);
    }

    public static Parent loadRootNode(TourListViewModel tourListViewModel, DetailsViewModel detailsViewModel) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TourPlannerApplication.class.getResource("main-view.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> {
            if (controllerClass == DetailsController.class) {
                return new DetailsController(detailsViewModel);
            } else if (controllerClass == TourListController.class) {
                return new TourListController(tourListViewModel, new DetailsController(detailsViewModel));
            } else if (controllerClass == MenuController.class) {
                return new MenuController();
            } else {
                throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
            }
        });
        return fxmlLoader.load();
    }

    public static void showStage(Stage stage, Parent root) {
        Scene scene = new Scene(root);
        stage.setMinWidth(500);
        stage.setMinHeight(400);
        stage.setTitle("TourPlanner");
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
