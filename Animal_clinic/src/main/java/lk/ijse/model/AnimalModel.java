package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.AnimalDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnimalModel {
    public static ArrayList<AnimalDTO> getAll() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from Animal");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<AnimalDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new AnimalDTO(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getInt(3),
                                resultSet.getString(4),
                                resultSet.getBytes(5),
                                resultSet.getString(6)));

            }
            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existCustomer(String id) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT id FROM Animal WHERE id=?");
            pstm.setString(1, id);
            return pstm.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static ResultSet getAnimalDetails(String animalName, String txtDescription) throws SQLException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("select Animal.*,Customer.name from Customer inner join Animal on Customer.id=Animal.customerId where Animal.name=? && Animal.customerId=?");
        pstm.setObject(1, animalName);
        pstm.setObject(2, txtDescription);

        ResultSet resultSet = pstm.executeQuery();

        return resultSet;
    }

    public AnimalDTO searchAnimal(String id) {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Animal where id=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            AnimalDTO animalDTO = null;
            while (resultSet.next()) {
                animalDTO = new AnimalDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getBytes(5),
                        resultSet.getString(6));

            }
            return animalDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int animalSave(AnimalDTO animalDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into Animal values (?,?,?,?,?,?)");
            pstm.setObject(1,animalDTO.getId());
            pstm.setObject(2,animalDTO.getName());
            pstm.setObject(3,animalDTO.getAge());
            pstm.setObject(4,animalDTO.getType());
            pstm.setObject(5,animalDTO.getImage());
            pstm.setObject(6,animalDTO.getCustomerId());

            int i =pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateAnimal(AnimalDTO animalDTO) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update Animal set name=?,age=?,categories=?, image=?, customerID=? where id=?");
            pstm.setObject(1,animalDTO.getName());
            pstm.setObject(2,animalDTO.getAge());
            pstm.setObject(3,animalDTO.getType());
            pstm.setObject(4,animalDTO.getImage());
            pstm.setObject(5,animalDTO.getCustomerId());
            pstm.setObject(6,animalDTO.getId());

            int i = pstm.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteAnimal(String id) {
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from Animal where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Animal;");
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

    public ResultSet getAnimalId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Animal;");
            ResultSet rst = pstm.executeQuery();

            return rst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
