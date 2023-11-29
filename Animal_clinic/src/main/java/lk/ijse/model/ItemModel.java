package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public static ArrayList<ItemDTO> getAll() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from Item");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<ItemDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new ItemDTO(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getInt(3),
                                resultSet.getInt(4)));

            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ItemDTO searchItem(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Item where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            ItemDTO itemDTO = null;
            while (resultSet.next()) {
                itemDTO = new ItemDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4));

            }
            return itemDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int itemSave(ItemDTO itemDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into Item values (?,?,?,?)");
            pstm.setObject(1,itemDTO.getId());
            pstm.setObject(2,itemDTO.getDescription());
            pstm.setObject(3,itemDTO.getPrice());
            pstm.setObject(4,itemDTO.getCount());
            int i =pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteItem(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from Item where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int itemUpdate(ItemDTO itemDTO) {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update Item set description=?,price=?,count=? where id=?");
            pstm.setObject(1,itemDTO.getDescription());
            pstm.setObject(2,itemDTO.getPrice());
            pstm.setObject(3,itemDTO.getCount());
            pstm.setObject(4,itemDTO.getId());

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existItem(String id) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT id FROM Item WHERE id=?");
            pstm.setString(1, id);
            return pstm.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
