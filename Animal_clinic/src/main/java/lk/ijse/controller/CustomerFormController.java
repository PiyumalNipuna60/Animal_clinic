package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.model.CustomerModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerFormController {
    public TextField txtName;
    public TableColumn<Object, Object> colId;

    public TableColumn<Object, Object> colName;
    public TableColumn<Object, Object> colAddress;
    public TableColumn<Object, Object> colPhone;
    public TableColumn<Object, Object> colEmail;
    public TableColumn<Object, Object> colUserId;
    public TableView<CustomerDTO> tblCustomer;
    public TextField txtSearch;
    public Label lblDate;
    public Label lblTime;

    static CustomerFormController customerFormController;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        customerFormController=this;

        loadData();
        generateRealTime();
    }


    public void loadData(){
        CustomerModel customer=new CustomerModel();
        ArrayList<CustomerDTO> all= customer.getAll();
        tblCustomer.getItems().setAll(FXCollections.observableArrayList(all));
    }


    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomerMaintainForm.fxml")))));
        stage.show();
    }

    private void generateRealTime() {
        lblDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}
