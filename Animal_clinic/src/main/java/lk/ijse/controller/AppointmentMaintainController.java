package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lk.ijse.dto.AppointmentDTO;
import lk.ijse.model.Appointment;
import lk.ijse.model.CustomerModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AppointmentMaintainController {

    public TextField txtID;

    public TextField txtPrice;
    public TextField txtTime;
    public DatePicker txtDate;
    public ComboBox cmbCustomerId;

    public void initialize() {
        loadComboBoxData();
    }

    private void loadComboBoxData() {
        CustomerModel customerModel = new CustomerModel();
        ResultSet result = customerModel.getCustomerName();
        try {
            ObservableList obList = FXCollections.observableArrayList();
            while (result.next()) {
                obList.add(result.getString(1));
            }
            cmbCustomerId.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        int price = Integer.parseInt(txtPrice.getText());
        String date = String.valueOf(txtDate.getValue());
        String time = txtTime.getText();
        String customerID = (String) cmbCustomerId.getValue();

        Appointment appointment = new Appointment();
        int i = appointment.appointmentSave(new AppointmentDTO(id, price, date, time, customerID));
        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Save Appointment...").show();
            AppointmentFormController.appointmentFormController.loadData();
            clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Wrong Appointment...").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        int price = Integer.parseInt(txtPrice.getText());
        String date = String.valueOf(txtDate.getValue());
        String time = txtTime.getText();
        String customerID = String.valueOf(cmbCustomerId.getValue());

        Appointment appointment = new Appointment();
        int i = appointment.updateAppointment(new AppointmentDTO(id, price, date, time, customerID));
        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Update Animal...").show();
            AppointmentFormController.appointmentFormController.loadData();
            clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something wrong...").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        Appointment appointment = new Appointment();
        int i = appointment.deleteAppointment(id);
        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Delete Appointment....");
            AppointmentFormController.appointmentFormController.loadData();
            clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something Wrong...");
        }
    }

    private void clear() {
        txtID.clear();
        txtPrice.clear();
        txtDate.setValue(LocalDate.parse(LocalDate.now().toString()));
        txtTime.clear();
        cmbCustomerId.setValue("Select Id");
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        Appointment appointment = new Appointment();
        AppointmentDTO appointmentDTO = appointment.searchAppointment(id);

        if (appointmentDTO == null) {
            new Alert(Alert.AlertType.ERROR, "Empty value...").show();
        } else {
            txtTime.setText(String.valueOf(appointmentDTO.getTime()));
            txtDate.setValue(LocalDate.parse(appointmentDTO.getDate()));
            txtTime.setText(appointmentDTO.getTime());
            cmbCustomerId.setValue(appointmentDTO.getCustomerId());
            txtPrice.setText(String.valueOf(appointmentDTO.getPrice()));
        }
    }
}
