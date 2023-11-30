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
    }



    public void btnAppoinmentOnAction(ActionEvent actionEvent) {
    }

    public void logOutOnAction(MouseEvent mouseEvent) {
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
        //test
        
    }

    public void btncompleteAppoOnAction(ActionEvent actionEvent) {
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) {
    }

    public void btnAnimalOnAction(ActionEvent actionEvent) {
    }
}
