package controller;

import controller.BusinessLogicControllers.ReportsController;
import SendData.SendPaymentDataToTable;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AnnuallyPaymentReportFormController implements Initializable , SendPaymentDataToTable {
    public AnchorPane paymentContext;
    public TableView<PaymentTM> tblPayment;
    public Label lblPaymentAmount;
    public JFXDatePicker startDate;
    public JFXDatePicker endDate;

    LocalDate EndDate = null;
    LocalDate StartDate=null;

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

        startDate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StartDate= startDate.getValue();
            }
        });

        endDate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EndDate = endDate.getValue();

                try {

                    loadDataToTable(StartDate,EndDate);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ObservableList<PaymentTM> loadDataToTable(LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        ArrayList<Payment> payments = new ReportsController().getMoAnPaymentDetails(startDate,endDate);
        ObservableList<PaymentTM> obList = FXCollections.observableArrayList();

        payments.forEach(e -> {
            obList.add(new PaymentTM(e.getPaymentId(),e.getSupplierId(),e.getPaymentDate(),e.getPaymentTime(),e.getTeaPacket(),e.getFertilizer(),e.getAdvance(),e.getDiscount(),e.getTotalPayment(),e.getTeaPacketQty(),e.getTeaPacketUnitPrice(),e.getFertilizerQty(),e.getFertilizerUnitPrice(),e.getTransport(),e.getCostForLeaves()));
        });
        tblPayment.setItems(obList);

        lblPaymentAmount.setText(String.valueOf(new ReportsController().countMoAnPayment(startDate,endDate)));
        return obList;
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

    public void dailyPaymentOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/PaymentsReportsForm.fxml"));
        AnchorPane pane = loader.load();
        paymentContext.getChildren().setAll(pane);
    }

    public void monthlyPaymentOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/MonthlyPaymentReportForm.fxml"));
        AnchorPane pane = loader.load();
        paymentContext.getChildren().setAll(pane);
    }

    @Override
    public void loadData() throws SQLException, ClassNotFoundException {
        tblPayment.getItems().setAll(loadDataToTable(StartDate,EndDate));
    }
}
