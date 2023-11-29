package lk.ijse.model;

import lk.ijse.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public ResultSet getUserName() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from User");
            ResultSet resultSet = pstm.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkUserNamePassword(String name, String password) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select id from user where name=? and password=?");
            pstm.setObject(1,name);
            pstm.setObject(2,password);

            ResultSet resultSet = pstm.executeQuery();
            int i=0;
            if (resultSet.next()){
                i=1;
            }
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
