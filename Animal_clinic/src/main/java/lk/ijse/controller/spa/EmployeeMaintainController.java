package lk.ijse.controller.spa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.controller.CustomerFormController;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.EmployeeModel;
import lk.ijse.util.ValidationUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeMaintainController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtEmail;
    public TextField txtPhone;
    public ComboBox cmbAge;
    public TextField txtSalary;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    public void initialize(){
        Pattern patternId = Pattern.compile("^(E0)[0-9]{1,5}$");
        Pattern patternName = Pattern.compile("^[A-z]{3,}$");
        Pattern patternAddress = Pattern.compile("^[A-z . /]{2,}$");
        Pattern patternEmail = Pattern.compile("^[a-z0-9](\\.?[a-z0-9]){4,}@g(oogle)?mail\\.com$");
        Pattern patternPhone = Pattern.compile("^[0-9]{10}$");
        Pattern patternSalary = Pattern.compile("^[0-9]{2,}.00$");

        map.put(txtId, patternId);
        map.put(txtName, patternName);
        map.put(txtAddress, patternAddress);
        map.put(txtEmail, patternEmail);
        map.put(txtSalary, patternSalary);
        map.put(txtPhone, patternPhone);
        loadComboboxData();
    }

    private void loadComboboxData() {
        ObservableList obList = FXCollections.observableArrayList();
        for (int i = 0; i < 100; i++) {
            obList.add(i);
        }
        cmbAge.setItems(obList);
    }

    public void textFieldsKeyReleasesd(KeyEvent keyEvent) {
        ValidationUtil.Validation(map);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object respond =  ValidationUtil.Validation(map);
            if (respond instanceof TextField) {
                TextField textField = (TextField) respond;
                textField.requestFocus();
            } else {
                boolean exit = EmployeeModel.existEmployee(txtId.getText());
                if (exit) {

                } else {
                    btnSaveOnAction();
                }
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id=txtId.getText();
        EmployeeModel employeeModel = new EmployeeModel();
        int i = employeeModel.delete(id);
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Customer....").show();
            EmployeeFormController.employeeFormController.loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Wrong...").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id= txtId.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        String email=txtEmail.getText();
        String phone=txtPhone.getText();
        String age = String.valueOf(cmbAge.getValue());
        String salary = txtSalary.getText();

        EmployeeModel employeeModel = new EmployeeModel();
        int i = employeeModel.Update(new EmployeeDTO(id, name, address, age, email, phone, salary));
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Customer...").show();
            EmployeeFormController.employeeFormController.loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something wrong...").show();
        }
    }

    public void btnSaveOnAction() {
        String id= txtId.getText();
        String name= txtName.getText();
        String address= txtAddress.getText();
        String email=txtEmail.getText();
        String  phone=txtPhone.getText();
        String salary = txtSalary.getText();
        String age = String.valueOf(cmbAge.getValue());

        EmployeeModel employeeModel = new EmployeeModel();
        int i = employeeModel.Save(new EmployeeDTO(id,name,address,age,email,phone,salary));
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Save Customer...").show();
            EmployeeFormController.employeeFormController.loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Wrong Customer...").show();
        }
    }

    void clear(){
        txtId.clear();
        txtAddress.clear();
        txtName.clear();
        cmbAge.setValue("select_age");
        txtEmail.clear();
        txtPhone.clear();
        txtSalary.clear();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        EmployeeModel employeeModel = new EmployeeModel();
        EmployeeDTO employeeDTO = employeeModel.searchEmployee(id);
        ArrayList<Object> all = new ArrayList<>();
        all.add(employeeDTO);

        if (employeeDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();
        }else {
            txtName.setText(employeeDTO.getName());
            txtAddress.setText(employeeDTO.getAddress());
            txtEmail.setText(employeeDTO.getEmail());
            txtPhone.setText(employeeDTO.getContact());
            txtSalary.setText(employeeDTO.getSalary());
            cmbAge.setValue(employeeDTO.getAge());
        }
    }
}

