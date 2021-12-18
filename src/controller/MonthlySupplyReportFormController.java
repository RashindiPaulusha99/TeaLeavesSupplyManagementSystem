package controller;

import controller.BusinessLogicControllers.ReportsController;
import com.jfoenix.controls.JFXButton;
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
import model.Supply;
import view.tm.SupplyTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MonthlySupplyReportFormController implements Initializable {

    public AnchorPane monthlyContext;
    public TableView<SupplyTM> tblSupply;
    public Label lblSupplyAmount;
    public Label lblTotalStock;
    public JFXDatePicker startDPicker;
    public JFXDatePicker endDPicker;
    public JFXButton deletebtn;
    public JFXButton editbtn;

    LocalDate endDate = null;
    LocalDate startDate=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblSupply.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("supplyId"));
        tblSupply.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        tblSupply.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("supplyTime"));
        tblSupply.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("weight"));

        startDPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startDate= startDPicker.getValue();
            }
        });

        endDPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                endDate = endDPicker.getValue();

                try {

                    loadDataToTable(startDate,endDate);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadDataToTable(LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        ArrayList<Supply> supplies = new ReportsController().loadSupplyDataForMoAnReports(this.startDate, this.endDate);
        ObservableList<SupplyTM> obList = FXCollections.observableArrayList();

        supplies.forEach(e -> {
            obList.add(new SupplyTM(e.getSupplyId(),e.getSupplierId(),e.getSupplyDate(),e.getSupplyTime(),e.getWeight(),e.getTotalCost(),e.getTransportCharge(),e.getTea()));
        });
        tblSupply.setItems(obList);

        lblSupplyAmount.setText(String.valueOf(new ReportsController().countSupplyForMoAn(this.startDate, this.endDate)));
        lblTotalStock.setText(new ReportsController().findWeightForMoAn(this.startDate, this.endDate)+" KG");
    }

    public void annualySupplyOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AnnuallySupplyReportForm.fxml"));
        AnchorPane pane = loader.load();
        monthlyContext.getChildren().setAll(pane);
    }

    public void dailyReportOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/SupplyReportsForm.fxml"));
        AnchorPane pane = loader.load();
        monthlyContext.getChildren().setAll(pane);
    }

    public void deleteOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/DeleteSupplyForm.fxml"));
        Parent load = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void EditOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/EditSupplyForm.fxml"));
        Parent load = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }
}
