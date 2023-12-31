package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.UserModel;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane mainPane;
    public TextField txtUserName;
    public TextField txtPassword;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String name = txtUserName.getText();
        String password = txtPassword.getText();

        UserModel userModel = new UserModel();
        int i = userModel.checkUserNamePassword(name, password);
        if (i==1){
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"))));
            stage.show();
            Stage window = (Stage) mainPane.getScene().getWindow();
            window.close();
        }else {
            new Alert(Alert.AlertType.ERROR,"Wrong userName And Password. Try Again..!").show();
        }
    }

    public void btnSignInOnAction(MouseEvent mouseEvent) {
        System.out.println("no create");
    }
}
