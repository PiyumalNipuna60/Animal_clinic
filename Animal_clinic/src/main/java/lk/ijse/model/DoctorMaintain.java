package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.DoctorMaintainDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorMaintain {
    public static ArrayList<DoctorMaintainDTO> getAll() {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from Doctor");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<DoctorMaintainDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new DoctorMaintainDTO(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5),
                                resultSet.getBytes(6),
                                resultSet.getString(7)));
            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteDoctorMaintain(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from Doctor where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateDoctorMaintain(DoctorMaintainDTO doctorMaintainDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update Doctor set name=?,description=?,email=?,contactNo=?,image=?,userId=? where id=?");
            pstm.setObject(7,doctorMaintainDTO.getId());
            pstm.setObject(1,doctorMaintainDTO.getName());
            pstm.setObject(2,doctorMaintainDTO.getDiscription());
            pstm.setObject(3,doctorMaintainDTO.getEmail());
            pstm.setObject(4,doctorMaintainDTO.getPhone());
            pstm.setObject(5,doctorMaintainDTO.getImage());
            pstm.setObject(6,doctorMaintainDTO.getUserID());

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int doctorMaintainSave(DoctorMaintainDTO doctorMaintainDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into Doctor values (?,?,?,?,?,?,?)");
            pstm.setObject(1,doctorMaintainDTO.getId());
            pstm.setObject(2,doctorMaintainDTO.getName());
            pstm.setObject(3,doctorMaintainDTO.getDiscription());
            pstm.setObject(4,doctorMaintainDTO.getEmail());
            pstm.setObject(5,doctorMaintainDTO.getPhone());
            pstm.setObject(6,doctorMaintainDTO.getImage());
            pstm.setObject(7,doctorMaintainDTO.getUserID());

            int i =pstm.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DoctorMaintainDTO searchDoctorMaintain(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Doctor where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            DoctorMaintainDTO doctorMaintainDTO = null;
            while (resultSet.next()) {
                doctorMaintainDTO = new DoctorMaintainDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getBytes(6),
                        resultSet.getString(7));
            }
            return doctorMaintainDTO;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Doctor");
            ResultSet rst = pstm.executeQuery();
            int count=0;
            while (rst.next()){
                count+=1;
            }

            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
