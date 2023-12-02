package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import lk.ijse.dto.InjectionDTO;
import lk.ijse.model.AnimalModel;
import lk.ijse.model.InjectionModel;

import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AnimalPopUpFormController {
    static String id = "";
    public static AnimalPopUpFormController animalPopUpFormController;
    public Label lblAnimalId;
    public VBox vboxName;
    public Label lblAnimalName;
    public Label lblAnimalAge;
    public Label lblAnimalType;
    public Label lblCusId;
    public Label lblCusName;
    public ComboBox txtState;
    public TextField txtId;
    public TextField txtName;
    public TextField txtNameNumOfTime;
    public TextField txtNameCatogery;
    public DatePicker endDate;
    public DatePicker startDate;
    public Label lblOldMed;

    public static String AnimalId = "";
    public static String AnimalName = "";
    public static String AnimalAge = "";
    public static String categories = "";
    public static String customerId = "";
    public static String customerName = "";
    public static byte[] image;
    public ImageView docImage;

    public void initialize() {
        animalPopUpFormController = this;
        loadCombo();
    }

    private void loadCombo() {
        ObservableList obList = FXCollections.observableArrayList();
        obList.add("Hold");
        obList.add("Complete");
        obList.add("Stop");
        obList.add("pending");
        txtState.setItems(obList);
    }

    public static void setID(String animalName, String txtDescription) {
        try {
            ResultSet animalDetails = AnimalModel.getAnimalDetails(animalName, txtDescription);
            if (animalDetails.next()) {
                AnimalId = animalDetails.getString(1);
                AnimalName = String.valueOf(animalDetails.getObject(2));
                AnimalAge = String.valueOf(animalDetails.getObject(3));
                categories = String.valueOf(animalDetails.getObject(4));
                customerId = String.valueOf(animalDetails.getObject(6));
                customerName = String.valueOf(animalDetails.getObject(7));
                image = animalDetails.getBytes(5);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void textFieldsKeyReleasesd(KeyEvent keyEvent) {
    }

    public void btnCheckOnAction(ActionEvent actionEvent) {
        String id = lblAnimalId.getText();
        if (id != null) {
            try {
                InjectionModel injectionModel = new InjectionModel();
                ArrayList<InjectionDTO> search = injectionModel.search(lblAnimalId.getText());
                String all = "";
                for (int i = 0; i < search.size(); i++) {
                    all = search.get(i).getName() + ", ";
                }
                lblOldMed.setText(all);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Load Animal ID..!").show();
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String numOfTime = txtNameNumOfTime.getText();
        String catogery = txtNameCatogery.getText();
        String state = String.valueOf(txtState.getValue());
        String startDate = String.valueOf(this.startDate.getValue());
        String endDate = String.valueOf(this.endDate.getValue());

        try {
            InjectionModel injectionModel = new InjectionModel();
            int save = injectionModel.Save(new InjectionDTO(id, name, numOfTime, catogery, state, startDate, endDate, lblAnimalId.getText()));
            if (save != 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Save Details..!").show();
                clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String numOfTime = txtNameNumOfTime.getText();
        String catogery = txtNameCatogery.getText();
        String state = String.valueOf(txtState.getValue());
        String startDate = String.valueOf(this.startDate.getValue());
        String endDate = String.valueOf(this.endDate.getValue());

        try {
            InjectionModel injectionModel = new InjectionModel();
            int save = injectionModel.update(new InjectionDTO(id, name, numOfTime, catogery, state, startDate, endDate, lblAnimalId.getText()));
            if (save != 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Update Details..!").show();
                clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            InjectionModel injectionModel = new InjectionModel();
            int save = injectionModel.delete(id);
            if (save != 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Delete Details..!").show();
                clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clear() {
        txtId.clear();
        txtName.clear();
        txtNameNumOfTime.clear();
        txtNameCatogery.clear();
        txtState.getSelectionModel().clearSelection();
    }

    public void btnLoadDataOnAction(ActionEvent actionEvent) {
        lblAnimalId.setText(AnimalId);
        lblAnimalAge.setText(AnimalAge);
        lblAnimalName.setText(AnimalName);
        lblAnimalType.setText(categories);
        lblCusId.setText(customerId);
        lblCusName.setText(customerName);

        Image images = new Image(new ByteArrayInputStream(image));
        docImage.setImage(images);
        docImage.setPreserveRatio(false);

    }

    public void txtNameOnAction(ActionEvent actionEvent) {
        String name = txtName.getText();

        try {
            InjectionModel injectionModel = new InjectionModel();
            InjectionDTO search = injectionModel.searchInjection(name);
            if (search == null) {
                new Alert(Alert.AlertType.CONFIRMATION, "Empty Data..!").show();
            } else {
                txtId.setText(search.getId());
                txtName.setText(search.getName());
                txtNameNumOfTime.setText(search.getNumber_of_times());
                txtNameCatogery.setText(search.getCategories());
                txtState.setValue(search.getState());
                startDate.setValue(LocalDate.parse(search.getStart_date()));
                endDate.setValue(LocalDate.parse(search.getEnd_date()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}