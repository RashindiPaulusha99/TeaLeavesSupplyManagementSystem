package controller;

import controller.BusinessLogicControllers.ReportsController;
import com.jfoenix.controls.JFXButton;
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
import model.Supply;
import view.tm.SupplyTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class SupplyReportsFormController implements Initializable {
    public AnchorPane dailySupplyContext;
    public TableView<SupplyTM> tblSupply;
    public Label lblDate;
    public Label lblSupplyAmount;
    public Label lblTotalStock;
    public JFXButton deletebtn;
    public JFXButton editbtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblSupply.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("supplyId"));
        tblSupply.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        tblSupply.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("supplyTime"));
        tblSupply.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("weight"));

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

    private void loadDataToTable() throws SQLException, ClassNotFoundException {
        ArrayList<Supply> supplies = new ReportsController().loadSupplyDataForReports(lblDate.getText());
        ObservableList<SupplyTM> obList = FXCollections.observableArrayList();

        supplies.forEach(e -> {
            obList.add(new SupplyTM(e.getSupplyId(),e.getSupplierId(),e.getSupplyDate(),e.getSupplyTime(),e.getWeight(),e.getTotalCost(),e.getTransportCharge(),e.getTea()));
        });
        tblSupply.setItems(obList);

        lblSupplyAmount.setText(String.valueOf(new ReportsController().countSupply(lblDate.getText())));
        lblTotalStock.setText(new ReportsController().findWeight(lblDate.getText())+" KG");
    }

    public void monthlySupplyOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/MonthlySupplyReportForm.fxml"));
        AnchorPane pane = loader.load();
        dailySupplyContext.getChildren().setAll(pane);
    }

    public void annualySupplyOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AnnuallySupplyReportForm.fxml"));
        AnchorPane pane = loader.load();
        dailySupplyContext.getChildren().setAll(pane);
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
