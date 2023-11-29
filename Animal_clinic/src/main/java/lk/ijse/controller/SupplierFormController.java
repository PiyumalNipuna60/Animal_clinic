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
import lk.ijse.dto.SupplierDTO;
import lk.ijse.model.Supplier;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class SupplierFormController {
    public TextField txtID;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtPhone;
    public TableColumn colID;
    public TableView tabSupplier;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colPhone;
    public Label lblDate;
    public Label lblTime;

    static SupplierFormController supplierFormController;

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        supplierFormController=this;

        generateRealTime();
        loadData();
    }
    public void loadData(){
        Supplier supplier=new Supplier();
        ArrayList<SupplierDTO> all=supplier.getAll();

        tabSupplier.setItems(FXCollections.observableArrayList(all));
    }
    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        Supplier supplier=new Supplier();
        SupplierDTO supplierDTO=supplier.searchSupplier(id);

        if (supplierDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();

        }else {
            txtName.setText(supplierDTO.getName());
            txtEmail.setText(supplierDTO.getEmail());
            txtPhone.setText(String.valueOf(supplierDTO.getPhone()));
        }
    }


    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SupplierMaintainForm.fxml")))));
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
