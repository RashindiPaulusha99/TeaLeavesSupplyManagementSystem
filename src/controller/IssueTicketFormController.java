package controller;

import controller.BusinessLogicControllers.SupplyController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Employee;
import model.Issue;
import model.Ticket;
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

public class IssueTicketFormController implements Initializable {

    public AnchorPane ticketContext;
    public Label lblInvoiceNo;
    public Label lblIssueDate;
    public Label lblIssueTime;
    public Label lblPrintDate;
    public Label lblPrintTime;
    public Label lblSupplyId;
    public Label lblWholeTeaLeavesWeight;
    public Label lblNetWeight;
    public Label lblSackQty;
    public Label lblSackWeight;
    public ComboBox<String> cmbEmployeeIds;
    public TextField txtEName;
    public Label lblSupplierId;
    public Label lblSupplierName;
    public Label lblCoarse;
    public Label lblWet;
    public Label lblBoiled;
    public Label lblReject;
    public TextField txtDescription;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();

        List<String> ids = null;
        try {

            setInvoiceId();
            ids = new SupplyController().searchEmployeeIds();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbEmployeeIds.getItems().addAll(ids);

        cmbEmployeeIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                txtEName.setText(new SupplyController().searchEData(newValue).getEmployeeName());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setInvoiceId() throws SQLException, ClassNotFoundException {
        lblInvoiceNo.setText(new SupplyController().getTicketIds());
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        lblPrintDate.setText(s.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblPrintTime.setText(currentTime.getHour()+" : "+currentTime.getMinute()+" : "+currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void setData(String sId,String id, String name, String d, String t, double netWeight, double wholeWeight, double coarse, double wet, int sackQty, double sackWeight, double boiled, double reject){
        lblIssueDate.setText(d);
        lblIssueTime.setText(t);
        lblSupplyId.setText(id);
        lblSupplierName.setText(name);
        lblSupplierId.setText(sId);
        lblWholeTeaLeavesWeight.setText(String.valueOf(wholeWeight));
        lblCoarse.setText(String.valueOf(coarse));
        lblNetWeight.setText(String.valueOf(netWeight));
        lblBoiled.setText(String.valueOf(boiled));
        lblReject.setText(String.valueOf(reject));
        lblWet.setText(String.valueOf(wet));
        lblSackQty.setText(String.valueOf(sackQty));
        lblSackWeight.setText(String.valueOf(sackWeight));
    }

    public void issueOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (txtEName.getText().isEmpty()||txtDescription.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"All Fields Are Required.").showAndWait();
        }else {

            Employee employee = new SupplyController().searchEData(cmbEmployeeIds.getValue());

            Ticket ticket = new Ticket(
                    lblInvoiceNo.getText(),
                    lblSupplierId.getText(),
                    lblPrintDate.getText(),
                    lblPrintTime.getText(),
                    Double.parseDouble(lblWholeTeaLeavesWeight.getText()),
                    Double.parseDouble(lblNetWeight.getText())
            );

            Issue issue = new Issue(
                    cmbEmployeeIds.getValue(),
                    lblInvoiceNo.getText(),
                    txtDescription.getText(),
                    employee.getDesignation(),
                    lblPrintDate.getText()
            );

            if (new SupplyController().setTicketData(ticket, issue)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Success").showAndWait();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").showAndWait();

            }
        }
    }

    public void printOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        JasperDesign design = null;
        try {
            design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Reports/Receipt.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            String issuedDate = lblIssueDate.getText();
            String issuedTime = lblIssueTime.getText();
            String receiptNo = lblInvoiceNo.getText();
            String supplyId = lblSupplyId.getText();
            String supplierId = lblSupplierId.getText();
            String supplierName = lblSupplierName.getText();
            double wholeWeight = Double.parseDouble(lblWholeTeaLeavesWeight.getText());
            int sackQty = Integer.parseInt(lblSackQty.getText());
            double sackWeight = Double.parseDouble(lblSackWeight.getText());
            double coarse = Double.parseDouble(lblCoarse.getText());
            double wet = Double.parseDouble(lblWet.getText());
            double boiled = Double.parseDouble(lblBoiled.getText());
            double reject = Double.parseDouble(lblReject.getText());
            double netWeight = Double.parseDouble(lblNetWeight.getText());

            HashMap map = new HashMap();
            map.put("recieptNo",receiptNo);
            map.put("issuedDate",issuedDate);
            map.put("issuedTime",issuedTime);
            map.put("supplyId",supplyId);
            map.put("supplierId",supplierId);
            map.put("supplierName",supplierName);
            map.put("wholeWeight",wholeWeight);
            map.put("sackQty",sackQty);
            map.put("sackWeight",sackWeight);
            map.put("coarse",coarse);
            map.put("wet",wet);
            map.put("boiled",boiled);
            map.put("reject",reject);
            map.put("netWeight",netWeight);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void cancelOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/SupplyForm.fxml"));
        AnchorPane pane = fxmlLoader.load();
        ticketContext.getChildren().setAll(pane);
    }

}
