package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.dto.ServicesDTO;
import lk.ijse.model.Services;

public class ServicesMaintainController {
    public TextField txtID;
    public TextField txtPrice;
    public TextField txtType;
    public TextField txtStartTime;
    public TextField txtEndTime;

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id= txtID.getText();
        String type= txtType.getText();
        String price= txtPrice.getText();
        String startTime= txtStartTime.getText();
        String endTime= txtEndTime.getText();

        Services services=new Services();
        int i = services.saveServices(new ServicesDTO(id,type,price,startTime,endTime));

        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Save Services details...").show();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Wrong Services...").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id= txtID.getText();
        String type= txtType.getText();
        String price= txtPrice.getText();
        String startTime= txtStartTime.getText();
        String endTime= txtEndTime.getText();

        Services services=new Services();
        int i = services.updateServices(new ServicesDTO(id,type,price,startTime,endTime));

        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Services details...").show();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something wrong...").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id=txtID.getText();
        Services services=new Services();
        int i = services.deleteServices(id);
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Service detail....");
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Wrong...");
        }
    }
    void clear(){
        txtID.clear();
        txtType.clear();
        txtPrice.clear();
        txtStartTime.clear();
        txtEndTime.clear();
    }
}
