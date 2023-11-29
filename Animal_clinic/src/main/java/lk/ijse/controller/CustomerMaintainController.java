package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.model.AnimalModel;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.UserModel;
import lk.ijse.util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CustomerMaintainController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtEmail;
    public TextField txtPhone;
    public ComboBox <String> cmbId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    public void initialize(){
        Pattern patternId = Pattern.compile("^(C0)[0-9]{1,5}$");
        Pattern patternName = Pattern.compile("^[A-z]{3,}$");
        Pattern patternAddress = Pattern.compile("^[A-z . /]{2,}$");
        Pattern patternEmail = Pattern.compile("^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$");
        Pattern patternPhone = Pattern.compile("^[0-9]{10}$");

        map.put(txtId, patternId);
        map.put(txtName, patternName);
        map.put(txtAddress, patternAddress);
        map.put(txtEmail, patternEmail);
        map.put(txtPhone, patternPhone);

        loadComboBoxData();
    }

    private void loadComboBoxData() {
        UserModel userModel = new UserModel();
        ResultSet result = userModel.getUserName();
        try {
            ObservableList obList = FXCollections.observableArrayList();
            while (result.next()) {
                obList.add(new String(result.getString(1)));
            }
            cmbId.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void btnSaveOnAction() {
        String id= txtId.getText();
        String name= txtName.getText();
        String address= txtAddress.getText();
        String email=txtEmail.getText();
        String  phone=txtPhone.getText();
        String userId = String.valueOf(cmbId.getValue());

        CustomerModel customer=new CustomerModel();
        int i = customer.customerSave(new CustomerDTO(id,name,address,email,phone,userId));
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Save Customer...").show();

            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Wrong Customer...").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id= txtId.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        String email=txtEmail.getText();
        String  phone=txtPhone.getText();
        String userId = String.valueOf(cmbId.getValue());

        CustomerModel customer=new CustomerModel();
        int i = customer.updateCustomer(new CustomerDTO(id,name,address,email,phone,userId));
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Customer...").show();
            CustomerFormController.customerFormController.loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something wrong...").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id=txtId.getText();
        CustomerModel customer=new CustomerModel();
        int i = customer.deleteCustomer(id);
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Customer....").show();
            CustomerFormController.customerFormController.loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Wrong...").show();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        CustomerModel customerModel=new CustomerModel();
        CustomerDTO customerDTO =customerModel.searchCustomer(id);
        ArrayList<Object> all = new ArrayList<>();
        all.add(customerDTO);

        if (customerDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();
        }else {
            txtName.setText(customerDTO.getName());
            txtAddress.setText(customerDTO.getAddress());
            txtEmail.setText(customerDTO.getEmail());
            txtPhone.setText(customerDTO.getPhone());
            cmbId.setValue(customerDTO.getUserId());
        }
    }

    void clear(){
        txtId.clear();
        txtAddress.clear();
        txtName.clear();
        cmbId.setValue("select_id");
        txtEmail.clear();
        txtPhone.clear();
    }

    public void textFieldsKeyReleasesd(KeyEvent keyEvent) throws IOException {
        ValidationUtil.Validation(map);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object respond =  ValidationUtil.Validation(map);
            if (respond instanceof TextField) {
                TextField textField = (TextField) respond;
                textField.requestFocus();
            } else {
                boolean exit = CustomerModel.existCustomer(txtId.getText());
                if (exit) {

                } else {
                    btnSaveOnAction();
                }
            }
        }
    }
}
