package lk.ijse.controller.spa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lk.ijse.controller.AppointmentFormController;
import lk.ijse.dto.AppointmentDTO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.SpaAppointmentDTO;
import lk.ijse.model.AnimalModel;
import lk.ijse.model.Appointment;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.SpaAppointmentModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SpaAppointmentMaintainController {

    public TextField txtID;

    public TextField txtPrice;
    public TextField txtTime;

    public DatePicker txtDate;
    public ComboBox cmbCustomerId;
    public TextField txtCustomerName;
    public ComboBox cmbAnimalId;
    public TextField txtState;
    public ComboBox cmbState;

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

        AnimalModel animalModel = new AnimalModel();
        ResultSet rst = animalModel.getAnimalId();

        try {
            ObservableList obList2 = FXCollections.observableArrayList();
            while (rst.next()) {
                obList2.add(rst.getString(1));
            }
            cmbAnimalId.setItems(obList2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ObservableList obList3 = FXCollections.observableArrayList();
        obList3.add("Complete");
        obList3.add("Hold");
        obList3.add("In Progress");
        cmbState.setItems(obList3);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        int price = Integer.parseInt(txtPrice.getText());
        String date = String.valueOf(txtDate.getValue());
        String time = txtTime.getText();
        String customerID = (String) cmbCustomerId.getValue();
        String animalId = String.valueOf(cmbAnimalId.getValue());
        String status = String.valueOf(cmbState.getValue());

        SpaAppointmentModel spaAppointmentModel = new SpaAppointmentModel();
        int i = spaAppointmentModel.appointmentSave(new SpaAppointmentDTO(id, price, date, time, customerID,animalId,status));
        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Save Appointment...").show();
            SpaAppointmentFormController.appointmentFormController.loadData();
            CompleteSpaAppointmentFormController.spaAppointmentFormController.loadData();
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
        String animalId = String.valueOf(cmbAnimalId.getValue());
        String status = String.valueOf(cmbState.getValue());

        SpaAppointmentModel spaAppointmentModel = new SpaAppointmentModel();
        int i = spaAppointmentModel.updateAppointment(new SpaAppointmentDTO(id, price, date, time, customerID,animalId,status));
        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Update Animal...").show();
            SpaAppointmentFormController.appointmentFormController.loadData();
            CompleteSpaAppointmentFormController.spaAppointmentFormController.loadData();
            clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something wrong...").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        SpaAppointmentModel spaAppointmentModel = new SpaAppointmentModel();
        int i = spaAppointmentModel.deleteAppointment(id);
        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Delete Appointment....");
            SpaAppointmentFormController.appointmentFormController.loadData();
            CompleteSpaAppointmentFormController.spaAppointmentFormController.loadData();
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
        SpaAppointmentModel spaAppointmentModel = new SpaAppointmentModel();
        SpaAppointmentDTO spaAppointmentDTO = spaAppointmentModel.searchAppointment(id);

        if (spaAppointmentDTO == null) {
            new Alert(Alert.AlertType.ERROR, "Empty value...").show();
        } else {
            txtTime.setText(String.valueOf(spaAppointmentDTO.getTime()));
            txtDate.setValue(LocalDate.parse(spaAppointmentDTO.getDate()));
            txtTime.setText(spaAppointmentDTO.getTime());
            cmbCustomerId.setValue(spaAppointmentDTO.getCustomerId());
            txtPrice.setText(String.valueOf(spaAppointmentDTO.getPrice()));
            cmbState.setValue(spaAppointmentDTO.getStatus());
            cmbAnimalId.setValue(spaAppointmentDTO.getAnimalId());
            cmbCustomerOnAction();
        }
    }

    public void cmbCustomerOnAction() {
        String id = String.valueOf(cmbCustomerId.getValue());
        CustomerModel customerModel = new CustomerModel();
        CustomerDTO customerDTO = customerModel.searchCustomer(id);
        txtCustomerName.setText(customerDTO.getName());
    }
}
