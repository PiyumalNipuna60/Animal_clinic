package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.controller.mail.Mail;
import lk.ijse.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void emailOnAction(ActionEvent actionEvent) {
        try {
            InjectionModel injectionModel = new InjectionModel();
            ResultSet resultSet = injectionModel.serchANDSendMail();
            while (resultSet.next()){

                LocalDate futureDate = LocalDate.now().plusMonths(1);
                String msg="Dear "+resultSet.getString(1)+",\n \n * I would like to inform you that the next " +
                        "injection date of your pet named '"+resultSet.getString(3)+"' is on "+futureDate+".";

                Mail mail = new Mail(); //creating an instance of Mail class
                mail.setMsg(msg);//email message
                mail.setTo(resultSet.getString(2)); //receiver's mail
                mail.setSubject("Your "+resultSet.getString(4)+" next injection Date..!"); //email subject

                Thread thread = new Thread(mail);
                thread.start();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
