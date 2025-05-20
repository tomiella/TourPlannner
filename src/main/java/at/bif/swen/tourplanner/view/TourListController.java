package at.bif.swen.tourplanner.view;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.viewmodel.TourListViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TourListController implements Initializable {
    private final TourListViewModel viewModel;

    @FXML
    public ListView<TourItem> tourList;

    @FXML
    public TextField searchField;

    @FXML
    protected void onAddButtonClick() {
        viewModel.addTour();
    }

    @FXML
    protected void onEditButtonClick() {
        TourItem selectedItem = tourList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No tour selected");
            a.setContentText("Please select a tour to edit");
            a.showAndWait();
            return;
        }
        viewModel.editTour(selectedItem);
    }

    public TourListController(TourListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bindings.bindBidirectional(searchField.textProperty(), viewModel.searchTextProperty());
        tourList.setItems(viewModel.getTourList());
    }
}
