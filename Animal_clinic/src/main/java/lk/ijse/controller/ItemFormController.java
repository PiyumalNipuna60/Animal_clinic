package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.ItemDTO;
import lk.ijse.model.ItemModel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        loadData();
        generateRealTime();
        itemFormController=this;
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

    public void searchOnAction(ActionEvent actionEvent) {

    }
}
