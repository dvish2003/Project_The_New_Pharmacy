package lk.ijse.gdse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.model.Employee;
import lk.ijse.gdse.repository.EmployeeRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

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
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNICNo;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNICNo;

    @FXML
    private TextField txtName;

    @FXML

    private TextField txtTel;

    @FXML
    private TextField txtSalary;

    public void initialize() {
        setCellValueFactory();
        loadAllEmployee();
//        applyAnimationToButton();
    }

   /* private void applyAnimationToButton() {
        applyAnimation(btnSave);
    }*/

    private void loadAllEmployee() {
        ObservableList<Employee> objList = FXCollections.observableArrayList();
        try {
            List<Employee> employeeList = EmployeeRepo.getAll();
            for (Employee employee : employeeList) {
                Employee employee1 = new Employee(
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getNICNo(),
                        employee.getAddress(),
                        employee.getTel(),
                        employee.getSalary()
                );
                objList.add(employee1);
            }
            tblEmployee.setItems(objList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        this.colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        this.colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colNICNo.setCellValueFactory(new PropertyValueFactory<>("NICNo"));
        this.colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        this.colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }


    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtId.getText();

        Employee employee = EmployeeRepo.searchById(id);

        if (employee != null) {
            txtId.setText(employee.getEmployeeId());
            txtName.setText(employee.getName());
            txtNICNo.setText(employee.getNICNo());
            txtAddress.setText(employee.getAddress());
            txtTel.setText(employee.getTel());
            txtSalary.setText(String.valueOf(employee.getSalary()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer is not found!");
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted!");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
        Parent rootNode = loader.load();

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");

        stage.show();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String nicNo = txtNICNo.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        Employee employee = new Employee(id, name, nicNo, address, tel, salary);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (id.isEmpty() || name.isEmpty() || nicNo.isEmpty() || address.isEmpty() || tel.isEmpty() || txtSalary.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
                return;
            }


            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved successfully!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtNICNo.clear();
        txtAddress.clear();
        txtTel.clear();
        txtSalary.clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String nicNo = txtNICNo.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        Employee employee = new Employee(id, name, nicNo, address, tel, salary);

        try {
            boolean isUpdate = EmployeeRepo.update(employee);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer is updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();
        clearFields();
    }

}
