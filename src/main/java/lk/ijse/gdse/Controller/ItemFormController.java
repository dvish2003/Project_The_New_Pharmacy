package lk.ijse.gdse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.model.Item;
import lk.ijse.gdse.model.Stock;
import lk.ijse.gdse.repository.ItemRepo;
import lk.ijse.gdse.repository.StockRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemFormController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<Item, String> colCode;

    @FXML
    private TableColumn<Item, String> colDescription;

    @FXML
    private TableColumn<Item, Integer> colQtyOnHand;

    @FXML
    private TableColumn<Item, Double> colUnitPrice;

    @FXML
    private TableColumn<Item, String> colStockId;

    @FXML
    private ComboBox<String> comStockId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<Item> tblItem;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private void initialize() {
        setCellValueFactory();
        loadAllItems();
        getStockIds();
        tblItem.setItems(FXCollections.observableArrayList()); // Clear existing items
        loadAllItems(); // Load items again
        // Add listener to handle item selection
        tblItem.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtCode.setText(newSelection.getItemId());
                txtDescription.setText(newSelection.getDescription());
                txtQtyOnHand.setText(String.valueOf(newSelection.getQtyOnHand()));
                txtUnitPrice.setText(String.valueOf(newSelection.getUnitPrice()));
                comStockId.setValue(newSelection.getStockId());
            }
        });
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
    }

    private void loadAllItems() {
        ObservableList<Item> obList = FXCollections.observableArrayList();
        try {
            List<Item> itemList = ItemRepo.getAll();
            obList.addAll(itemList);
            tblItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ObservableList<Item> selectedRows = tblItem.getSelectionModel().getSelectedItems();
        List<Item> itemsToDelete = new ArrayList<>(selectedRows);
        try {
            for (Item item : itemsToDelete) {
                boolean isDeleted = ItemRepo.delete(item.getItemId());
                if (isDeleted) {
                    tblItem.getItems().remove(item);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed to delete item: " + item.getItemId());
                }
            }
            showAlert(Alert.AlertType.CONFIRMATION, "Items deleted successfully!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error occurred while deleting items: " + e.getMessage());
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String stockId = comStockId.getValue();
        String itemId = txtCode.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Item item = new Item(itemId, description, unitPrice, qtyOnHand, stockId);
        try {
            boolean isItemSaved = ItemRepo.save(item);
            if (isItemSaved) {
                tblItem.getItems().add(item);
                showAlert(Alert.AlertType.CONFIRMATION, "Item saved successfully!");
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to save Item!");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error occurred while saving Item: " + e.getMessage());
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String stockId = comStockId.getValue();
        String itemId = txtCode.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Item item = new Item(itemId, description, unitPrice, qtyOnHand, stockId);
        try {
            boolean isUpdate = ItemRepo.update(item);
            if (isUpdate) {
                showAlert(Alert.AlertType.CONFIRMATION, "Item is updated!");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error occurred while updating Item: " + e.getMessage());
        }
        loadAllItems();
        clearFields();
    }

    @FXML
    void comStockIdOnAction(ActionEvent event) {
        String id = comStockId.getValue();
        try {
            Stock stock = StockRepo.searchById(id);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error occurred while searching for stock: " + e.getMessage());
        }
    }

    private void getStockIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = StockRepo.getId();
            obList.addAll(idList);
            comStockId.setItems(obList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error occurred while fetching stock IDs: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        comStockId.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
