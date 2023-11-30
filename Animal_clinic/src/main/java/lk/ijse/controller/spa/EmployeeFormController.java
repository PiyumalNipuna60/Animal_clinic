package lk.ijse.controller.spa;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.EmployeeModel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class EmployeeFormController {
    public Label lblTime;
    public Label lblDate;
    public TableColumn colSalary;
    public TableColumn colAge;
    public TableColumn colPhone;
    public TableColumn colEmail;
    public TableColumn colAddress;
    public TableColumn colName;
    public TableColumn colId;
    public TableView tblEmployee;
    public TextField txtSearch;

    public static EmployeeFormController employeeFormController;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));


        employeeFormController=this;

        loadData();
        generateRealTime();
    }


    public void loadData(){
        EmployeeModel employeeModel = new EmployeeModel();
        ArrayList<EmployeeDTO> all = employeeModel.getAll();
        tblEmployee.getItems().setAll(FXCollections.observableArrayList(all));
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
    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/spa/EmployeeMaintainForm.fxml")))));
        stage.show();
    }
}
