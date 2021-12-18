package controller;

import controller.BusinessLogicControllers.PaymentController;
import controller.BusinessLogicControllers.SupplyController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Payment;
import model.Supply;
import net.sf.jasperreports.engine.*;
import util.Validation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class PaymentFormController implements Initializable {
    public Label lblPaymentId;
    public JFXComboBox<String> cmbSupplierIds;
    public JFXDatePicker startDay;
    public JFXDatePicker EndDay;
    public TableView<Supply> tblPayment;
    public TextField txtFertilizerQty;
    public TextField txtFertilizerPrice;
    public TextField txtTeaPaQty;
    public TextField txtPacketPrice;
    public TextField txtDiscount;
    public TextField txtAdvance;
    public TextField txtTransport;
    public AnchorPane paymentContext;
    public JFXButton btnAdd;
    public JFXButton btnDelete;

    LocalDate endDate = null;
    LocalDate startDate = null;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    Pattern compile_FertilizerQty = Pattern.compile("^[0-9]{1,5}$");
    Pattern compile_FertilizerPrice = Pattern.compile("^[0-9]{1,6}?([.][0-9]{2})?$");
    Pattern compile_TeaPaQty = Pattern.compile("^[0-9]{1,2}$");
    Pattern compile_PacketPrice = Pattern.compile("^[0-9]{1,3}?([.][0-9]{2})?$");
    Pattern compile_Advance = Pattern.compile("^[0-9]{1,6}?([.][0-9]{2})?$");
    Pattern compile_Discount = Pattern.compile("^[0-9]{1,3}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tblPayment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("supplyId"));
        tblPayment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("supplyDate"));
        tblPayment.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("weight"));
        tblPayment.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        loadDateAndTime();

        setValidations();

        try {

            setPaymentId();

            setSupplierIds();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        startDay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startDate = startDay.getValue();
            }
        });

        EndDay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                endDate = EndDay.getValue();
            }
        });

        cmbSupplierIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {

                loadDetail(newValue, startDate, endDate);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setValidations() {
        map.put(txtFertilizerQty, compile_FertilizerQty);
        map.put(txtFertilizerPrice, compile_FertilizerPrice);
        map.put(txtAdvance, compile_Advance);
        map.put(txtTeaPaQty, compile_TeaPaQty);
        map.put(txtPacketPrice, compile_PacketPrice);
        map.put(txtDiscount, compile_Discount);
    }

    public void payment_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().ValidateTextField(map, btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    String d = null;
    String t = null;

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        d = s.format(date);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            t = currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond();
        }),
                new KeyFrame(Duration.seconds(1))

        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    double totalCostForSupply = 0;
    double totalWeightForSupply = 0;

    private void loadDetail(String id, LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        ArrayList<Supply> supplies = new PaymentController().searchSupplyDetail(id, startDate, endDate);
        ObservableList<Supply> obList = FXCollections.observableArrayList();

        supplies.forEach(supply -> {
            obList.add(new Supply(supply.getSupplyId(), supply.getSupplierId(), supply.getSupplyDate(), supply.getSupplyTime(), supply.getWeight(), supply.getTotalCost(), supply.getTransportCharge()));
        });
        tblPayment.setItems(obList);

        txtTransport.setText(String.valueOf(new PaymentController().getTotalTransportCharge(id, startDate, endDate)));
        txtTransport.setDisable(true);

        totalCostForSupply = new PaymentController().getTotalCostForSupply(id, startDate, endDate);
        totalWeightForSupply = new PaymentController().getTotalWeightForSupply(id, startDate, endDate);

        addSupplyIds(id, startDate, endDate);
    }

    String supplyIds = null;

    public void addSupplyIds(String id, LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        supplyIds = String.valueOf(new PaymentController().getSupplyIds(id, startDate, endDate));
    }

    private void setSupplierIds() throws SQLException, ClassNotFoundException {
        cmbSupplierIds.getItems().addAll(new SupplyController().searchSIds());
    }

    private void setPaymentId() throws SQLException, ClassNotFoundException {
        lblPaymentId.setText(new PaymentController().getPaymentIds());
    }

    double totalAmount = 0;
    double discountPrice = 0;
    double netAmount = 0;

    public Payment addPaymentData() {

        double fertilizerQty = Double.parseDouble(txtFertilizerQty.getText());
        double fertilizerPrice = Double.parseDouble(txtFertilizerPrice.getText());
        double advance = Double.parseDouble(txtAdvance.getText());
        int packetQty = Integer.parseInt(txtTeaPaQty.getText());
        double packetPrice = Double.parseDouble(txtPacketPrice.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        double fertilizer = fertilizerQty * fertilizerPrice;
        double teaPacket = packetQty * packetPrice;
        double transport = Double.parseDouble(txtTransport.getText());
        totalAmount = totalCostForSupply - (fertilizer + teaPacket + advance + transport);
        discountPrice = (totalAmount / 100 * discount);
        netAmount = totalAmount - discountPrice;

        txtFertilizerQty.setDisable(true);
        txtFertilizerPrice.setDisable(true);
        txtPacketPrice.setDisable(true);
        txtTeaPaQty.setDisable(true);
        txtAdvance.setDisable(true);
        txtDiscount.setDisable(true);

        Payment payment = new Payment(
                lblPaymentId.getText(),
                cmbSupplierIds.getValue(),
                d,
                t,
                teaPacket,
                fertilizer,
                advance,
                discount,
                netAmount,
                packetQty,
                packetPrice,
                fertilizerQty,
                fertilizerPrice,
                transport,
                totalCostForSupply
        );
        return payment;
    }

    public void addToPaymentOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Payment payment = addPaymentData();

        if (new PaymentController().savePaymentDetail(payment)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Payment Proceeded.").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again.").show();
        }

    }

    public void editDetailOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/EditPaymentForm.fxml"));
        Parent load = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void deleteDetailOnAction(ActionEvent event) throws IOException {
        btnDelete.setOnAction((events) -> {
            ButtonType yes= new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no= new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Payment?",yes,no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no)==yes){

                try {
                    if (new PaymentController().deletePayment(lblPaymentId.getText())) {
                        new Alert(Alert.AlertType.CONFIRMATION,"Delete Successful.").showingProperty();
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

    public void issueBillOnAction(ActionEvent event) throws JRException, SQLException, ClassNotFoundException, IOException {
        if (txtTransport.getText().isEmpty()||txtFertilizerPrice.getText().isEmpty()||txtFertilizerQty.getText().isEmpty()||txtPacketPrice.getText().isEmpty()||txtTeaPaQty.getText().isEmpty()||txtDiscount.getText().isEmpty()||txtAdvance.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Make A Payment Before Issue Bill.").show();
        }else {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/PrintBillForm.fxml"));
            AnchorPane pane = fxmlLoader.load();
            PrintBillFormController controller = fxmlLoader.<PrintBillFormController>getController();
            controller.setData(lblPaymentId.getText(), cmbSupplierIds.getValue(), totalWeightForSupply, totalAmount, discountPrice);
            paymentContext.getChildren().setAll(pane);
        }
    }
}
