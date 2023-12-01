package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.tm.cartTM;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.ItemModel;
import lk.ijse.model.OrderModel;
import lk.ijse.model.PlaceOrderModel;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    public TableView<cartTM> tblOrder;
    public Label lblDate;
    public Label lblTime;
    public TextField txtSearch;
    public Label txtPrice;
    public ComboBox cmdCustomerId;
    public Label labCustomerID;
    public Label labTotal;
    public TextField txtCusName;
    public Label labCustomerID1;
    public Label txtTotalPrice;
    public Label txtOrderId;
    public Label labCustomerID11;
    public ComboBox cmbItemId;
    public Label labCustomerID2;
    public Label labTotal1;
    public TextField txtItemName;
    public Label labTotal11;
    public TextField txtItemQty;
    public Label labTotal12;
    public TextField txtItemPrice;
    public Label labTotal13;
    public TextField txtQtyOnHand;
    public Label labTotal131;
    private ObservableList<cartTM> obList = FXCollections.observableArrayList();


    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colQuantityAndHand.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadComboboxData();
        generateRealTime();
        generateNextOrderId();
    }

    private void generateNextOrderId() {
        try {
            String id = OrderModel.getNextOrderId();
            txtOrderId.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadComboboxData() {
        ItemModel itemModel = new ItemModel();
        ResultSet result = itemModel.getItemId();

        try {
            ObservableList obList = FXCollections.observableArrayList();
            while (result.next()) {
                obList.add(result.getString(1));
            }
            cmbItemId.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        CustomerModel customerModel = new CustomerModel();
        ResultSet rst = customerModel.getCustomerName();

        try {
            ObservableList obList1 = FXCollections.observableArrayList();
            while (rst.next()) {
                obList1.add(rst.getString(1));
            }
            cmdCustomerId.setItems(obList1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        OrderModel order=new OrderModel();
        OrderDTO orderDTO =order.searchOrder(id);

        if (orderDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();
        }else {
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

    public void btnAllOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String itemId = String.valueOf(cmbItemId.getValue());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double TotalCardPrice = Double.parseDouble(txtPrice.getText());
        String fullTotalPrice = txtTotalPrice.getText();
        double itemPrice = Double.parseDouble(txtItemPrice.getText());
        int qty = Integer.parseInt(txtItemQty.getText());

        double cartPrice=qtyOnHand*itemPrice;

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                if (colID.getCellData(i).equals(itemId)) {
                    qtyOnHand += (int) colQuantityAndHand.getCellData(i);
                    cartPrice = qtyOnHand * itemPrice;

                    obList.get(i).setQty(qtyOnHand);
                    obList.get(i).setTotal(cartPrice);

                    tblOrder.refresh();
                    calculateNetTotal();
                    return;
                }
            }
        }

        cartTM tm = new cartTM(itemId, qtyOnHand, cartPrice);

        obList.add(tm);
        tblOrder.setItems(obList);
        calculateNetTotal();

     clear();
    }

    private void clear() {
        txtItemQty.clear();
        txtItemName.clear();
        cmbItemId.getSelectionModel().clearSelection();
        txtItemPrice.clear();
        txtQtyOnHand.clear();
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            double total = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        txtTotalPrice.setText(String.valueOf(netTotal));
    }

    public void cmbItemIdOnAction(ActionEvent actionEvent) {
        String id = String.valueOf(cmbItemId.getValue());
        ItemModel itemModel = new ItemModel();
        ItemDTO itemDTO = itemModel.searchItem(id);
        if (itemDTO!=null){
            int count=0;
            int cellData = 0;
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                if (colID.getCellData(i).equals(id)) {
                     cellData = (int) colQuantityAndHand.getCellData(i);
                   count=1;
                }
            }
            if (count==1){
                int i = itemDTO.getCount() - cellData;
                if (i<=0){
                    new Alert(Alert.AlertType.ERROR,"This Item not exit stock Store..!").show();
                }else {
                    txtItemQty.setText(String.valueOf(i));
                    txtItemPrice.setText(String.valueOf(itemDTO.getPrice()));
                    txtItemName.setText(itemDTO.getDescription());
                }
            }else {
                txtItemQty.setText(String.valueOf(itemDTO.getCount()));
                txtItemPrice.setText(String.valueOf(itemDTO.getPrice()));
                txtItemName.setText(itemDTO.getDescription());
            }
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        String oId = txtOrderId.getText();
        String cusId = String.valueOf(cmdCustomerId.getValue());

        List<cartTM> cartDTOList = new ArrayList<>();
        String date = lblDate.getText();
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            cartTM carttm = obList.get(i);

            cartTM dto = new cartTM(
                    carttm.getId(),
                    carttm.getQty(),
                    carttm.getTotal()
            );
            cartDTOList.add(dto);
        }

        boolean isPlaced = false;
        try {
            double total= Double.parseDouble(txtTotalPrice.getText());

            isPlaced = PlaceOrderModel.placeOrder(oId, cusId, cartDTOList,total);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error").show();
        }
    }

    public void colCustomerIDOnAction(ActionEvent actionEvent) {
        String id = String.valueOf(cmdCustomerId.getValue());
        CustomerModel customerModel = new CustomerModel();
        CustomerDTO customerDTO = customerModel.searchCustomer(id);
        txtCusName.setText(customerDTO.getName());
    }

    public void setcartTotalOnAction(ActionEvent actionEvent) {
        int itemqty = Integer.parseInt(txtItemQty.getText());
        int QtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double price = Double.parseDouble(txtItemPrice.getText());

        if (QtyOnHand>itemqty){
            new Alert(Alert.AlertType.ERROR,"Not fount qty over Stock").show();
        }else {
            double total=price*QtyOnHand;
            txtPrice.setText(String.valueOf(total));
        }
    }


    public void btnPrintOrderOnAction(ActionEvent actionEvent) {
    }
}
