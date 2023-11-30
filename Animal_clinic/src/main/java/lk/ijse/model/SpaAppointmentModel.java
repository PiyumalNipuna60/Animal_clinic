package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.SpaAppointmentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SpaAppointmentModel {
    public  ArrayList<SpaAppointmentDTO> getAll() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from SpaAppointment");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<SpaAppointmentDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new SpaAppointmentDTO(
                                resultSet.getString(1),
                                resultSet.getInt(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7))
                        );
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SpaAppointmentDTO searchAppointment(String id) {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from SpaAppointment where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            SpaAppointmentDTO appointmentDTO = null;
            while (resultSet.next()) {
                appointmentDTO=new SpaAppointmentDTO(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );

            }
            return appointmentDTO;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int appointmentSave(SpaAppointmentDTO appointmentDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into SpaAppointment values (?,?,?,?,?,?,?)");
            pstm.setObject(1,appointmentDTO.getId());
            pstm.setObject(2,appointmentDTO.getPrice());
            pstm.setObject(3,appointmentDTO.getDate());
            pstm.setObject(4,appointmentDTO.getTime());
            pstm.setObject(5,appointmentDTO.getCustomerId());
            pstm.setObject(6,appointmentDTO.getAnimalId());
            pstm.setObject(7,appointmentDTO.getStatus());

            int i =pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteAppointment(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from SpaAppointment where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateAppointment(SpaAppointmentDTO appointmentDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update SpaAppointment set price=?,date=?,time=?,customerID=?,AnimalId=?,status=? where id=?");
            pstm.setObject(1,appointmentDTO.getPrice());
            pstm.setObject(2,appointmentDTO.getDate());
            pstm.setObject(3,appointmentDTO.getTime());
            pstm.setObject(4,appointmentDTO.getCustomerId());
            pstm.setObject(7,appointmentDTO.getId());
            pstm.setObject(5,appointmentDTO.getAnimalId());
            pstm.setObject(6,appointmentDTO.getStatus());

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from SpaAppointment");
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
