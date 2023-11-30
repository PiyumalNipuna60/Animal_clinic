package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.dto.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public static ArrayList<EmployeeDTO> getAll() {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from Employee");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<EmployeeDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new EmployeeDTO(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7)));

            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public EmployeeDTO searchEmployee(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Employee where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            EmployeeDTO employeeDTO = null;
            while (resultSet.next()) {
                employeeDTO = new EmployeeDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7));

            }
            return employeeDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int Save(EmployeeDTO itemDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into Employee values (?,?,?,?,?,?,?)");
            pstm.setObject(1,itemDTO.getId());
            pstm.setObject(2,itemDTO.getName());
            pstm.setObject(3,itemDTO.getAddress());
            pstm.setObject(4,itemDTO.getAge());
            pstm.setObject(5,itemDTO.getEmail());
            pstm.setObject(6,itemDTO.getContact());
            pstm.setObject(7,itemDTO.getSalary());
            int i =pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from Employee where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int Update(EmployeeDTO itemDTO) {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update Employee set name=?,address=?,age=?,email=?,contact=?,salary=? where id=?");
            pstm.setObject(7,itemDTO.getId());
            pstm.setObject(1,itemDTO.getName());
            pstm.setObject(2,itemDTO.getAddress());
            pstm.setObject(3,itemDTO.getAge());
            pstm.setObject(4,itemDTO.getEmail());
            pstm.setObject(5,itemDTO.getContact());
            pstm.setObject(6,itemDTO.getSalary());
            int i = pstm.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existEmployee(String id) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT id FROM Employee WHERE id=?");
            pstm.setString(1, id);
            return pstm.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
