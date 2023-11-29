package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.model.AnimalModel;
import lk.ijse.model.Appointment;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.DoctorMaintain;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class AdminDashBoardForm2Controller {
    public Pane secondPane;
    public Label lblDate;
    public Label lblTime;
    public Label lblCustomerCount;
    public Label lblEmployeeCount;
    public Label lblAnimalCount;
    public Label lblDoctorCount;

    public void initialize() {
        generateRealTime();
        loadCount();
    }

    private void loadCount() {
        CustomerModel customerModel = new CustomerModel();
        int count = customerModel.getCount();

        DoctorMaintain doctorMaintain = new DoctorMaintain();
        int count1 = doctorMaintain.getCount();

        AnimalModel animalModel = new AnimalModel();
        int count2 = animalModel.getCount();

        Appointment appointment = new Appointment();
        int count3 = appointment.getCount();

        lblDoctorCount.setText("0"+count1);
        lblCustomerCount.setText("0"+count);
        lblAnimalCount.setText("0"+count2);
        lblEmployeeCount.setText("0"+count3);
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
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
