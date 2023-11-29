package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.dto.DoctorMaintainDTO;
import lk.ijse.model.DoctorMaintain;
import lk.ijse.model.UserModel;
import javafx.scene.image.Image;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class DoctorMaintainController {
    public TextField txtID;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtPhone;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colPhone;
    public TableColumn colUserID;
    public ComboBox cmbUserId;
    public Pane secondPane;
    public TableView tblDoctorMaintain;
    public TableColumn colDis;
    public ImageView docImage;
    public TextField txtDis;
    public TextField txtSearch1;
    public Button btnFileChooser;

    public byte[] imageBytes;

    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        loadData();
        loadComboBoxData();



    }

    private void loadData() {
        DoctorMaintain doctorMaintain = new DoctorMaintain();
        ArrayList<DoctorMaintainDTO> all = doctorMaintain.getAll();
        tblDoctorMaintain.getItems().setAll(FXCollections.observableArrayList(all));
    }

    private void loadComboBoxData() {
        UserModel userModel = new UserModel();
        ResultSet result = userModel.getUserName();
        try {
            ObservableList obList = FXCollections.observableArrayList();
            while (result.next()) {
                obList.add(new String(result.getString(1)));
            }
            cmbUserId.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void clear() {
        txtID.clear();
        txtEmail.clear();
        txtName.clear();
        txtPhone.clear();
        txtDis.clear();
        docImage.setImage(null);
        cmbUserId.setValue("Select Id");
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {
        String id = txtID.getText();
        String name = txtName.getText();
        String discription = txtDis.getText();
        String email = txtEmail.getText();
        int phone = Integer.parseInt(txtPhone.getText());
        String userID = String.valueOf(cmbUserId.getValue());
        String contact = txtPhone.getText();

        DoctorMaintain doctorMaintain = new DoctorMaintain();
        int i = doctorMaintain.doctorMaintainSave(new DoctorMaintainDTO(id, name,discription, email, phone,imageBytes, userID));
        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Save Doctor...").show();
            DoctorFormController.doctorFormController.setData();
            clear();
            loadData();


            String date = LocalDate.now().toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String time = LocalDateTime.now().format(formatter);

            HashMap hashMap = new HashMap<>();
            hashMap.put("ID",id);
            hashMap.put("Name",name);
            hashMap.put("Discription",discription);
            hashMap.put("Email",email);
            hashMap.put("Contact",contact);
            hashMap.put("Date", date);
            hashMap.put("Time", time);
            hashMap.put("UserID",userID);

            try {
                JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/doctor/doctor.jrxml"));
                JasperReport jasperReport = JasperCompileManager.compileReport(load);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap, new JREmptyDataSource());
                JasperViewer.viewReport(jasperPrint,false);

            } catch (JRException e) {
                throw new RuntimeException(e);
            }


        } else {
            new Alert(Alert.AlertType.ERROR, "Wrong Doctor...").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        String id = txtID.getText();
        String name = txtName.getText();
        String discription = txtDis.getText();
        String email = txtEmail.getText();
        int phone = Integer.parseInt(txtPhone.getText());
        String userID = String.valueOf(cmbUserId.getValue());


        DoctorMaintain doctorMaintain = new DoctorMaintain();
        int i = doctorMaintain.updateDoctorMaintain(new DoctorMaintainDTO(id, name,discription, email, phone,imageBytes, userID));

        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Update Doctor...").show();
            DoctorFormController.doctorFormController.setData();
            clear();
            loadData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something wrong...").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws IOException {
        String id = txtID.getText();
        DoctorMaintain doctorMaintain = new DoctorMaintain();
        int i = doctorMaintain.deleteDoctorMaintain(id);
        if (i == 1) {
            new Alert(Alert.AlertType.CONFIRMATION, "Delete Doctor....");
            DoctorFormController.doctorFormController.setData();
            clear();
            loadData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something Wrong...");
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        DoctorMaintain doctorMaintain = new DoctorMaintain();
        DoctorMaintainDTO doctorMaintainDTO = doctorMaintain.searchDoctorMaintain(id);

        if (doctorMaintainDTO == null) {
            new Alert(Alert.AlertType.ERROR, "Empty value...").show();
        } else {
            txtID.setText(doctorMaintainDTO.getId());
            txtName.setText(doctorMaintainDTO.getName());
            txtDis.setText(doctorMaintainDTO.getDiscription());
            txtEmail.setText(doctorMaintainDTO.getEmail());
            txtPhone.setText(String.valueOf(doctorMaintainDTO.getPhone()));
            cmbUserId.setValue(doctorMaintainDTO.getUserID());
            imageBytes=doctorMaintainDTO.getImage();

            Image image = new Image(new ByteArrayInputStream(doctorMaintainDTO.getImage()));
            docImage.setImage(image);
            docImage.setPreserveRatio(false);
        }
    }

    public void btnFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image","*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files","*.jpg","*.png"));

        Stage stage = (Stage) secondPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        Image image = new Image(file.getPath());
        docImage.setImage(image);
        docImage.setPreserveRatio(false);
        System.out.println(file.getPath());

        imageBytes = readImageToByteArray(file);
    }

    private byte[] readImageToByteArray(File file){
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

