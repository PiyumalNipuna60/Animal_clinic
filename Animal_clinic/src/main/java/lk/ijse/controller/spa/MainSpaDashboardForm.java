package lk.ijse.controller.spa;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainSpaDashboardForm {

    public Pane secondPane;
    public AnchorPane mainPage;

    public void initialize() {
        setUi("SpaDashBoardForm");
    }
    public void btnDashBoardOnAction(ActionEvent actionEvent) {
        setUi("SpaDashBoardForm");
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"));
            secondPane.getChildren().setAll(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void btnAppoinmentOnAction(ActionEvent actionEvent) {
        setUi("SpaAppointmentForm");
    }

    public void logOutOnAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));
        stage.show();
        Stage window = (Stage) mainPage.getScene().getWindow();
        window.close();
    }

    public void txtSearchOnAction(InputMethodEvent inputMethodEvent) {
    }



    void setUi(String url){
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/spa/"+url+".fxml"));
            secondPane.getChildren().setAll(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAdminDashBoard() throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"))));
        stage.show();
        Stage window = (Stage) mainPage.getScene().getWindow();
        window.close();
    }

    public void btncompleteAppoOnAction(ActionEvent actionEvent) {
        setUi("CompleteSpaAppointmentForm");
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) {
        setUi("EmployeeForm");
    }

    public void btnAnimalOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AnimalForm.fxml"));
            secondPane.getChildren().setAll(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
