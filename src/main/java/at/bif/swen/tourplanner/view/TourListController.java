package at.bif.swen.tourplanner.view;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.viewmodel.TourListViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class TourListController implements Initializable {
    private final TourListViewModel viewModel;
    private final DetailsController detailsController;
    private final TourLogsController logsController;

    @FXML
    public ListView<TourItem> tourList;

    @FXML
    public TextField searchField;

    @FXML
    protected void onAddButtonClick() {
        viewModel.addTour(tourList.getScene().getWindow());
    }

    @FXML
    protected void onEditButtonClick() {
        TourItem selectedItem = getSelectedTourItem();
        if (selectedItem == null) {
            return;
        }
        viewModel.editTour(tourList.getScene().getWindow(), selectedItem);
    }

    @FXML
    protected void onDeleteButtonClick() {
        TourItem selectedItem = getSelectedTourItem();
        if (selectedItem == null) {
            return;
        }
        viewModel.deleteTour(selectedItem);
    }

    public TourListController(TourListViewModel viewModel, DetailsController detailsController, TourLogsController logsController) {
        this.detailsController = detailsController;
        this.logsController = logsController;
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bindings.bindBidirectional(searchField.textProperty(), viewModel.searchTextProperty());
        tourList.setItems(viewModel.getFilteredTours());
        tourList.getSelectionModel().selectedItemProperty().addListener((obs, oldT, newT) -> {
            detailsController.setSelected(newT);
            logsController.setSelected(newT);
        });
    }

    private TourItem getSelectedTourItem() {
        TourItem selectedItem = tourList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No tour selected");
            a.setContentText("Please select a tour to edit");
            a.showAndWait();
            return null;
        }
        return selectedItem;
    }
}
