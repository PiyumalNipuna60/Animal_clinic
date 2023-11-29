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
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;
import lk.ijse.dto.DoctorMaintainDTO;
import lk.ijse.model.DoctorMaintain;

public class DoctorFormController {
    public Pane secondPane;
    public ScrollPane scrollPane;
    public TextField txtSearch;
    public Label lblDate;
    public Label lblTime;
    public ImageView imageView; // Add ImageView in your FXML file and link it here
    public VBox vboxName;
    public VBox vboxName2;
    public VBox vboxName3;

    public static DoctorFormController doctorFormController;

    public void initialize() {
        doctorFormController=this;
        try {
            setData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        generateRealTime();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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

    private void clearScrollPaneContent() {
        vboxName.getChildren().clear();
        vboxName2.getChildren().clear();
        vboxName3.getChildren().clear();
    }


    public void setData() throws IOException {
        clearScrollPaneContent();
        DoctorMaintain doctorMaintain = new DoctorMaintain();
        ArrayList<DoctorMaintainDTO> all = doctorMaintain.getAll();
        for (int i = 0; i < all.size(); i++) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/test.fxml"));

            TestController cusromerRowFormController = new TestController();
            fxmlLoader.setController(cusromerRowFormController);
            TestController customerRowFormController = new TestController();
            fxmlLoader.setController(customerRowFormController);


            Node node = fxmlLoader.load();

            customerRowFormController.setDocImage(all.get(i).getImage());
            customerRowFormController.setTxtName(all.get(i).getName());
            customerRowFormController.setTxtDescription(all.get(i).getDiscription());

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
            System.out.println(controller.getTxtName());
            System.out.println(controller.getTxtDescription());
        });
        VBox vBox1 = new VBox(node);
        vbox.getChildren().add(vBox1);
    }


    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/DoctorMaintainForm.fxml")))));
        stage.show();
    }

    public void searchOnAction(ActionEvent actionEvent) {
    }
}
