package controller;

import controller.BusinessLogicControllers.SupplyController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Supply;
import model.SupplyDetail;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeleteSupplyFormController implements Initializable {
    public ComboBox<String> cmbSupplyId;
    public TextField txtSupplierId;
    public TextField txtSupplyDate;
    public TextField txtSupplyTime;
    public TextField txtSupplyWeight;
    public TextField txtSupplyCost;
    public TextField txtSupplyTransport;
    public JFXButton btnDelete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String > ids = null;
        try {
            ids = new SupplyController().SupplyIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbSupplyId.getItems().addAll(ids);

        cmbSupplyId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<Supply> supply = null;
            try {
                supply = new SupplyController().findSupplyDetail(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            txtSupplierId.setText(supply.get(0).getSupplierId());
            txtSupplyDate.setText(supply.get(0).getSupplyDate());
            txtSupplyTime.setText(supply.get(0).getSupplyTime());
            txtSupplyWeight.setText(String.valueOf(supply.get(0).getWeight()));
            txtSupplyCost.setText(String.valueOf(supply.get(0).getTotalCost()));
            txtSupplyTransport.setText(String.valueOf(supply.get(0).getTransportCharge()));
        });
    }

    public void deletePaymentOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ArrayList<SupplyDetail> supplyDetail = new SupplyController().getSupplyDetail(cmbSupplyId.getValue());

        btnDelete.setOnAction((events) -> {
            ButtonType yes= new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no= new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Supply?",yes,no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no)==yes){

                try {
                    if (new SupplyController().deleteSupply(cmbSupplyId.getValue(),supplyDetail)){
                        new Alert(Alert.AlertType.CONFIRMATION,"Deleted Supply.").showAndWait();
                            txtSupplierId.clear();
                            txtSupplyDate.clear();
                            txtSupplyTime.clear();
                            txtSupplyWeight.clear();
                            txtSupplyCost.clear();
                            txtSupplyTransport.clear();
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Try Again.").show();

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }else{
            }
        });
    }

}
