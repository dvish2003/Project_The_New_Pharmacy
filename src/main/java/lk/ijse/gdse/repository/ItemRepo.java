package lk.ijse.gdse.repository;

import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {
    public static boolean save(Item item) throws SQLException {
        String sql = "INSERT INTO item VALUES(?,?,?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, item.getItemId());
        pstm.setObject(2, item.getDescription());
        pstm.setObject(4, item.getScale());
        pstm.setObject(3, item.getUnitPrice());
        pstm.setObject(5, item.getStockId());

        return pstm.executeUpdate() > 0;
    }

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT*FROM item";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Item> itemList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String description = resultSet.getString(2);
            String scale = resultSet.getString(4);
            double unitePrice = Double.parseDouble(resultSet.getString(3));
            String stockId = resultSet.getString(5);

            Item item = new Item(id, description, scale, unitePrice, stockId);
            itemList.add(item);
        }
        return itemList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM item WHERE itemId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE item SET description = ? , scale = ? , unitPrice = ? , stockId = ? WHERE itemId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,item.getDescription());
        pstm.setObject(3,item.getScale());
        pstm.setObject(2,item.getUnitPrice());
        pstm.setObject(4,item.getStockId());
        pstm.setObject(5,item.getItemId());

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT itemId FROM item";
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        ArrayList<String> idList = new ArrayList<>();
        while (resultSet.next()){
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static Item searchById(String id) throws SQLException {
        String sql = "SELECT *FROM item WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,id);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }
}
