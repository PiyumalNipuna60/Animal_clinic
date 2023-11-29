package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.dto.ItemDTO;
import lk.ijse.model.ItemModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ItemFormController {
    public TextField txtID;
    public TextField txtDescription;
    public TextField txtPrice;
    public TextField txtCount;
    public TableColumn colID;
    public TableColumn colDescription;
    public TableColumn colPrice;
    public TableColumn colCount;
    public TableView tblItem;

    public static ItemFormController itemFormController;

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        loadData();

        itemFormController=this;
    }
    public void loadData(){
        ItemModel item=new ItemModel();
        ArrayList<ItemDTO> all= item.getAll();

        tblItem.setItems(FXCollections.observableArrayList(all));
    }

    public void txtSerachOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        ItemModel item=new ItemModel();
        ItemDTO itemDTO = item.searchItem(id);

        if (itemDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();

        }else {
            txtDescription.setText(itemDTO.getDescription());
            txtPrice.setText(String.valueOf(itemDTO.getPrice()));
            txtCount.setText(String.valueOf(itemDTO.getCount()));

        }
    }

    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ItemMaintainForm.fxml")))));
        stage.show();
    }

}
