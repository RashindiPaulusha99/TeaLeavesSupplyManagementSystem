package controller;

import controller.BusinessLogicControllers.SupplyController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.*;
import util.Validation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditSupplyFormController implements Initializable {

    public TextField txtSackQty;
    public TextField txtSackWeight;
    public TextField txtWholeWeight;
    public TextField txtWaterWeight;
    public TextField txtTransport;
    public JFXCheckBox cboxNo;
    public JFXCheckBox cboxYes;
    public JFXButton btnUpdate;
    public TextField txtCoarseWeight;
    public TextField txtBoiled;
    public TextField txtReject;
    public ComboBox<String> cmbSupplyID;
    public TextField txtTeaCode;
    public TableView<SupplyDetail> tblSupplyDetail;
    public Label lblSupplierId;
    public Label lblSupplyDate;
    public Label lblSupplyTime;
    public Label lblWholeWeight;
    public Label lblWholeCost;

    double unitPrice = 0;
    ObservableList<SupplyDetail> observableList = null;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapForTransport = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapSackQty = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapSackWeight = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapWholeWeight = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapCoarse = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapWaterWeight = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapBoiled = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapReject = new LinkedHashMap();

    Pattern compile_SackQty = Pattern.compile("^[0-9]{1,3}$");
    Pattern compile_SackWeight = Pattern.compile("^[^0]?[0-9]{1,2}?([.][0-9]{2})?$");
    Pattern compile_WholeWeight = Pattern.compile("^[^0]?[0-9]{1,5}?([.][0-9]{2})?$");
    Pattern compile_Coarse = Pattern.compile("^[^0]?[0-9]{1,2}?([.][0-9]{2})?$");
    Pattern compile_WaterWeight = Pattern.compile("^[^0]?[0-9]{1,2}?([.][0-9]{2})?$");
    Pattern compile_Boiled = Pattern.compile("^[^0]?[0-9]{1,2}?([.][0-9]{2})?$");
    Pattern compile_Reject = Pattern.compile("^[^0]?[0-9]{1,2}?([.][0-9]{2})?$");
    Pattern compile_Transport = Pattern.compile("^[^0][0-9]{1,4}?([.][0-9]{2})?$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tblSupplyDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("teaCode"));
        tblSupplyDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("sackQTY"));
        tblSupplyDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("sackWeight"));
        tblSupplyDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("wholeWeight"));
        tblSupplyDetail.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("coarse"));
        tblSupplyDetail.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("wet"));
        tblSupplyDetail.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("boiled"));
        tblSupplyDetail.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("reject"));
        tblSupplyDetail.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("netWeight"));

        ArrayList<String> Supplyids = null;
        try {
            Supplyids = new SupplyController().SupplyIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbSupplyID.getItems().addAll(Supplyids);

        cmbSupplyID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {

                setSupply(newValue);
                observableList = loadData(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblSupplyDetail.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                txtTeaCode.setText(newValue.getTeaCode());
                txtTeaCode.setDisable(true);
                txtSackQty.setText(String.valueOf(newValue.getSackQTY()));
                txtSackWeight.setText(String.valueOf(newValue.getSackWeight()));
                txtWaterWeight.setText(String.valueOf(newValue.getWet()));
                txtWholeWeight.setText(String.valueOf(newValue.getWholeWeight()));
                txtBoiled.setText(String.valueOf(newValue.getBoiled()));
                txtReject.setText(String.valueOf(newValue.getReject()));
                txtCoarseWeight.setText(String.valueOf(newValue.getCoarse()));

                try {
                    unitPrice = Double.parseDouble(String.valueOf(new SupplyController().searchTDate(txtTeaCode.getText()).getUnitPrice()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else {

            }
         });

        setValidations();
    }

    private void setValidations() {
        mapSackQty.put(txtSackQty, compile_SackQty);
        mapSackWeight.put(txtSackWeight, compile_SackWeight);
        mapWholeWeight.put(txtWholeWeight, compile_WholeWeight);
        mapCoarse.put(txtCoarseWeight, compile_Coarse);
        mapWaterWeight.put(txtWaterWeight, compile_WaterWeight);
        mapBoiled.put(txtBoiled, compile_Boiled);
        mapReject.put(txtReject, compile_Reject);
        mapForTransport.put(txtTransport, compile_Transport);
    }

    public void sqty_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapSackQty, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void sweight_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapSackWeight, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void whweight_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapWholeWeight, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void coarse_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapCoarse, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void wet_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapWaterWeight, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void trans_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapForTransport, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void boiled_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapBoiled, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void reject_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapReject, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public ObservableList<SupplyDetail> loadData(String id) throws SQLException, ClassNotFoundException {
        ObservableList<SupplyDetail> obList = FXCollections.observableArrayList();
        ArrayList<SupplyDetail> supplyDetail = new SupplyController().getSupplyDetail(id);
        supplyDetail.forEach(e -> {
            obList.add(new SupplyDetail(e.getTeaCode(),e.getWholeWeight(),e.getSackQTY(),e.getSackWeight(),e.getCoarse(),e.getWet(),e.getBoiled(),e.getReject(),e.getNetWeight(),e.getCost()));
        });
        tblSupplyDetail.setItems(obList);
        return obList;
    }

    public void setSupply(String  id) throws SQLException, ClassNotFoundException {
        ArrayList<Supply> supplyDetail = new SupplyController().findSupplyDetail(id);
        lblSupplierId.setText(supplyDetail.get(0).getSupplierId());
        lblSupplyDate.setText(supplyDetail.get(0).getSupplyDate());
        lblSupplyTime.setText(supplyDetail.get(0).getSupplyTime());
        lblWholeWeight.setText(supplyDetail.get(0).getWeight()+" KG");
        lblWholeCost.setText(supplyDetail.get(0).getTotalCost()+" /=");
        txtTransport.setText(String.valueOf(supplyDetail.get(0).getTransportCharge()));
    }

    public void transportNo_Clicked(MouseEvent mouseEvent) {
        if (cboxNo.isSelected()) {
            cboxYes.setSelected(false);
            txtTransport.setText("0");
            txtTransport.setDisable(true);
        }
    }

    public void transportYes_Clicked(MouseEvent mouseEvent) {
        if (cboxYes.isSelected()) {
            cboxNo.setSelected(false);
            txtTransport.setText("");
            txtTransport.setDisable(false);
        }
    }

    public int isExists(SupplyDetail tm){
        for (int j = 0; j < observableList.size(); j++) {
            if (tm.getTeaCode().equals(observableList.get(j).getTeaCode())){
                return j;
            }
        }
        return -1;
    }

    double totalCost = 0;
    public void calculateCost(){
        double cost = 0;
        for (SupplyDetail tm : observableList) {
            cost+=tm.getCost();
        }
        totalCost = cost;
    }

    double totalWeight = 0;
    public void calculateWeight(){
        double weight = 0;
        for (SupplyDetail tm : observableList) {
            weight+=tm.getNetWeight();
        }
        totalWeight = weight;
    }

    public void addDataOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        int sackQty = Integer.parseInt(txtSackQty.getText());
        double sackWeight = Double.parseDouble(txtSackWeight.getText());
        double wholeWeight = Double.parseDouble(txtWholeWeight.getText());
        double coarse = Double.parseDouble(txtCoarseWeight.getText());
        double waterWeight = Double.parseDouble(txtWaterWeight.getText());
        double boiled = Double.parseDouble(txtBoiled.getText());
        double reject = Double.parseDouble(txtReject.getText());
        double wholeSackWeight = (sackQty * sackWeight);
        double netWeight = wholeWeight - (wholeSackWeight + coarse + waterWeight + boiled + reject);
        Tea tea = new SupplyController().searchTDate(txtTeaCode.getText());
        double unitPrice = Double.parseDouble(String.valueOf(tea.getUnitPrice()));
        double price = unitPrice * netWeight;

        SupplyDetail supplyDetail = new SupplyDetail(
                txtTeaCode.getText(),
                wholeWeight,
                sackQty,
                sackWeight,
                coarse,
                waterWeight,
                boiled,
                reject,
                netWeight,
                price
        );

        int rawIndex = isExists(supplyDetail);

        if (rawIndex == -1) {

        } else {
            SupplyDetail tm = observableList.get(rawIndex);

            SupplyDetail temp = new SupplyDetail(
                    tm.getTeaCode(),
                    wholeWeight,
                    sackQty,
                    sackWeight,
                    coarse,
                    waterWeight,
                    boiled,
                    reject,
                    netWeight,
                    price
            );

            observableList.remove(rawIndex);
            observableList.add(temp);
        }
        tblSupplyDetail.setItems(observableList);
        calculateCost();
        calculateWeight();

    }

    double netWeightForSupply = 0;//whole weight of supply
    double wholeCostForSupply = 0;//whole weight of supply
    double wholeWeightForSupply = 0;

    public void updateDetailOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        ArrayList<SupplyDetail> supplyDetails = new ArrayList<>();//create arraylist to put supply

        for (SupplyDetail tempTm : observableList) {
            netWeightForSupply += tempTm.getNetWeight();
            wholeCostForSupply += tempTm.getCost();
            wholeWeightForSupply += tempTm.getWholeWeight();

            //add all table details to arraylist
            supplyDetails.add(new SupplyDetail(
                    tempTm.getTeaCode(),
                    tempTm.getWholeWeight(),
                    tempTm.getSackQTY(),
                    tempTm.getSackWeight(),
                    tempTm.getCoarse(),
                    tempTm.getWet(),
                    tempTm.getBoiled(),
                    tempTm.getReject(),
                    tempTm.getNetWeight(),
                    tempTm.getCost()
            ));
        }

        //create supply object
        Supply supply = new Supply(
                cmbSupplyID.getValue(),
                lblSupplierId.getText(),
                lblSupplyDate.getText(),
                lblSupplyTime.getText(),
                netWeightForSupply,
                wholeCostForSupply,
                Double.parseDouble(txtTransport.getText()),
                supplyDetails
        );

        if (new SupplyController().UpdateSupply(supply)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Success.").showAndWait();
            setSupply(cmbSupplyID.getValue());
            clearTextFieldBorder();
            clearTextField();

        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void clearTextField(){
        lblSupplyDate.setText("");
        lblSupplyTime.setText("");
        lblWholeCost.setText("");
        lblWholeWeight.setText("");
        lblSupplierId.setText("");
        tblSupplyDetail.getItems().clear();
        txtTeaCode.clear();
        txtTransport.clear();
        txtSackQty.clear();
        txtSackWeight.clear();
        txtWholeWeight.clear();
        txtWaterWeight.clear();
        txtCoarseWeight.clear();
        txtBoiled.clear();
        txtReject.clear();
    }

    public void clearTextFieldBorder(){
        txtTransport.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtSackQty.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtSackWeight.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtWholeWeight.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtCoarseWeight.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtWaterWeight.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtReject.getParent().setStyle("-fx-border-color:  #a4b0be");
        txtBoiled.getParent().setStyle("-fx-border-color:  #a4b0be");
    }
}
