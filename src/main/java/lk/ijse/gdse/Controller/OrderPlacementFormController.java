
package lk.ijse.gdse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.repository.CustomerRepo;
import lk.ijse.gdse.repository.ItemRepo;
import lk.ijse.gdse.repository.OrderRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderPlacementFormController {

    @FXML
    private JFXButton btnAddCustomer;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private ComboBox<String> comCustomerId;

    @FXML
    private ComboBox<String> comItemId;

    @FXML
    private Label lblItemDescription;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<?> tblOrderPlacement;

    @FXML
    private TextField txtQty;

    public void initialize() {
        setDate();
        getCurrentOrderId();
        getCustomerIds();
        getItemIds();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        colQty.setCellValueFactory(new PropertyValueFactory<>(""));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    private void getItemIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
           List<String> idList = ItemRepo.getIds();

           for (String id : idList){
               obList.add(id);
           }
           comItemId.setItems(obList);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void getCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = CustomerRepo.getId();

            for (String id : idList){
                obList.add(id);
            }
            comCustomerId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentOrderId() {
        try {
            String currentId = OrderRepo.getCurrentId();

            String nextId = generateNextOrderId(currentId);
            lblOrderId.setText(nextId);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("0");
            int idNo = Integer.parseInt(split[1]);
            return "0" + ++idNo;
        }
        return "01";
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

}

