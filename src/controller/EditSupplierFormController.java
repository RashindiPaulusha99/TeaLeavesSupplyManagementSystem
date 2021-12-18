package controller;

import controller.BusinessLogicControllers.SupplierController;
import SendData.SendSupplierDataToTable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Supplier;
import util.Validation;
import view.tm.SupplierTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditSupplierFormController implements Initializable {

    public TextField txtSupplierId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtNIC;
    public TextField txtArea;
    public JFXRadioButton radiobtnMale;
    public JFXRadioButton radiobtnFemale;
    public JFXButton btnUpdate;

    private SendSupplierDataToTable send;

    LinkedHashMap<TextField, Pattern> mapName = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapAddress = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapContact = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapNic = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapArea = new LinkedHashMap();

    Pattern compile_SupplierName = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_SupplierAddress = Pattern.compile("^[A-z .,/0-9]{4,50}$");
    Pattern compile_SupplierContact = Pattern.compile("^[+94]?[0-9]{3}[-]?[0-9]{7}$");
    Pattern compile_SupplierNIC = Pattern.compile("^[0-9]{9,12}[V-v]?$");
    Pattern compile_Area = Pattern.compile("^[0-9]{1,6}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValidation();
    }

    private void setValidation() {
        mapName.put(txtName, compile_SupplierName);
        mapAddress.put(txtAddress, compile_SupplierAddress);
        mapContact.put(txtContact, compile_SupplierContact);
        mapNic.put(txtNIC, compile_SupplierNIC);
        mapArea.put(txtArea, compile_Area);
    }

    public void genderF_clicked(MouseEvent mouseEvent) {
        if (radiobtnFemale.isSelected()) {
            radiobtnMale.setSelected(false);
        }
    }

    public void genderM_clicked(MouseEvent mouseEvent) {
        if (radiobtnMale.isSelected()) {
            radiobtnFemale.setSelected(false);
        }
    }

    public void setDataToTextfields(SupplierTM tm){
        txtSupplierId.setText(String.valueOf(tm.getSupplierId()));
        txtSupplierId.setDisable(true);
        txtName.setText(String.valueOf(tm.getSupplierName()));
        txtAddress.setText(String.valueOf(tm.getSupplierAddress()));
        txtContact.setText(String.valueOf(tm.getSupplierContact()));
        txtNIC.setText(String.valueOf(tm.getSupplierNIC()));
        if (tm.getSupplierGender().equals("Male")){
            radiobtnMale.setSelected(true);
        }else if(tm.getSupplierGender().equals("Female")){
            radiobtnFemale.setSelected(true);
        }
        txtArea.setText(String.valueOf(tm.getAreaOfTheLand()));
    }

    public void updateSupplierDetailsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String gender = null;

        if (radiobtnMale.isSelected()){
            gender = "Male";
        }else if (radiobtnFemale.isSelected()){
            gender = "Female";
        }

        Supplier supplier = new Supplier(
                txtSupplierId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtContact.getText(),
                txtNIC.getText(),
                gender,
                Integer.parseInt(txtArea.getText())
        );

        if (new SupplierController().updateSupplier(supplier)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated A Supplier.").showAndWait();
            send.loadData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again.").show();
        }
    }

    public void setData(SendSupplierDataToTable send){
        this.send = send;
    }

    public void name_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapName, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void address_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapAddress, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void contact_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapContact, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void nic_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapNic, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void area_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapArea, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }
}
