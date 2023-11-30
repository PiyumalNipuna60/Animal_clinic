package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardForm implements Initializable {
    public AnchorPane mainPane;
    public Label lblMainName;
    public Pane secondPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUi("AdmindashBoardForm2");
    }

    public void btnDashBoardOnAction(ActionEvent actionEvent) {
        setUi("AdmindashBoardForm2");
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) {
        setUi("CustomerForm");
    }

    public void btnAnimalOnAction(ActionEvent actionEvent) {
        setUi("AnimalForm");
    }

    public void btnDoctorOnAction(ActionEvent actionEvent) {
        setUi("DoctorForm");
    }

    public void btnAppointmentOnAction(ActionEvent actionEvent) {

        setUi("AppointmentForm");
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) {
        setUi("SupplierForm");
    }

    public void btnScheduleOnAction(ActionEvent actionEvent) {
        setUi("ScheduleForm");
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
        setUi("OrderForm");
    }

    public void btnItemOnAction(ActionEvent actionEvent) {
        setUi("ItemForm");
    }

    public void txtSearchOnAction(InputMethodEvent inputMethodEvent) {
        setUi("SearchForm");
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));
        stage.show();
        Stage window = (Stage) mainPane.getScene().getWindow();
        window.close();
    }


    public void btnSPAOnAction(MouseEvent mouseEvent) {
    }

    public void btnSPAOnAction2(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/spa/MainSpaDashboardForm.fxml"))));
        stage.show();
        Stage window = (Stage) mainPane.getScene().getWindow();
        window.close();
    }


    void setUi(String url){
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/"+url+".fxml"));
            secondPane.getChildren().setAll(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
