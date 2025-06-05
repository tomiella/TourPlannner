package at.bif.swen.tourplanner.view;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourLog;
import at.bif.swen.tourplanner.service.LogManager;
import at.bif.swen.tourplanner.viewmodel.TourLogViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;


@Controller
public class TourLogsController implements Initializable {

    private TourLogViewModel viewModel;

    @FXML
    public TableView<TourLog> logTable;

    @FXML
    public TableColumn<TourLog, String> commentCol;
    @FXML
    public TableColumn<TourLog, String> dateCol;
    @FXML
    public TableColumn<TourLog, Integer> difficultyCol;
    @FXML
    public TableColumn<TourLog, Integer> ratingCol;
    @FXML
    public TableColumn<TourLog, Integer> durationCol;

    @FXML
    protected void onAddButtonClick() {
        viewModel.addLog(logTable.getScene().getWindow());
        logTable.setItems(viewModel.getLogList());
    }

    @FXML
    protected void onEditButtonClick() {
        TourLog selectedItem = getSelectedTourLog();
        if (selectedItem == null) {
            return;
        }
        viewModel.editLog(logTable.getScene().getWindow(), selectedItem);
    }

    @FXML
    protected void onDeleteButtonClick() {
        TourLog selectedItem = getSelectedTourLog();
        if(selectedItem == null){
            return;
        }
        viewModel.deleteLog(selectedItem);
    }

    public TourLogsController(TourLogViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        viewModel = new TourLogViewModel(new LogManager());


        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));


        dateCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDatetime() != null) {
                return new SimpleStringProperty(cellData.getValue().getDatetime().toString());
            } else {
                return new SimpleStringProperty("");
            }
        });


        logTable.setItems(viewModel.getLogList());
    }

    private TourLog getSelectedTourLog() {
        TourLog selectedItem = logTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No tour selected");
            a.setContentText("Please select a tour to edit");
            a.showAndWait();
            return null;
        }
        return selectedItem;
    }

    public void setSelected(TourItem tour) {
        viewModel.setSelected(tour);
    }
}
