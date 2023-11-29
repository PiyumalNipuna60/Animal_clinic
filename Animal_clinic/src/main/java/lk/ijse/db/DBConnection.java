package lk.ijse.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection con;
    private static DBConnection dbConnection;

    public DBConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AnimalClinic","root","1234");
    }
    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null){
            return dbConnection = new DBConnection();
        }
        else
            return dbConnection;
    }
    public Connection getConnection() {
        return con;
    }
}