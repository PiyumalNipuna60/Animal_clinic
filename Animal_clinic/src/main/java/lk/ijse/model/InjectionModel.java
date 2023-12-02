package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.InjectionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class InjectionModel {

    public ArrayList<InjectionDTO> search(String id) throws SQLException {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from injection where animalId=?");
            pstm.setObject(1,id);

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<InjectionDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new InjectionDTO(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8)));

            }
            return object;

    }

    public static ArrayList<InjectionDTO> getAll() throws SQLException {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement("select * from injection");

            ResultSet resultSet=pstm.executeQuery();
            ArrayList<InjectionDTO> object=new ArrayList<>();

            while (resultSet.next()){
                object.add(
                        new InjectionDTO(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8)));
            }
            return object;
    }

    public static boolean existCustomer(String id) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT id FROM customer WHERE id=?");
            pstm.setString(1, id);
            return pstm.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public InjectionDTO searchInjection(String id) throws SQLException {
            Connection connection= DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from injection where name=?");
            pstm.setObject(1, id);

            ResultSet resultSet = pstm.executeQuery();

            InjectionDTO DTO = null;
            while (resultSet.next()) {
                DTO = new InjectionDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                       );

            }
            return DTO;
    }

    public int Save(InjectionDTO DTO) throws SQLException {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("insert into injection values (?,?,?,?,?,?,?,?)");
            pstm.setObject(1,DTO.getId());
            pstm.setObject(2,DTO.getName());
            pstm.setObject(3,DTO.getNumber_of_times());
            pstm.setObject(4,DTO.getCategories());
            pstm.setObject(5,DTO.getState());
            pstm.setObject(6,DTO.getStart_date());
            pstm.setObject(7,DTO.getEnd_date());
            pstm.setObject(8,DTO.getAnimalId());

            int i =pstm.executeUpdate();
            return i;
    }

    public int update(InjectionDTO DTO) throws SQLException {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm= connection.prepareStatement("update injection set name=?,number_of_times=?,categories=?, state=?, Start_date=?, end_date=?, animalId=? where id=?");
            pstm.setObject(1,DTO.getName());
            pstm.setObject(2,DTO.getNumber_of_times());
            pstm.setObject(3,DTO.getCategories());
            pstm.setObject(4,DTO.getState());
            pstm.setObject(5,DTO.getStart_date());
            pstm.setObject(6,DTO.getEnd_date());
            pstm.setObject(7,DTO.getAnimalId());
            pstm.setObject(8,DTO.getId());

            int i = pstm.executeUpdate();
            return i;
    }

    public int delete(String id) throws SQLException {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("delete from injection where id=?");
            pstm.setObject(1,id);

            int i = pstm.executeUpdate();
            return i;
    }

    public int getCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from injection;");
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

    public ResultSet getId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from injection;");
            ResultSet rst = pstm.executeQuery();

            return rst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet serchANDSendMail() throws SQLException {
        LocalDate futureDate = LocalDate.now().plusMonths(1);
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("select Customer.name,Customer.email,Animal.name,Animal.categories,injection.end_date from Customer inner join Animal on Customer.id=Animal.customerId inner join injection on Animal.id=injection.animalId where injection.end_date=? group by Animal.id");
        pstm.setObject(1, futureDate);

        ResultSet resultSet = pstm.executeQuery();
        return resultSet;
    }
}
