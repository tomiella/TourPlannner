package at.bif.swen.tourplanner.view;

import at.bif.swen.tourplanner.viewmodel.TourListViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TourListController implements Initializable {
    private final TourListViewModel viewModel;

    @FXML
    public TextField searchField;

    @FXML
    protected void onAddButtonClick() {
        viewModel.addTour();
    }

    public TourListController(TourListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bindings.bindBidirectional(searchField.textProperty(), viewModel.searchTextProperty());
    }
}
