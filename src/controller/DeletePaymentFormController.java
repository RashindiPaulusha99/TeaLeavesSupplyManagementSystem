package controller;

import controller.BusinessLogicControllers.PaymentController;
import SendData.SendPaymentDataToTable;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Supply;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeletePaymentFormController implements Initializable {

    public ComboBox<String> cmbPaymentId;
    public TextField txtSupplierId;
    public TableView<Supply> tblPayment;
    public TextField txtPayment;
    public JFXButton btnDelete;

    private SendPaymentDataToTable send;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblPayment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("supplyId"));
        tblPayment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("supplyDate"));
        tblPayment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("supplyTime"));
        tblPayment.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("weight"));
        tblPayment.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        List<String> ids = null;
        try {
            ids = new PaymentController().PaymentIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbPaymentId.getItems().addAll(ids);

        cmbPaymentId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                txtSupplierId.setText(new PaymentController().getSupplierId(newValue));
                loadDataToTable(txtSupplierId.getText());
                txtPayment.setText(String.valueOf(new PaymentController().getPaymentDetail(newValue).get(0).getTotalPayment()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void loadDataToTable(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Supply> supplies = new PaymentController().takeSupplyDetail(id);
        ObservableList<Supply> obList = FXCollections.observableArrayList();

        supplies.forEach(supply -> {
            obList.add(new Supply(supply.getSupplyId(), supply.getSupplierId(), supply.getSupplyDate(), supply.getSupplyTime(), supply.getWeight(), supply.getTotalCost(), supply.getTransportCharge()));
        });
        tblPayment.setItems(obList);
    }

    public void deletePaymentOnAction(ActionEvent event) throws IOException {
        btnDelete.setOnAction((events) -> {
            ButtonType yes= new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no= new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Payment?",yes,no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no)==yes){

                try {
                    if (new PaymentController().deletePayment(cmbPaymentId.getValue())) {
                        new Alert(Alert.AlertType.CONFIRMATION,"Delete Successful.").showAndWait();
                        tblPayment.getItems().clear();
                        txtPayment.clear();
                        txtSupplierId.clear();
                        send.loadData();
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Try Again.").show();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }

            }else{
            }
        });
    }

    public void setData(SendPaymentDataToTable send){
        this.send = send;
    }

}
