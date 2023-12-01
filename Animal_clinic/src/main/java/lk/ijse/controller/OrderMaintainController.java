package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.ijse.dto.OrderDTO;
import lk.ijse.model.OrderModel;

public class OrderMaintainController {
    public TextField txtQuantityAndHand;
    public TextField txtDate;
    public TextField txtTotal;
    public ComboBox <String> txtCustomerID;
    public TextField txtID;

    public void txtCustomerID(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id= txtID.getText();
        String quantityAndHand=txtQuantityAndHand.getText();
        String date= txtDate.getText();
        int total=Integer.parseInt(txtTotal.getText()) ;
        String customerID = txtCustomerID.getValue();

        OrderModel order=new OrderModel();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id= txtID.getText();
        String quantityAndHand=txtQuantityAndHand.getText();
        String date= txtDate.getText();
        int total=Integer.parseInt(txtTotal.getText()) ;
        String customerID = txtCustomerID.getValue();

        OrderModel order=new OrderModel();
        int i = order.updateOrder(new OrderDTO(id,date,total,customerID));

        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Order...").show();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something wrong...").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id= txtID.getText();
        OrderModel order=new OrderModel();
        int i = order.deleteOrder(id);
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Order....");
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Wrong...");
        }
    }
    void clear(){
        txtID.clear();
        txtQuantityAndHand.clear();
        txtDate.clear();
        txtTotal.clear();
        txtCustomerID.setValue("");
    }
}
