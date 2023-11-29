package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.OrderDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Order {
    public static ArrayList<OrderDTO> getAll() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from orders");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<OrderDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new OrderDTO(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getInt(4),
                                resultSet.getString(5)));
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteOrder(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from orders where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateOrder(OrderDTO orderDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update orders set quantityAndHand=?,date=?,total=?,customerID=? where id=?");
            pstm.setObject(1,orderDTO.getId());
            pstm.setObject(2,orderDTO.getQuantityAndHand());
            pstm.setObject(3,orderDTO.getDate());
            pstm.setObject(4,orderDTO.getTotal());
            pstm.setObject(5,orderDTO.getCustomerId());

            int i = pstm.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int saveOrder(OrderDTO orderDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into orders values (?,?,?,?,?)");
            pstm.setObject(1,orderDTO.getId());
            pstm.setObject(2,orderDTO.getQuantityAndHand());
            pstm.setObject(3,orderDTO.getDate());
            pstm.setObject(4,orderDTO.getTotal());
            pstm.setObject(5,orderDTO.getCustomerId());

            int i =pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public OrderDTO searchOrder(String id) {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from orders where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            OrderDTO orderDTO = null;
            while (resultSet.next()) {
                orderDTO = new OrderDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5));

            }
            return orderDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
