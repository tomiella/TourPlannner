package at.bif.swen.tourplanner.view;

import at.bif.swen.tourplanner.viewmodel.MainViewModel;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public MainViewModel viewModel = new MainViewModel();

    @FXML
    private TextField newRouteName;

    @FXML
    private Label routesSummary;

    @FXML
    protected void onAddButtonClick() {
        viewModel.createNewRoute();
    }

    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        routesSummary.textProperty().bind(viewModel.routesSummaryProperty());
//        newRouteName.textProperty().bindBidirectional(viewModel.newRouteNameProperty());
    }
}
