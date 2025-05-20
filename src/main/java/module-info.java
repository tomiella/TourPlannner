module at.bif.swen.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.web;

    opens at.bif.swen.tourplanner to javafx.fxml;
    exports at.bif.swen.tourplanner;
    opens at.bif.swen.tourplanner.model to javafx.fxml;
    exports at.bif.swen.tourplanner.model;
    opens at.bif.swen.tourplanner.view to javafx.fxml;
    exports at.bif.swen.tourplanner.view;
    opens at.bif.swen.tourplanner.viewmodel to javafx.fxml;
    exports at.bif.swen.tourplanner.viewmodel;
}