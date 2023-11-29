package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.ScheduleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Schedule {
    public static ArrayList<ScheduleDTO> getAll() {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from Schedule");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<ScheduleDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new ScheduleDTO(
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

    public int scheduleSave(ScheduleDTO scheduleDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into Schedule values (?,?,?,?,?,?)");
            pstm.setObject(1,scheduleDTO.getId());
            pstm.setObject(2,scheduleDTO.getName());
            pstm.setObject(3,scheduleDTO.getTime());
            pstm.setObject(4,scheduleDTO.getDate());
            pstm.setObject(5,scheduleDTO.getDoctorId());
            pstm.setObject(6,scheduleDTO.getAppointmentId());

            int i =pstm.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ScheduleDTO searchSchedule(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Schedule where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            ScheduleDTO scheduleDTO = null;
            while (resultSet.next()) {
                scheduleDTO = new ScheduleDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6));

            }
            return scheduleDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int scheduleUpdate(ScheduleDTO scheduleDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update Schedule set name=?,time=?,date=?,doctorID=?,appointmentID=? where id=?");
            pstm.setObject(6,scheduleDTO.getId());
            pstm.setObject(1,scheduleDTO.getName());
            pstm.setObject(2,scheduleDTO.getTime());
            pstm.setObject(3,scheduleDTO.getDate());
            pstm.setObject(4,scheduleDTO.getDoctorId());
            pstm.setObject(5,scheduleDTO.getAppointmentId());

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteSchedule(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from Schedule where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
