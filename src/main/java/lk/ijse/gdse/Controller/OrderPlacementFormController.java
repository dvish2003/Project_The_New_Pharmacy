package lk.ijse.gdse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.model.CartTm;
import lk.ijse.gdse.model.Customer;
import lk.ijse.gdse.model.Employee;
import lk.ijse.gdse.model.Item;
import lk.ijse.gdse.repository.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OrderPlacementFormController {

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private JFXButton btnAddCustomer;

    @FXML
    private JFXButton btnAddEmployee;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnPlaceOrder;

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
    private ComboBox<String> comEmployeeId;

    @FXML
    private ComboBox<String> comItemId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private Label lblItemDescription;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblPayId;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblQty;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CartTm> tblOrderPlacement;

    @FXML
    private TextField txtQty;
    private ObservableList<CartTm> obList = FXCollections.observableArrayList();
    public void initialize() {
        setDate();
        getCurrentOrderId();
        getCurrentPayId();
        getCustomerIds();
        getEmployeeIds();
        getItemIds();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));

    }
    private void getCurrentOrderId() {

        try {
            String currentId = OrderRepo.getCurrentId();
            String nextOrderId = generateNextOrderId(currentId);
            lblOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if (currentId != null && currentId.startsWith("ORD")) {
            int idNum = Integer.parseInt(currentId.substring(3)) + 1;
            return "ORD" + String.format("%03d", idNum);
        }
        return "ORD001";
    }
    private void getCurrentPayId() {
        try {
            String currentId = PaymentRepo.getPayCurrentId();
            String nextPayId = generateNextPay(currentId);
            lblPayId.setText(nextPayId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextPay(String currentId) {
        if (currentId != null && currentId.startsWith("PAY")) {
            try {
                int idNum = Integer.parseInt(currentId.substring(3)) + 1;
                return "PAY" + String.format("%03d", idNum);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid current payment ID format");
            }
        }
        return "PAY001";
    }





    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        String I_Id = comItemId.getValue();
        String Description = lblItemDescription.getText();
        double Unit_Price = Double.parseDouble(lblUnitPrice.getText());
        int Qty = Integer.parseInt(txtQty.getText());
        double Amount = Unit_Price * Qty;

        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tblOrderPlacement.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);
                tblOrderPlacement.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrderPlacement.getItems().size(); i++) {
            if(I_Id.equals(colId.getCellData(i))){
                CartTm tm = obList.get(i);
                Qty += tm.();
                Amount = Qty*Unit_Price;
                tm.
            }
        }

        CartTm tm = new CartTm(I_Id, Description, Qty, Unit_Price, Amount, btnRemove);
        obList.add(tm);
        tblOrderPlacement.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");
    }


    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblOrderPlacement.getItems().size(); i++) {
            netTotal += (int) colAmount.getCellData(i);
        }
        lblAmount.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }
    private void getCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = CustomerRepo.getId();

            for(String id : idList) {
                obList.add(id);
            }

            comCustomerId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = EmployeeRepo.getId();

            for(String id : idList) {
                obList.add(id);
            }

            comEmployeeId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getItemIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = ItemRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            comItemId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void comCustomerIdOnActiion(ActionEvent event) {
        String id = comCustomerId.getValue();
        try {
            Customer customer = CustomerRepo.searchById(id);

            lblCustomerName.setText(customer.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void comEmployeeIdOnAction(ActionEvent event) {
        String id =  comEmployeeId.getValue();
        try {
            Employee employee = EmployeeRepo.searchById(id);

            lblEmployeeName.setText(employee.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void comItemIdOnAction(ActionEvent event) {
        String id = String.valueOf(comItemId.getValue());
        try {
            Item item = ItemRepo.searchById(id);

            assert item != null;
            lblItemDescription.setText(item.getDescription());
            lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            lblQty.setText(String.valueOf(item.getQtyOnHand()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

}


