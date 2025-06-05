package at.bif.swen.tourplanner.view;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.viewmodel.DetailsViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class DetailsController implements Initializable {
    private final DetailsViewModel viewModel;

    public DetailsController(DetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private WebView mapWebView;

    @FXML
    private Label nameLabel;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Label routeLabel;

    @FXML
    private Label transportLabel;

    @FXML
    private ImageView tourImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine webEngine = mapWebView.getEngine();
        URL mapHtml = getClass().getResource("/at/bif/swen/tourplanner/map.html");
        webEngine.load(mapHtml.toExternalForm());

        Bindings.bindBidirectional(nameLabel.textProperty(), viewModel.nameProperty());
        Bindings.bindBidirectional(descriptionArea.textProperty(), viewModel.descriptionProperty());
        Bindings.bindBidirectional(routeLabel.textProperty(), viewModel.routeProperty());
        Bindings.bindBidirectional(transportLabel.textProperty(), viewModel.transportProperty());
        Bindings.bindBidirectional(tourImage.imageProperty(), viewModel.imageProperty());
    }

    public void setSelected(TourItem tour) {
        viewModel.setSelected(tour);
    }
}
