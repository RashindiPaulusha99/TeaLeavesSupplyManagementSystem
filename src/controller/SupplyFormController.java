package controller;

import controller.BusinessLogicControllers.SupplyController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import util.Validation;
import view.tm.SupplyDetailTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class SupplyFormController implements Initializable {

    public Label lblSupplyId;
    public Label lblTotalWeight;
    public Label lblTotalCost;
    public ComboBox<String> cmbSupplierIds;
    public ComboBox<String> cmbEmployeeIds;
    public ComboBox<String> cmbTeaCodes;
    public JFXCheckBox cboxNo;
    public JFXCheckBox cboxYes;
    public TextField txtSupplierName;
    public TextField txtSupplierAddress;
    public TextField txtSupplierContact;
    public TextField txtTeaName;
    public TextField txtUnitPrice;
    public TextField txtSackQty;
    public TextField txtSackWeight;
    public TextField txtWholeWeight;
    public TextField txtWaterWeight;
    public TextField txtTransport;
    public TableView<SupplyDetailTM> tblSupplyDetails;
    public JFXButton btnAdd;
    public JFXButton btnEdit;
    public AnchorPane supplyContext;
    public TextField txtCoarseWeight;
    public JFXButton btnDelete;
    public TextField txtBoiled;
    public TextField txtReject;

    String d = null;
    String t = null;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapForTransport = new LinkedHashMap();

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
        btnAdd.setDisable(true);
        txtTransport.setDisable(true);

        tblSupplyDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("teaCode"));
        tblSupplyDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("sackQTY"));
        tblSupplyDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("sackWeight"));
        tblSupplyDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("wholeWeight"));
        tblSupplyDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("coarse"));
        tblSupplyDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("wet"));
        tblSupplyDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("boiled"));
        tblSupplyDetails.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("reject"));
        tblSupplyDetails.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("netWeight"));
        tblSupplyDetails.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("cost"));
        tblSupplyDetails.getColumns().get(10).setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadDateAndTime();

        setValidations();

        try {

            setSupplyId();

            loadEmployeeIds();

            loadSupplierIds();

            loadTeaCodes();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbSupplierIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {

                setSupplierDetails(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbTeaCodes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {

                setTeaDetails(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        d = s.format(date);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO,event -> {
            LocalTime currentTime = LocalTime.now();
            t = currentTime.getHour()+" : "+currentTime.getMinute()+" : "+currentTime.getSecond();
        }),
        new KeyFrame(Duration.seconds(1))

        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setSupplyId() throws SQLException, ClassNotFoundException {
        lblSupplyId.setText(new SupplyController().getSupplyIds());
    }

    public void loadEmployeeIds() throws SQLException, ClassNotFoundException {
        List<String> ids = new SupplyController().searchEIds();
        cmbEmployeeIds.getItems().addAll(ids);
    }

    public void loadSupplierIds() throws SQLException, ClassNotFoundException {
        List<String> ids = new SupplyController().searchSIds();
        cmbSupplierIds.getItems().addAll(ids);
    }

    public void setSupplierDetails(String id) throws SQLException, ClassNotFoundException {
        Supplier suppliers = new SupplyController().searchSDate(id);

        if (suppliers == null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set.").show();
        }else {
            txtSupplierName.setText(suppliers.getSupplierName());
            txtSupplierName.setDisable(true);
            txtSupplierAddress.setText(suppliers.getSupplierAddress());
            txtSupplierAddress.setDisable(true);
            txtSupplierContact.setText(suppliers.getSupplierContact());
            txtSupplierContact.setDisable(true);
        }
    }

    public void loadTeaCodes() throws SQLException, ClassNotFoundException {
        List<String> ids = new SupplyController().searchTCodes();
        cmbTeaCodes.getItems().addAll(ids);
    }

    public void setTeaDetails(String id) throws SQLException, ClassNotFoundException {
        Tea tea = new SupplyController().searchTDate(id);

        if (tea == null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set.").show();
        }else {
            txtTeaName.setText(tea.getName());
            txtTeaName.setDisable(true);
            txtUnitPrice.setText(String.valueOf(tea.getUnitPrice()));
            txtUnitPrice.setDisable(true);
        }
    }

    private void setValidations() {
        map.put(txtSackQty, compile_SackQty);
        map.put(txtSackWeight, compile_SackWeight);
        map.put(txtWholeWeight, compile_WholeWeight);
        map.put(txtCoarseWeight, compile_Coarse);
        map.put(txtWaterWeight, compile_WaterWeight);
        map.put(txtBoiled, compile_Boiled);
        map.put(txtReject, compile_Reject);
        mapForTransport.put(txtTransport, compile_Transport);
    }

    public void supply_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(map, btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void supply_TransKeyReleased(KeyEvent keyEvent) {
       Object response = new Validation().validate(mapForTransport);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
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
            txtTransport.setDisable(false);
            txtTransport.setText("");
        }
    }

    JFXButton button;
    public void setBtn(){
        button = new JFXButton();
        button.setStyle("-fx-border-color: red");
        Image img = new Image("assets\\icons8-delete-64.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(27);
        view.setFitWidth(36);
        button.setGraphic(view);
    }

    public void deleteBtnOnAction(SupplyDetailTM supplyDetailTM){
        button.setOnAction((e) -> {
            ButtonType yes= new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no= new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Supply?",yes,no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no)==yes){
                obList.remove(supplyDetailTM);
                calculateWeight();
                calculateCost();
                tblSupplyDetails.refresh();
            }else{
            }
        });
    }

    ObservableList<SupplyDetailTM> obList = FXCollections.observableArrayList();

    public ObservableList<SupplyDetailTM> loadSupplyData(){

            int sackQty = Integer.parseInt(txtSackQty.getText());
            double sackWeight = Double.parseDouble(txtSackWeight.getText());
            double wholeWeight = Double.parseDouble(txtWholeWeight.getText());
            double coarse = Double.parseDouble(txtCoarseWeight.getText());
            double waterWeight = Double.parseDouble(txtWaterWeight.getText());
            double boiled = Double.parseDouble(txtBoiled.getText());
            double reject = Double.parseDouble(txtReject.getText());
            double wholeSackWeight = (sackQty * sackWeight);
            double netWeight = wholeWeight - (wholeSackWeight + coarse + waterWeight + boiled + reject);
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            double price = unitPrice * netWeight;

            setBtn();

            SupplyDetailTM supplyDetailTM = new SupplyDetailTM(
                    cmbTeaCodes.getValue(),
                    wholeWeight,
                    sackQty,
                    sackWeight,
                    coarse,
                    waterWeight,
                    boiled,
                    reject,
                    netWeight,
                    price,
                    button
            );

            int rawIndex = isExists(supplyDetailTM);

            if (rawIndex == -1) {
                obList.add(supplyDetailTM);
                deleteBtnOnAction(supplyDetailTM);
            } else {
                SupplyDetailTM tm = obList.get(rawIndex);

                SupplyDetailTM temp = new SupplyDetailTM(
                        tm.getTeaCode(),
                        tm.getWholeWeight() + wholeWeight,
                        tm.getSackQTY() + sackQty,
                        tm.getSackWeight() + sackWeight,
                        tm.getCoarse() + coarse,
                        tm.getWet() + waterWeight,
                        tm.getBoiled() + boiled,
                        tm.getReject() + reject,
                        tm.getNetWeight() + netWeight,
                        tm.getCost() + price,
                        button
                );

                obList.remove(rawIndex);
                obList.add(temp);

                deleteBtnOnAction(temp);
            }

            tblSupplyDetails.setItems(obList);
            calculateCost();
            calculateWeight();

        return obList;
    }

    public void addSupplyOnAction(ActionEvent actionEvent)  {
        if (cmbTeaCodes.getValue()==null || txtWaterWeight.getText().isEmpty() || txtCoarseWeight.getText().isEmpty() || txtWholeWeight.getText().isEmpty() || txtSackWeight.getText().isEmpty() || txtSackQty.getText().isEmpty() || txtReject.getText().isEmpty() || txtBoiled.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"All Fields Are Required.").show();
        }else {
            loadSupplyData();
        }
    }

    public int isExists(SupplyDetailTM tm){
        for (int j = 0; j < obList.size(); j++) {
            if (tm.getTeaCode().equals(obList.get(j).getTeaCode())){
                return j;
            }
        }
        return -1;
    }

    public void calculateCost(){
        double cost = 0;
        for (SupplyDetailTM tm : obList) {
            cost+=tm.getCost();
        }
        lblTotalCost.setText(cost+" /=");
    }

    public void calculateWeight(){
        double weight = 0;
        for (SupplyDetailTM tm : obList) {
            weight+=tm.getNetWeight();
        }
        lblTotalWeight.setText(weight+" KG");
    }

    double netWeight = 0;//whole weight of supply
    double wholeCost = 0;//whole weight of supply
    double wholeWeight = 0;
    double coarse = 0;
    double wet = 0;
    double sackWeight = 0;
    double boiled = 0;
    double reject = 0;
    int sackQty = 0;

    public void acceptSupplyOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (txtTransport.getText().isEmpty()||txtSackQty.getText().isEmpty()||txtSackWeight.getText().isEmpty()||txtWholeWeight.getText().isEmpty()||txtWaterWeight.getText().isEmpty()||txtCoarseWeight.getText().isEmpty()||txtBoiled.getText().isEmpty()||txtReject.getText().isEmpty()||cmbEmployeeIds.getValue()==null||cmbSupplierIds.getValue()==null||cmbTeaCodes.getValue()==null){
            new Alert(Alert.AlertType.WARNING,"All Fields Are Required.").show();
        }else {

            ArrayList<SupplyDetail> supplyDetails = new ArrayList<>();//create arraylist to put supply

            for (SupplyDetailTM tempTm : obList) {
                netWeight += tempTm.getNetWeight();
                wholeCost += tempTm.getCost();
                wholeWeight += tempTm.getWholeWeight();
                coarse += tempTm.getCoarse();
                wet += tempTm.getWet();
                sackWeight += tempTm.getSackWeight();
                sackQty += tempTm.getSackQTY();
                boiled += tempTm.getBoiled();
                reject += tempTm.getReject();

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
                    lblSupplyId.getText(),
                    cmbSupplierIds.getValue(),
                    d,
                    t,
                    netWeight,
                    wholeCost,
                    Double.parseDouble(txtTransport.getText()),
                    supplyDetails
            );

            String designation = new SupplyController().returnDesignation(cmbEmployeeIds.getValue());
            String description = null;

            if (designation=="driver") {
                description = "Transport";
            } else if (designation=="data entry operator") {
                description = "Insert Detail";
            } else if (designation=="helper") {
                description = "Measure Tea Sacks";
            }
            Service service = new Service(
                    cmbEmployeeIds.getValue(),
                    lblSupplyId.getText(),
                    description,
                    designation,
                    d
            );

            if (new SupplyController().acceptSupply(supply, service)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Success.").showAndWait();
                setSupplyId();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }
    }

    public void issueTicketOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/IssueTicketForm.fxml"));
        AnchorPane pane = fxmlLoader.load();
        IssueTicketFormController controller = fxmlLoader.<IssueTicketFormController>getController();
        controller.setData(cmbSupplierIds.getValue(),lblSupplyId.getText(),txtSupplierName.getText(),d,t,netWeight,wholeWeight,coarse,wet,sackQty,sackWeight,boiled,reject);
        supplyContext.getChildren().setAll(pane);
    }

    public void editSupplyOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/EditSupplyForm.fxml"));
        Parent load = loader.load ();
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void deleteSupplyOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/DeleteSupplyForm.fxml"));
        Parent load = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }
}
