package lk.ijse.gdse.repository;

import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee VALUES(?,?,?,?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, employee.getEmployeeId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(4, employee.getNICNo());
        pstm.setObject(3, employee.getAddress());
        pstm.setObject(5, employee.getTel());
        pstm.setObject(6, employee.getSalary());

        return pstm.executeUpdate() > 0;
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM Employee";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Employee> employeesList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String nicNo = resultSet.getString(4);
            String tel = resultSet.getString(5);
            double salary = resultSet.getDouble(6);

            Employee employee = new Employee(id, name, address, nicNo, tel, salary);
            employeesList.add(employee);
        }
        return employeesList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Employee WHERE employeeId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET name = ? , nicNo = ? , address = ? , tel = ? , salary = ? WHERE employeeId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,employee.getName());
        pstm.setObject(3,employee.getNICNo());
        pstm.setObject(2,employee.getAddress());
        pstm.setObject(4,employee.getTel());
        pstm.setObject(5,employee.getSalary());
        pstm.setObject(6,employee.getEmployeeId());

        return pstm.executeUpdate() > 0;
    }

    public static Employee searchById(String id) throws SQLException {
        String sql = "SELECT*FROM Employee WHERE employeeId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();
        resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String nicNo = resultSet.getString(4);
            String tel = resultSet.getString(5);
            double salary = resultSet.getDouble(6);

            return new Employee(employeeId, name, address, nicNo, tel, salary);

        }
        return null;
    }

    public static List<String> getId() throws SQLException {
        String sql = "SELECT employeeId from Employee";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> employeeList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            employeeList.add(id);
        }
        return employeeList;
    }
}
