package at.bif.swen.tourplanner;

import at.bif.swen.tourplanner.service.LogManager;
import at.bif.swen.tourplanner.service.TourManager;
import at.bif.swen.tourplanner.view.DetailsController;
import at.bif.swen.tourplanner.view.MenuController;
import at.bif.swen.tourplanner.view.TourListController;
import at.bif.swen.tourplanner.view.TourLogsController;
import at.bif.swen.tourplanner.viewmodel.DetailsViewModel;
import at.bif.swen.tourplanner.viewmodel.TourListViewModel;
import at.bif.swen.tourplanner.viewmodel.TourLogViewModel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Application;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;


public class TourPlannerApplication extends Application {


    private ConfigurableApplicationContext springContext;


    public TourPlannerApplication() {

    }
    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(TourPlannerConfig.class).run(getParameters().getRaw().toArray(new String[0]));
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TourPlannerApplication.class.getResource("main-view.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root = fxmlLoader.load();
        showStage(stage, root);
    }


    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }

    public static void showStage(Stage stage, Parent root) {
        Scene scene = new Scene(root);
        stage.setMinWidth(725);
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
