package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.OrderDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderModel {
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
                                resultSet.getInt(3),
                                resultSet.getString(4)));
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean saveOrder(String oId, String cusId, LocalDate now, double total) throws SQLException {
           Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into orders values (?,?,?,?)");
            pstm.setObject(1,oId);
            pstm.setObject(2,now);
            pstm.setObject(3,total);
            pstm.setObject(4,cusId);

        return pstm.executeUpdate() > 0;
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
            PreparedStatement pstm= connection.prepareStatement("update orders set date=?,total=?,customerID=? where id=?");
            pstm.setObject(1,orderDTO.getId());
            pstm.setObject(2,orderDTO.getDate());
            pstm.setObject(3,orderDTO.getTotal());
            pstm.setObject(4,orderDTO.getCustomerId());

            int i = pstm.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getNextOrderId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT id FROM Orders ORDER BY id DESC LIMIT 1";
        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("O0");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "O0" + id;
        }
        return "O001";
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
                        resultSet.getInt(3),
                        resultSet.getString(4));

            }
            return orderDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
