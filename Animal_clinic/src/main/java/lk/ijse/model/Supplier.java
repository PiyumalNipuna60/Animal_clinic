package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.SupplierDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Supplier {
    public static ArrayList<SupplierDTO> getAll() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from Supplier");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<SupplierDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new SupplierDTO(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getInt(4)));
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int supplierSave(SupplierDTO supplierDTO) {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into Supplier values (?,?,?,?)");
            pstm.setObject(1,supplierDTO.getId());
            pstm.setObject(2,supplierDTO.getName());
            pstm.setObject(3,supplierDTO.getEmail());
            pstm.setObject(4,supplierDTO.getPhone());

            int i =pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateSupplier(SupplierDTO supplierDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update Supplier set name=?,email=?,phone=? where id=?");
            pstm.setObject(1,supplierDTO.getId());
            pstm.setObject(2,supplierDTO.getName());
            pstm.setObject(3,supplierDTO.getEmail());
            pstm.setObject(4,supplierDTO.getPhone());

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteSupplier(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from Supplier where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SupplierDTO searchSupplier(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Supplier where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            SupplierDTO supplierDTO = null;
            while (resultSet.next()) {
                supplierDTO = new SupplierDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4));

            }
            return supplierDTO;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
