package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public static boolean existCustomer(String id) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT id FROM Customer WHERE id=?");
            pstm.setString(1, id);
            return pstm.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public  ArrayList<CustomerDTO> getAll() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from Customer");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<CustomerDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new CustomerDTO(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)));
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public CustomerDTO searchCustomer(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Customer where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            CustomerDTO customerDTO = null;
            while (resultSet.next()) {
                customerDTO = new CustomerDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6));

            }
            return customerDTO;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int deleteCustomer(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from Customer where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateCustomer(CustomerDTO customerDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update Customer set name=?,address=?,email=?,contactNo=?,userId=? where id=?");
            pstm.setObject(1,customerDTO.getName());
            pstm.setObject(2,customerDTO.getAddress());
            pstm.setObject(3,customerDTO.getEmail());
            pstm.setObject(4,customerDTO.getPhone());
            pstm.setObject(5,customerDTO.getUserId());
            pstm.setObject(6,customerDTO.getId());

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int customerSave(CustomerDTO customerDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into Customer values (?,?,?,?,?,?)");
            pstm.setObject(1,customerDTO.getId());
            pstm.setObject(2,customerDTO.getName());
            pstm.setObject(3,customerDTO.getAddress());
            pstm.setObject(4,customerDTO.getEmail());
            pstm.setObject(5,customerDTO.getPhone());
            pstm.setObject(6,customerDTO.getUserId());

            int i =pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getCustomerName() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Customer");
            ResultSet resultSet = pstm.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet rst = pstm.executeQuery();
            int count=0;
            while (rst.next()){
                count++;
            }

            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
