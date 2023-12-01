package lk.ijse.model;


import lk.ijse.db.DBConnection;
import lk.ijse.dto.tm.cartTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
    public static boolean save(String oId, List<cartTM> cartDTOList) throws SQLException {
        for(cartTM dto : cartDTOList) {
            if(!save(oId, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, cartTM dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO order_details VALUES(?,?,?,?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oId);
        pstm.setString(2, dto.getId());
        pstm.setInt(3, dto.getQty());
        pstm.setDouble(4, dto.getTotal());

        return pstm.executeUpdate() > 0;
    }
}
