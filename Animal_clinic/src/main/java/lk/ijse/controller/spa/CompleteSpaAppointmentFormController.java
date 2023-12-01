package lk.ijse.controller.spa;

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
import lk.ijse.dto.AppointmentDTO;
import lk.ijse.dto.SpaAppointmentDTO;
import lk.ijse.model.Appointment;
import lk.ijse.model.SpaAppointmentModel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class CompleteSpaAppointmentFormController {

    static CompleteSpaAppointmentFormController completeSpaAppointmentFormController;
    public Label lblDate;
    public Label lblTime;
    public TableColumn colStatus;
    public TableColumn colAnimalId;
    public TableColumn colCustomerID;
    public TableColumn colTime;
    public TableColumn colPrice;
    public TableColumn colDate;
    public TableColumn colID;
    public TableView<SpaAppointmentDTO> tblAppoinments;
    public TextField txtSearch;

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colAnimalId.setCellValueFactory(new PropertyValueFactory<>("animalId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        completeSpaAppointmentFormController=this;
        generateRealTime();
        loadData();
        generateRealTime();
    }

    public void loadData(){
        SpaAppointmentModel spaAppointmentModel = new SpaAppointmentModel();
        ArrayList<SpaAppointmentDTO> all = spaAppointmentModel.getAll();
        tblAppoinments.getItems().setAll(FXCollections.observableArrayList(all));
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtSearch.getText();
        SpaAppointmentModel spaAppointmentModel = new SpaAppointmentModel();
        SpaAppointmentDTO spaAppointmentDTO = spaAppointmentModel.searchAppointment(id);

        if (spaAppointmentDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();

        }else {
        }
    }

    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/spa/SpaAppointmentMaintainForm.fxml")))));
        stage.show();
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

    public void btnPrintOnAction(ActionEvent actionEvent) {
    }
}
