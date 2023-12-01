package lk.ijse.model;

import lk.ijse.db.DBConnection;
import lk.ijse.dto.tm.cartTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderModel {
    public static boolean placeOrder(String oId, String cusId, List<cartTM> cartDTOList, double total) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isSaved = OrderModel.saveOrder(oId, cusId, LocalDate.now(),total);
            if(isSaved) {
                boolean isUpdated = ItemModel.updateQty(cartDTOList);
                if(isUpdated) {
                    boolean isOrdered = OrderDetailModel.save(oId, cartDTOList);
                    if(isOrdered) {
                        con.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }

    }
}
