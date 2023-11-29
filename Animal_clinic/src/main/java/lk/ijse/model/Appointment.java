package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.AppointmentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Appointment {
    public  ArrayList<AppointmentDTO> getAll() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from appointment");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<AppointmentDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new AppointmentDTO(
                                resultSet.getString(1),
                                resultSet.getInt(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5))
                        );
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AppointmentDTO searchAppointment(String id) {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Appointment where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            AppointmentDTO appointmentDTO = null;
            while (resultSet.next()) {
                appointmentDTO=new AppointmentDTO(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );

            }
            return appointmentDTO;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int appointmentSave(AppointmentDTO appointmentDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into Appointment values (?,?,?,?,?)");
            pstm.setObject(1,appointmentDTO.getId());
            pstm.setObject(2,appointmentDTO.getPrice());
            pstm.setObject(3,appointmentDTO.getDate());
            pstm.setObject(4,appointmentDTO.getTime());
            pstm.setObject(5,appointmentDTO.getCustomerId());

            int i =pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteAppointment(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from Appointment where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateAppointment(AppointmentDTO appointmentDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update Appointment set price=?,date=?,time=?,customerID=? where id=?");
            pstm.setObject(1,appointmentDTO.getPrice());
            pstm.setObject(2,appointmentDTO.getDate());
            pstm.setObject(3,appointmentDTO.getTime());
            pstm.setObject(4,appointmentDTO.getCustomerId());
            pstm.setObject(5,appointmentDTO.getId());

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Appointment");
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
