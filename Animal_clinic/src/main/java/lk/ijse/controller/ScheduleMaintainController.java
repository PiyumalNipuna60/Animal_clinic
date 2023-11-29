package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lk.ijse.dto.AppointmentDTO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.DoctorMaintainDTO;
import lk.ijse.dto.ScheduleDTO;
import lk.ijse.model.Appointment;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.DoctorMaintain;
import lk.ijse.model.Schedule;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleMaintainController {
    public TextField txtID;
    public TextField txtName;
    public TextField txtTime;
    public ComboBox<String> comDoctorID;
    public ComboBox<String> comAppointmentID;
    public DatePicker txtDate;
    public TextField txtDoCName;
    public TextField txtCustomerName;

    public void initialize() {
        loadComboBoxData();
    }

    private void loadComboBoxData() {
        DoctorMaintain doctorMaintain = new DoctorMaintain();
        ArrayList<DoctorMaintainDTO> all = DoctorMaintain.getAll();
        ObservableList obList = FXCollections.observableArrayList();
        for (int i = 0; i < all.size(); i++) {
            obList.add(all.get(i).getId());
        }
        comDoctorID.setItems(obList);

        Appointment appointment = new Appointment();
        ArrayList<AppointmentDTO> all1 = appointment.getAll();
        ObservableList obList2 = FXCollections.observableArrayList();
        for (int i = 0; i < all1.size(); i++) {
            obList2.add(all1.get(i).getId());
        }
        comAppointmentID.setItems(obList2);

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String name = txtName.getText();
        String time = txtTime.getText();
        String date = String.valueOf(txtDate.getValue());
        String doctorId = comDoctorID.getValue();
        String appointmentID = comAppointmentID.getValue();

        Schedule schedule = new Schedule();
        int i = schedule.scheduleSave(new ScheduleDTO(id, name, time, date, doctorId, appointmentID));
        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Save Schedule...").show();
            ScheduleFormController.scheduleFormController.loadData();
            clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Wrong Schedule...").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String name = txtName.getText();
        String time = txtTime.getText();
        String date = String.valueOf(txtDate.getValue());
        String doctorId = comDoctorID.getValue();
        String appointmentID = comAppointmentID.getValue();

        Schedule schedule = new Schedule();
        int i = schedule.scheduleUpdate(new ScheduleDTO(id, name, time, date, doctorId, appointmentID));

        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Update Schedule...").show();
            ScheduleFormController.scheduleFormController.loadData();
            clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something wrong...").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        Schedule schedule = new Schedule();
        int i = schedule.deleteSchedule(id);
        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Delete Schedule....");
            ScheduleFormController.scheduleFormController.loadData();
            clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something Wrong...");
        }
    }

    void clear() {
        String date = LocalDate.now().toString();
        txtID.clear();
        txtName.clear();
        txtTime.clear();
        txtDate.setValue(LocalDate.parse(date));
        txtCustomerName.clear();
        txtDoCName.clear();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        Schedule schedule = new Schedule();
        ScheduleDTO scheduleDTO = schedule.searchSchedule(id);

        if (scheduleDTO == null) {
            new Alert(Alert.AlertType.ERROR, "Empty value...").show();
        } else {
            txtName.setText(scheduleDTO.getName());
            txtTime.setText(scheduleDTO.getTime());
            txtDate.setValue(LocalDate.parse(scheduleDTO.getDate()));
            comDoctorID.setValue(scheduleDTO.getDoctorId());
            System.out.println(scheduleDTO.getAppointmentId());

            comAppointmentID.setValue(scheduleDTO.getAppointmentId());

            cmbAppoinmentOnAction();

            cmbOnDragEntered();

        }
    }

    public void cmbOnDragEntered() {
        String value = comDoctorID.getValue();
        DoctorMaintain doctorMaintain = new DoctorMaintain();
        DoctorMaintainDTO doctorMaintainDTO = doctorMaintain.searchDoctorMaintain(value);

        txtDoCName.setText(doctorMaintainDTO.getName());
    }

    public void cmbAppoinmentOnAction() {
        String value = comAppointmentID.getValue();

        Appointment appointment = new Appointment();
        AppointmentDTO appointmentDTO = appointment.searchAppointment(value);

        CustomerModel customerModel = new CustomerModel();
        CustomerDTO customerDTO = customerModel.searchCustomer(appointmentDTO.getCustomerId());
        txtCustomerName.setText(customerDTO.getName());
    }

    public void btnPrintOnAction(ActionEvent actionEvent) {

        String ids = txtID.getText();
        String name = txtName.getText();
        String date= String.valueOf(txtDate.getValue());
        String time = txtTime.getText();
        String docId=comDoctorID.getValue();
        String docName = txtDoCName.getText();
        String appoinmentId=comAppointmentID.getValue();
        String cusName = txtCustomerName.getText();

        HashMap hashMap = new HashMap<>();
        hashMap.put("ID",ids);
        hashMap.put("Name",name);
        hashMap.put("Date",date);
        hashMap.put("Time",time);
        hashMap.put("DoctorID",docId);
        hashMap.put("DoctorName",docName);
        hashMap.put("AppointmentID",appoinmentId);
        hashMap.put("CustomerName",cusName);

        try {
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/animalcard/AnimalCard.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap, new JREmptyDataSource());
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }
}
