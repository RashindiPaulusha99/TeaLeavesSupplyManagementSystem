package controller;

import controller.BusinessLogicControllers.PaymentController;
import controller.BusinessLogicControllers.SupplyController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Supplier;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class PrintBillFormController implements Initializable {

    public AnchorPane billContext;
    public Label lblDate;
    public Label lblTime;
    public Label lblTotalPayment;
    public Label lblTotalWeight;
    public Label lblTotalCost;
    public Label lblTransport;
    public Label lblFertilizer;
    public Label lblTeaPacket;
    public Label lblDiscount;
    public Label lblAdvance;
    public Label lblGrossAmount;
    public Label lblDiscountPrice;
    public Label lblNetAmount;
    public Label lblSupplierId;
    public Label lblPacketQty;
    public Label lblFertilizerQty;
    public Label lblSupplierName;
    public Label lblPaymentId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
    }

    public void setData(String pId,String sId,double totalWeightForSupply, double grossAmount, double discount ) throws SQLException, ClassNotFoundException {
        lblPaymentId.setText(pId);
        lblSupplierId.setText(sId);
        lblSupplierName.setText(new PaymentController().getSupplierName(lblSupplierId.getText()));
        lblTotalCost.setText(String.valueOf(new PaymentController().getPaymentDetail(pId).get(0).getCostForLeaves()));
        lblNetAmount.setText(String.valueOf(new PaymentController().getPaymentDetail(pId).get(0).getTotalPayment()));
        lblTotalPayment.setText(String.valueOf(new PaymentController().getPaymentDetail(pId).get(0).getTotalPayment()));
        lblFertilizerQty.setText(String.valueOf(new PaymentController().getPaymentDetail(pId).get(0).getFertilizerQty()));
        lblFertilizer.setText(String.valueOf(new PaymentController().getPaymentDetail(pId).get(0).getFertilizer()));
        lblPacketQty.setText(String.valueOf(new PaymentController().getPaymentDetail(pId).get(0).getTeaPacketQty()));
        lblTeaPacket.setText(String.valueOf(new PaymentController().getPaymentDetail(pId).get(0).getTeaPacket()));
        lblTransport.setText(String.valueOf(new PaymentController().getPaymentDetail(pId).get(0).getTransport()));
        lblAdvance.setText(String.valueOf(new PaymentController().getPaymentDetail(pId).get(0).getAdvance()));
        lblDiscount.setText(String.valueOf(new PaymentController().getPaymentDetail(pId).get(0).getDiscount()));
        lblTotalWeight.setText(String.valueOf(totalWeightForSupply));
        lblGrossAmount.setText(String.valueOf(grossAmount));
        lblDiscountPrice.setText(String.valueOf(discount));
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(s.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour()+":"+currentTime.getMinute()+":"+currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))

        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void cancelOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/PaymentForm.fxml"));
        AnchorPane pane = fxmlLoader.load();
        billContext.getChildren().setAll(pane);
    }

    public void printBillOnAction(ActionEvent event) {
        JasperDesign design = null;
        try {
            design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Reports/Bill.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            String paymentId = lblPaymentId.getText();
            String supplierId = lblSupplierId.getText();
            Supplier supplier = new SupplyController().searchSDate(lblSupplierId.getText());
            String supplierAddress = supplier.getSupplierAddress();
            String supplierContact = supplier.getSupplierContact();
            String supplierName = lblSupplierName.getText();
            double wholeWeight = Double.parseDouble(lblTotalWeight.getText());
            double wholeCost = Double.parseDouble(lblTotalCost.getText());
            int packetQty = Integer.parseInt(lblPacketQty.getText());
            double packet = Double.parseDouble(lblTeaPacket.getText());
            double fertilizerQty = Double.parseDouble(lblFertilizerQty.getText());
            double fertilizer = Double.parseDouble(lblFertilizer.getText());
            double transport = Double.parseDouble(lblTransport.getText());
            double advance = Double.parseDouble(lblAdvance.getText());
            double discount = Double.parseDouble(lblDiscount.getText());
            double gross = Double.parseDouble(lblGrossAmount.getText());
            double reduce = Double.parseDouble(lblDiscountPrice.getText());
            double netAmount = Double.parseDouble(lblNetAmount.getText());

            HashMap map = new HashMap();
            map.put("billNo",paymentId);
            map.put("supplierId",supplierId);
            map.put("supplierName",supplierName);
            map.put("supplierAddress",supplierAddress);
            map.put("supplierContact",supplierContact);
            map.put("suppliedTeaQty",wholeWeight);
            map.put("totalCostForLeaves",wholeCost);
            map.put("fertilizerQty",fertilizerQty);
            map.put("fertilizerPrice",fertilizer);
            map.put("teaPacketQty",packetQty);
            map.put("packetPrice",packet);
            map.put("transport",transport);
            map.put("advance",advance);
            map.put("discount",discount);
            map.put("grossAmount",gross);
            map.put("reducePrice",reduce);
            map.put("netAmount",netAmount);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JREmptyDataSource());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
