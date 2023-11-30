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
import lk.ijse.dto.OrderDTO;
import lk.ijse.model.Order;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class OrderFormController {
    public TextField txtID;
    public TextField txtQuantityAndHand;
    public TextField txtDate;
    public TextField txtTotal;
    public TableColumn colID;
    public TableColumn colQuantityAndHand;
    public TableColumn colDate;
    public TableColumn colTotal;
    public TableColumn colCustomerID;
    public ComboBox <String> txtCustomerID;
    public TableView tblOrder;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        colID.setCellFactory(new PropertyValueFactory<>("id"));
        colQuantityAndHand.setCellFactory(new PropertyValueFactory<>("quantityAnHand"));
        colDate.setCellFactory(new PropertyValueFactory<>("date"));
        colTotal.setCellFactory(new PropertyValueFactory<>("total"));
        colCustomerID.setCellFactory(new PropertyValueFactory<>("customerID"));

        loadData();
    }
    private void loadData(){
        Order order=new Order();
        ArrayList<OrderDTO> all=Order.getAll();

        tblOrder.setItems(FXCollections.observableArrayList(all));
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

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        Order order=new Order();
        OrderDTO orderDTO =order.searchOrder(id);

        if (orderDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();

        }else {
            txtQuantityAndHand.setText(orderDTO.getQuantityAndHand());
            txtDate.setText(orderDTO.getDate());
            txtTotal.setText(String.valueOf(orderDTO.getTotal()));

        }
    }


    public void txtCustomerID(ActionEvent actionEvent) {
    }

    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/OrderMaintainForm.fxml")))));
        stage.show();
    }

}
