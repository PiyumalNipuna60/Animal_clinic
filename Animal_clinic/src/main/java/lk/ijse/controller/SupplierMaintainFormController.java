package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.model.Supplier;

public class SupplierMaintainFormController {
    public TextField txtID;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtPhone;

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id= txtID.getText();
        String name= txtName.getText();
        String email= txtEmail.getText();
        int phone=Integer.parseInt(txtPhone.getText());


        Supplier supplier=new Supplier();
        int i = supplier.supplierSave(new SupplierDTO(id,name,email,phone));
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Save Supplier...").show();
            SupplierFormController.supplierFormController.loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Wrong Supplier...").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id= txtID.getText();
        String name= txtName.getText();
        String email= txtEmail.getText();
        int phone=Integer.parseInt(txtPhone.getText());


        Supplier supplier=new Supplier();
        int i = supplier.updateSupplier(new SupplierDTO(id,name,email,phone));

        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Update supplier...").show();
            SupplierFormController.supplierFormController.loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something wrong...").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id=txtID.getText();
        Supplier supplier=new Supplier();
        int i = supplier.deleteSupplier(id);
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Supplier....");
            SupplierFormController.supplierFormController.loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Wrong...");
        }
    }
    void clear(){
        txtID.clear();
        txtName.clear();
        txtEmail.clear();
        txtPhone.clear();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        Supplier supplier=new Supplier();
        SupplierDTO supplierDTO=supplier.searchSupplier(id);

        if (supplierDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();

        }else {
            txtName.setText(supplierDTO.getName());
            txtEmail.setText(supplierDTO.getEmail());
            txtPhone.setText(String.valueOf(supplierDTO.getPhone()));
        }
    }
}
