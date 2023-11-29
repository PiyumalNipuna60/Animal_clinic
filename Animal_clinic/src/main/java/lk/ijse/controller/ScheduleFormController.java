package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.ScheduleDTO;
import lk.ijse.model.Schedule;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class ScheduleFormController {
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colDate;
    public TableColumn colTime;
    public TextField txtTime;
    public TextField txtID;
    public TextField txtName;
    public TextField txtDate;
    public TableView tblSchedule;
    public TableColumn colDoctorID;
    public TableColumn colAppointmentID;
    public ComboBox <String> comDoctorID;
    public ComboBox <String> comAppointmentID;

    public static ScheduleFormController scheduleFormController;
    public TextField txtSearch;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDoctorID.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        colAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));

        scheduleFormController=this;
        generateRealTime();
        loadData();
    }
    public void loadData(){
        Schedule schedule=new Schedule();
        ArrayList<ScheduleDTO> all=schedule.getAll();

        tblSchedule.setItems(FXCollections.observableArrayList(all));
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



    public void txtSearch(ActionEvent actionEvent) {
        String id = txtID.getText();
        Schedule schedule=new Schedule();
        ScheduleDTO scheduleDTO =schedule.searchSchedule(id);

        if (scheduleDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();

        }else {
            txtName.setText(scheduleDTO.getName());
            txtTime.setText(scheduleDTO.getTime());
            txtDate.setText(scheduleDTO.getDate());

        }
    }

    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ScheduleMaintainForm.fxml")))));
        stage.show();
    }

}
