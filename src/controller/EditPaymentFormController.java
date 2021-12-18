package controller;

import controller.BusinessLogicControllers.PaymentController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Payment;
import util.Validation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditPaymentFormController implements Initializable {
    public AnchorPane editPaymentContext;
    public TextField txtFertilizerQty;
    public TextField txtFertilizerPrice;
    public TextField txtTeaPaQty;
    public TextField txtPacketPrice;
    public TextField txtAdvance;
    public TextField txtDiscount;
    public JFXButton btnUpdate;
    public Label lblSupplierId;
    public Label lblPaymentDate;
    public Label lblPaymentTime;
    public ComboBox<String> cmbPaymentId;

    double trans = 0;
    double totalCostForSupply = 0;

    LinkedHashMap<TextField, Pattern> mapFertilizerQty = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapFertilizerPrice = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapTeaPaQty = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapPacketPrice = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapAdvance = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapDiscount = new LinkedHashMap();

    Pattern compile_FertilizerQty = Pattern.compile("^[0-9]{1,5}$");
    Pattern compile_FertilizerPrice = Pattern.compile("^[0-9]{1,6}?([.][0-9]{2})?$");
    Pattern compile_TeaPaQty = Pattern.compile("^[0-9]{1,2}$");
    Pattern compile_PacketPrice = Pattern.compile("^[0-9]{1,3}?([.][0-9]{2})?$");
    Pattern compile_Advance = Pattern.compile("^[0-9]{1,6}?([.][0-9]{2})?$");
    Pattern compile_Discount = Pattern.compile("^[0-9]{1,3}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            cmbPaymentId.getItems().addAll(new PaymentController().PaymentIds());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbPaymentId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<Payment> paymentDetail = null;
            try {
                paymentDetail = new PaymentController().getPaymentDetail(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            lblSupplierId.setText(paymentDetail.get(0).getSupplierId());
            lblPaymentDate.setText(paymentDetail.get(0).getPaymentDate());
            lblPaymentTime.setText(paymentDetail.get(0).getPaymentTime());
            txtTeaPaQty.setText(String.valueOf(paymentDetail.get(0).getTeaPacketQty()));
            txtPacketPrice.setText(String.valueOf(paymentDetail.get(0).getTeaPacketUnitPrice()));
            txtFertilizerQty.setText(String.valueOf(paymentDetail.get(0).getFertilizerQty()));
            txtFertilizerPrice.setText(String.valueOf(paymentDetail.get(0).getFertilizerUnitPrice()));
            txtAdvance.setText(String.valueOf(paymentDetail.get(0).getAdvance()));
            txtDiscount.setText(String.valueOf(paymentDetail.get(0).getDiscount()));
            trans = paymentDetail.get(0).getTransport();
            totalCostForSupply = paymentDetail.get(0).getCostForLeaves();
        });

        setValidations();
    }

    private void setValidations() {
        mapFertilizerQty.put(txtFertilizerQty, compile_FertilizerQty);
        mapFertilizerPrice.put(txtFertilizerPrice, compile_FertilizerPrice);
        mapAdvance.put(txtAdvance, compile_Advance);
        mapTeaPaQty.put(txtTeaPaQty, compile_TeaPaQty);
        mapPacketPrice.put(txtPacketPrice, compile_PacketPrice);
        mapDiscount.put(txtDiscount, compile_Discount);
    }

    public void fertilizerqty_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapFertilizerQty, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void fertilizerprice_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapFertilizerPrice, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void teapqty_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapTeaPaQty, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void teapprice_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapPacketPrice, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void advance_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapAdvance, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void discount_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapDiscount, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void updatePaymentOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        double fertilizerQty = Double.parseDouble(txtFertilizerQty.getText());
        double fertilizerPrice = Double.parseDouble(txtFertilizerPrice.getText());
        double advance = Double.parseDouble(txtAdvance.getText());
        int packetQty = Integer.parseInt(txtTeaPaQty.getText());
        double packetPrice = Double.parseDouble(txtPacketPrice.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        double fertilizer = fertilizerQty * fertilizerPrice;
        double teaPacket = packetQty * packetPrice;
        double totalAmount = totalCostForSupply-(fertilizer + teaPacket + advance + trans);
        double netAmount = totalAmount-(totalAmount/100*discount);

        Payment payment = new Payment(
                cmbPaymentId.getValue(),
                lblSupplierId.getText(),
                lblPaymentDate.getText(),
                lblPaymentTime.getText(),
                teaPacket,
                fertilizer,
                advance,
                discount,
                netAmount,
                packetQty,
                packetPrice,
                fertilizerQty,
                fertilizerPrice,
                trans,
                totalCostForSupply
        );

        if (new PaymentController().updatePaymentDetail(payment)){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated.").showAndWait();
            clearTextField();
            clearTextFieldBorder();

        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again.").show();
        }
    }

    public void clearTextField(){
        lblPaymentTime.setText("");
        lblPaymentDate.setText("");
        lblSupplierId.setText("");
        txtFertilizerQty.clear();
        txtFertilizerPrice.clear();
        txtPacketPrice.clear();
        txtTeaPaQty.clear();
        txtDiscount.clear();
        txtAdvance.clear();
    }

    public void clearTextFieldBorder(){
        txtFertilizerQty.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtFertilizerPrice.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtPacketPrice.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtTeaPaQty.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtDiscount.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtAdvance.getParent().setStyle("-fx-border-color:  #a4b0be");
    }

}
