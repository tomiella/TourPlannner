package at.bif.swen.tourplanner.viewmodel;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.service.TourManager;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TourListViewModelTest {

    private TourManager tourManager;
    private TourListViewModel viewModel;

    @BeforeEach
    void setUp() {
        tourManager = new TourManager();
        viewModel = new TourListViewModel(tourManager);
    }

   /* @Test
    void whenInit_thenTourListNotNull() {
        ObservableList<TourItem> list = viewModel.getTourList();
        assertNotNull(list);
    }*/

   /* @Test
    void whenManagerCreatesTour_thenViewModelReflectsNewItem() {
        TourItem item = new TourItem("Sample Tour");
        tourManager.createTour(item);
        ObservableList<TourItem> list = viewModel.getTourList();
        assertTrue(list.contains(item), "ViewModel should reflect new items created in manager");
    }*/

  /*  @Test
    void whenViewModelDeletesTour_thenManagerListUpdates() {
        TourItem item = new TourItem("DeleteMe");
        tourManager.createTour(item);
        viewModel.deleteTour(item);
        ObservableList<TourItem> list = viewModel.getTourList();
        assertFalse(list.contains(item), "ViewModel.deleteTour should remove the item from the manager's list");
    }*/

    @Test
    void searchTextProperty_ShouldAllowGetAndSet() {
        assertEquals("", viewModel.searchTextProperty().get(), "Search text should be empty by default");
        viewModel.searchTextProperty().set("query");
        assertEquals("query", viewModel.searchTextProperty().get(), "Search text property should be updatable");
    }

    @Test
    void searchTours_CanBeCalledWithoutException() {
        assertDoesNotThrow(() -> viewModel.searchTextProperty().set("any text"));
    }
}
