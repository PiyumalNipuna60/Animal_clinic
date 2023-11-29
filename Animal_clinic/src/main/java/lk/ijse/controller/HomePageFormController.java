package lk.ijse.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomePageFormController implements Initializable {

    public ProgressBar progressBar;
    public ImageView mainPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateImageView();
        Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        for (int i = 0; i <= 100; i++) {
                            updateProgress(i, 70);
                            Thread.sleep(50);
                        }
                        return null;
                    }
                };
                progressBar.progressProperty().bind(task.progressProperty());
                task.setOnSucceeded(event -> {
                    try {
                        Stage stage = new Stage();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));
                        stage.show();
                        Stage window = (Stage) mainPane.getScene().getWindow();
                        window.close();

                        ((Stage) progressBar.getScene().getWindow()).close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                new Thread(task).start();
            }

    private void updateImageView() {
        Image newImage = new Image(getClass().getResourceAsStream("/view/assets/image/dogRun1.gif"));
        mainPane.setImage(newImage);
    }
}
