package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.AnimalDTO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.model.AnimalModel;
import lk.ijse.model.CustomerModel;
import lk.ijse.util.ValidationUtil;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AnimalMaintainController {
    public ComboBox <String> txtCustomerID;
    public TextField txtType;
    public TextField txtAge;
    public TextField txtName;
    public TextField txtID;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAge;
    public TableColumn colType;
    public TableView tblAnimal;
    public ComboBox cmbCustomerId;
    public TableColumn colCustomerID;
    public TextField txtSearch;
    public ImageView docImage;
    public Button btnFileChooser;
    public TextField txtDis;
    public Label lblDate;
    public Label lblTime;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    public byte[] imageBytes;

    public Pane secondPane;

    public void initialize(){
        Pattern patternId = Pattern.compile("^(A0)[0-9]{1,5}$");
        Pattern patternName = Pattern.compile("^[A-z]{3,}$");
        Pattern patternAge = Pattern.compile("^[0-9]{1,}$");
        Pattern patternType = Pattern.compile("^[A-z]{1,8}$");

        map.put(txtID, patternId);
        map.put(txtName, patternName);
        map.put(txtAge, patternAge);
        map.put(txtType, patternType);

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        generateRealTime();
        loadData();
        loadComboboxData();
    }

    private void loadComboboxData() {
        CustomerModel customerModel = new CustomerModel();
        ArrayList<CustomerDTO> all = customerModel.getAll();
        ObservableList obList = FXCollections.observableArrayList();
        for (int i = 0; i < all.size(); i++) {
                obList.add(new String(all.get(i).getId()));
        }
        cmbCustomerId.setItems(obList);
    }

    private void loadData(){
        AnimalModel animal=new AnimalModel();
        ArrayList<AnimalDTO> all= animal.getAll();
       tblAnimal.getItems().setAll(FXCollections.observableArrayList(all));
    }

    public void txtCustomerID(ActionEvent mouseEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id= txtID.getText();
        AnimalModel animal=new AnimalModel();
        int i = animal.deleteAnimal(id);
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Animal....");
            loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Wrong...");
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        String id= txtID.getText();
        String name=txtName.getText();
        int age=Integer.parseInt(txtAge.getText()) ;
        String type= txtType.getText();
        String customerID = (String) cmbCustomerId.getValue();

        AnimalModel animal=new AnimalModel();
        int i = animal.updateAnimal(new AnimalDTO(id,name,age,type,imageBytes,customerID));
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Animal...").show();
            AnimalFormController.animalFormController.setData();
            loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something wrong...").show();
        }
    }

    public void btnSaveOnAction() throws IOException {
        String id= txtID.getText();
        String name= txtName.getText();
        int age=Integer.parseInt(txtAge.getText());
        String type= txtType.getText();
        String customerID = (String) cmbCustomerId.getValue();

        if (customerID==null){

        }else {
            try {
                AnimalModel animal=new AnimalModel();
                int i = animal.animalSave(new AnimalDTO(id,name,age,type,imageBytes,customerID));
                if (i==1){
                    new Alert(Alert.AlertType.CONFIRMATION,"Save Animal...").show();
                    AnimalFormController.animalFormController.setData();
                    loadData();
                    clear();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Wrong Animal...").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void clear() {
        txtAge.clear();
        txtID.clear();
        txtName.clear();
        txtType.clear();
        docImage.setImage(null);
        cmbCustomerId.setValue("Select Id");
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        AnimalModel animal=new AnimalModel();
        AnimalDTO animalDTO =animal.searchAnimal(id);
        if (animalDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();
        }else {
            txtID.setText(animalDTO.getId());
            txtName.setText(animalDTO.getName());
            txtAge.setText(String.valueOf(animalDTO.getAge()));
            txtType.setText(animalDTO.getType());
            cmbCustomerId.setValue(animalDTO.getCustomerId());
            imageBytes=animalDTO.getImage();

            Image image = new Image(new ByteArrayInputStream(animalDTO.getImage()));
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

    private void generateRealTime() {
        lblDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void textFieldsKeyReleasesd(KeyEvent keyEvent) throws IOException {
        ValidationUtil.Validation(map);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object respond =  ValidationUtil.Validation(map);
            if (respond instanceof TextField) {
                TextField textField = (TextField) respond;
                textField.requestFocus();
            } else {
                boolean exit = AnimalModel.existCustomer(txtID.getText());
                if (exit) {

                } else {
                    btnSaveOnAction();
                }
            }
        }
    }
}
