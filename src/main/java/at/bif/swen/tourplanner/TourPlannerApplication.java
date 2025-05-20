package at.bif.swen.tourplanner;

import at.bif.swen.tourplanner.service.TourManager;
import at.bif.swen.tourplanner.view.MainController;
import at.bif.swen.tourplanner.view.MenuController;
import at.bif.swen.tourplanner.view.TourListController;
import at.bif.swen.tourplanner.viewmodel.MainViewModel;
import at.bif.swen.tourplanner.viewmodel.TourListViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.application.Application;


public class TourPlannerApplication extends Application {
    private final TourManager tourManager;

    private final MainViewModel mainViewModel;
    private final TourListViewModel tourListViewModel;

    public TourPlannerApplication() {
        this.tourManager = new TourManager();

        this.mainViewModel = new MainViewModel();
        this.tourListViewModel = new TourListViewModel(tourManager);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadRootNode(mainViewModel, tourListViewModel);
        showStage(stage, root);
    }

    public static Parent loadRootNode(MainViewModel mainViewModel, TourListViewModel tourListViewModel) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TourPlannerApplication.class.getResource("main-view.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> {
            if (controllerClass == MainController.class) {
                return new MainController();
            } else if (controllerClass == TourListController.class) {
                return new TourListController(tourListViewModel);
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
