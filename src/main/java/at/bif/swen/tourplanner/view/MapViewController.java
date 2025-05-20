package at.bif.swen.tourplanner.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MapViewController implements Initializable {

    @FXML
    private WebView mapWebView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine webEngine = mapWebView.getEngine();
        URL mapHtml = getClass().getResource("/at/bif/swen/tourplanner/map.html");
        webEngine.load(mapHtml.toExternalForm());
    }
}
