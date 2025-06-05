package at.bif.swen.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MenuController implements Initializable {
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
