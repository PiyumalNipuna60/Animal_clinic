package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.AnimalDTO;
import lk.ijse.dto.DoctorMaintainDTO;
import lk.ijse.model.AnimalModel;
import lk.ijse.model.DoctorMaintain;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class AnimalFormController {
    public TextField txtSearch;
    public ScrollPane scrollPane;
    public VBox vboxName;
    public VBox vboxName2;
    public VBox vboxName3;

    public static AnimalFormController animalFormController;
    public Label lblDate1;
    public Label lblTime1;

    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AnimalMaintainForm.fxml")))));
        stage.show();
    }

    public void initialize() {
        animalFormController=this;
        try {
            setData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        generateRealTime();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void generateRealTime() {
        lblDate1.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblTime1.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void clearScrollPaneContent() {
        vboxName.getChildren().clear();
        vboxName2.getChildren().clear();
        vboxName3.getChildren().clear();
    }


    public void setData() throws IOException {
        clearScrollPaneContent();
        AnimalModel animalModel = new AnimalModel();
        ArrayList<AnimalDTO> all = animalModel.getAll();
        for (int i = 0; i < all.size(); i++) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/test.fxml"));

            TestController customerRowFormController = new TestController();
            fxmlLoader.setController(customerRowFormController);

            Node node = fxmlLoader.load();

            customerRowFormController.setDocImage(all.get(i).getImage());
            customerRowFormController.setTxtName(all.get(i).getName());
            customerRowFormController.setTxtDescription(all.get(i).getCustomerId());

            if (i % 3 == 0) {
                setVBoxAndAction(vboxName, node, customerRowFormController);
                vboxName.setSpacing(20);
            }
            if (i % 3 == 1) {
                setVBoxAndAction(vboxName2, node, customerRowFormController);
                vboxName2.setSpacing(20);
            }
            if (i % 3 == 2) {
                setVBoxAndAction(vboxName3, node, customerRowFormController);
                vboxName3.setSpacing(20);
            }

        }
    }

    private void setVBoxAndAction(VBox vbox, Node node, TestController controller) {
        Button btnSelect = controller.getBtnSelect();
        btnSelect.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AnimalPopUpForm.fxml")))));
                AnimalPopUpFormController.animalPopUpFormController.setID(controller.getTxtName(),controller.getTxtDescription());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.show();
        });
        VBox vBox1 = new VBox(node);
        vbox.getChildren().add(vBox1);
    }

    public void searchOnAction(ActionEvent actionEvent) {
    }

    public void mainSearchOnAction(ActionEvent actionEvent) {
    }
}
