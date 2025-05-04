module at.bif.swen.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens at.bif.swen.tourplanner to javafx.fxml;
    exports at.bif.swen.tourplanner;
}