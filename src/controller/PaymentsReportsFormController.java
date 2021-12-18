package controller;

import controller.BusinessLogicControllers.ReportsController;
import SendData.SendPaymentDataToTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Payment;
import view.tm.PaymentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PaymentsReportsFormController implements Initializable, SendPaymentDataToTable {

    public Label lblDate;
    public Label lblPaymentAmount;
    public TableView<PaymentTM> tblPayment;
    public AnchorPane paymentContext;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblPayment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        tblPayment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        tblPayment.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("teaPacketQty"));
        tblPayment.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("teaPacket"));
        tblPayment.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("fertilizerQty"));
        tblPayment.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("fertilizer"));
        tblPayment.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("advance"));
        tblPayment.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblPayment.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("transport"));
        tblPayment.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("costForLeaves"));
        tblPayment.getColumns().get(10).setCellValueFactory(new PropertyValueFactory<>("totalPayment"));

        loadDate();

        try {

            loadDataToTable();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    private ObservableList<PaymentTM> loadDataToTable() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> payments = new ReportsController().getPaymentDetails(lblDate.getText());
        ObservableList<PaymentTM> obList = FXCollections.observableArrayList();

        payments.forEach(e -> {
            obList.add(new PaymentTM(e.getPaymentId(),e.getSupplierId(),e.getPaymentDate(),e.getPaymentTime(),e.getTeaPacket(),e.getFertilizer(),e.getAdvance(),e.getDiscount(),e.getTotalPayment(),e.getTeaPacketQty(),e.getTeaPacketUnitPrice(),e.getFertilizerQty(),e.getFertilizerUnitPrice(),e.getTransport(),e.getCostForLeaves()));
        });
        tblPayment.setItems(obList);

        lblPaymentAmount.setText(String.valueOf(new ReportsController().countPayment(lblDate.getText())));
        return obList;
    }

    public void monthlyPaymentOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/MonthlyPaymentReportForm.fxml"));
        AnchorPane pane = loader.load();
        paymentContext.getChildren().setAll(pane);
    }

    public void annualyPaymentOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AnnuallyPaymentReportForm.fxml"));
        AnchorPane pane = loader.load();
        paymentContext.getChildren().setAll(pane);
    }

    public void editOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/EditPaymentForm.fxml"));
        Parent load = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void deleteOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/DeletePaymentForm.fxml"));
        Parent load = loader.load();
        DeletePaymentFormController controller = loader.<DeletePaymentFormController>getController();
        controller.setData(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    @Override
    public void loadData() throws SQLException, ClassNotFoundException {
        tblPayment.getItems().setAll(loadDataToTable());
        lblPaymentAmount.setText(String.valueOf(loadDataToTable()));
    }
}
