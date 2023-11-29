package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.dto.ServicesDTO;
import lk.ijse.model.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ServicesFormController {
    public TextField txtID;
    public TextField txtPrice;
    public TextField txtType;
    public TextField txtStartTime;
    public TableColumn colID;
    public TableColumn colType;
    public TableColumn colPrice;
    public TableColumn colStartTime;
    public TableColumn colEndTime;
    public TextField txtEndTime;
    public TableView tblServices;

    public void initialize(){
        colID.setCellFactory(new PropertyValueFactory<>("id"));
        colType.setCellFactory(new PropertyValueFactory<>("type"));
        colPrice.setCellFactory(new PropertyValueFactory<>("price"));
        colStartTime.setCellFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellFactory(new PropertyValueFactory<>("endTime"));

        loadData();
    }
    private void loadData(){
        Services services=new Services();
        ArrayList<ServicesDTO> all=Services.getAll();

        tblServices.setItems(FXCollections.observableArrayList(all));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

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
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Wrong...");
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        Services services=new Services();
        ServicesDTO servicesDTO=services.SearchService(id);

        if (servicesDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();

        }else {
            txtType.setText(servicesDTO.getType());
            txtPrice.setText(servicesDTO.getPrice());
            txtStartTime.setText(servicesDTO.getStartTime());
            txtEndTime.setText(String.valueOf(servicesDTO.getEndTime()));
        }
    }

    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ServicesMaintainForm.fxml")))));
        stage.show();
    }

}
