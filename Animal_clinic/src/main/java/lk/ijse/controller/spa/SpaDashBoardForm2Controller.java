package lk.ijse.controller.spa;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SpaDashBoardForm2Controller {
    public Pane secondPane;
    public Label lblCustomerCount;
    public Label lblAppoinmentCount;
    public Label lblAnimalServiceCount;
    public Label lblEmployeeCount;
    public Label lblTime;
    public Label lblDate;
    public void initialize() {
        generateRealTime();
        loadCount();
    }

    private void loadCount() {
        CustomerModel customerModel = new CustomerModel();
        int count = customerModel.getCount();

        EmployeeModel employeeModel = new EmployeeModel();
        int count1 = employeeModel.getCount();

        AnimalModel animalModel = new AnimalModel();
        int count2 = animalModel.getCount();  // masaj table eken ganna one

        Appointment appointment = new Appointment();
        int count3 = appointment.getCount();

        lblEmployeeCount.setText("0"+count1);
        lblCustomerCount.setText("0"+count);
        lblAnimalServiceCount.setText("0"+count2);
        lblAppoinmentCount.setText("0"+count3);
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

    public void txtSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnScheduleOnAction(ActionEvent actionEvent) {

    }
}
