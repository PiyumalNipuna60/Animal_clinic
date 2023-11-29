package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.dto.ItemDTO;
import lk.ijse.model.ItemModel;
import lk.ijse.util.ValidationUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ItemMaintainController {
    public TextField txtDescription;
    public TextField txtPrice;
    public TextField txtCount;
    public TextField txtID;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    public void initialize(){
        Pattern patternId = Pattern.compile("^(I0)[0-9]{1,5}$");
        Pattern patternName = Pattern.compile("^[A-z]{3,}$");
        Pattern patternPrice = Pattern.compile("^[0-9 . /]{2,}$");
        Pattern patternCount = Pattern.compile("^[0-9]{1,}$");

        map.put(txtID, patternId);
        map.put(txtDescription, patternName);
        map.put(txtPrice, patternPrice);
        map.put(txtCount, patternCount);
    }



    public void btnSaveOnAction() {
        String id= txtID.getText();
        String description= txtDescription.getText();
        int price=Integer.parseInt(txtPrice.getText());
        int count=Integer.parseInt(txtCount.getText());

        ItemModel item=new ItemModel();
        int i = item.itemSave(new ItemDTO(id,description,price,count));
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Save Item...").show();
            ItemFormController.itemFormController.loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Wrong Item...").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id= txtID.getText();
        String description= txtDescription.getText();
        int price=Integer.parseInt(txtPrice.getText());
        int count=Integer.parseInt(txtCount.getText());

        ItemModel item=new ItemModel();
        int i = item.itemUpdate(new ItemDTO(id,description,price,count));

        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Item...").show();
            ItemFormController.itemFormController.loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something wrong...").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id= txtID.getText();
        ItemModel item=new ItemModel();
        int i = item.deleteItem(id);
        if (i==1){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Item....");
            ItemFormController.itemFormController.loadData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Wrong...");
        }
    }
    void clear(){
        txtID.clear();
        txtDescription.clear();
        txtPrice.clear();
        txtCount.clear();

    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        ItemModel itemModel = new ItemModel();
        ItemDTO itemDTO = itemModel.searchItem(id);
        ArrayList<Object> all = new ArrayList<>();
        all.add(itemDTO);

        if (itemDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();
        }else {
            txtDescription.setText(itemDTO.getDescription());
            txtPrice.setText(String.valueOf(itemDTO.getPrice()));
            txtCount.setText(String.valueOf(itemDTO.getCount()));
        }

    }

    public void textFieldsKeyReleasesd(KeyEvent keyEvent) {
        ValidationUtil.Validation(map);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object respond =  ValidationUtil.Validation(map);
            if (respond instanceof TextField) {
                TextField textField = (TextField) respond;
                textField.requestFocus();
            } else {
                boolean exit = ItemModel.existItem(txtID.getText());
                if (exit) {

                } else {
                    btnSaveOnAction();
                }
            }
        }
    }
}
