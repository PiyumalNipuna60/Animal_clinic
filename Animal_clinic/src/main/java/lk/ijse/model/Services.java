package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ServicesDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Services {

    public static ArrayList<ServicesDTO> getAll() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from Services");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<ServicesDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new ServicesDTO(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)));
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ServicesDTO SearchService(String id) {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Services where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            ServicesDTO servicesDTO = null;
            while (resultSet.next()) {
                servicesDTO = new ServicesDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5));

            }
            return servicesDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteServices(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from Services where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateServices(ServicesDTO servicesDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update Customer set type=?,price=?,startTime=?endTime=? where id=?");
            pstm.setObject(1,servicesDTO.getType());
            pstm.setObject(2,servicesDTO.getPrice());
            pstm.setObject(3,servicesDTO.getStartTime());
            pstm.setObject(4,servicesDTO.getEndTime());

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int saveServices(ServicesDTO servicesDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into Customer values (?,?,?,?)");
            pstm.setObject(1,servicesDTO.getType());
            pstm.setObject(2,servicesDTO.getPrice());
            pstm.setObject(3,servicesDTO.getStartTime());
            pstm.setObject(4,servicesDTO.getEndTime());

            int i =pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
