package controller;

import controller.BusinessLogicControllers.SupplierController;
import SendData.SendSupplierDataToTable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Supplier;
import model.User;
import util.Validation;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddSupplierFormController implements Initializable {

    public TextField txtSupplierId;
    public TextField txtAddress;
    public JFXRadioButton radiobtnMale;
    public JFXRadioButton radiobtnFemale;
    public TextField txtContact;
    public TextField txtNIC;
    public TextField txtArea;
    public TextField txtName;
    public JFXButton btnSave;
    public TextField txtUsername;
    public ComboBox<String > cmbUserId;

    private SendSupplierDataToTable send;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    Pattern compile_SupplierName = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_SupplierAddress = Pattern.compile("^[A-z .,/0-9]{4,50}$");
    Pattern compile_SupplierContact = Pattern.compile("^[+94]?[0-9]{3}[-]?[0-9]{7}$");
    Pattern compile_SupplierNIC = Pattern.compile("^[0-9]{9,12}[V-v]?$");
    Pattern compile_Area = Pattern.compile("^[0-9]{1,6}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setDisable(true);

        try {

            loadEmployeeIds();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setValidations();

        cmbUserId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {

                loadEmployeeName(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadEmployeeIds() throws SQLException, ClassNotFoundException {
        List<String> employeeIds = new SupplierController().searchIds();
        cmbUserId.getItems().addAll(employeeIds);
    }

    public void loadEmployeeName(String id) throws SQLException, ClassNotFoundException {
        txtUsername.setText(new SupplierController().getName(id));
    }

    private void setValidations() {
        map.put(txtName, compile_SupplierName);
        map.put(txtAddress, compile_SupplierAddress);
        map.put(txtContact, compile_SupplierContact);
        map.put(txtNIC, compile_SupplierNIC);
        map.put(txtArea, compile_Area);
    }

    public void supplier_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void genderM_clicked(MouseEvent mouseEvent) {
        if (radiobtnMale.isSelected()) {
            radiobtnFemale.setSelected(false);
        }
    }

    public void genderF_clicked(MouseEvent mouseEvent) {
        if (radiobtnFemale.isSelected()) {
            radiobtnMale.setSelected(false);
        }
    }

    public void setId(String id){
        txtSupplierId.setText(id);
        txtSupplierId.setDisable(true);
    }

    public void saveSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String gender = null;

        if (radiobtnMale.isSelected()) {
            gender = "Male";
        } else if (radiobtnFemale.isSelected()) {
            gender = "Female";
        }

        Boolean b = false;
        if (radiobtnMale.isSelected() || radiobtnFemale.isSelected()) {
            b = true;
        } else {
            b = false;
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

        User user = new User(
                txtSupplierId.getText(),
                cmbUserId.getValue(),
                txtUsername.getText()
        );

        if (new SupplierController().searchId(txtSupplierId.getText())) {
            new Alert(Alert.AlertType.WARNING, "This Supplier Already Exists.").show();
        } else {

            if (new SupplierController().searchNIC(txtNIC.getText())) {
                new Alert(Alert.AlertType.WARNING, "NIC Already Exists.").show();
            } else {
                if (txtSupplierId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtContact.getText().isEmpty() || txtNIC.getText().isEmpty() || txtArea.getText().isEmpty() || b == false ||txtUsername.getText().isEmpty() || cmbUserId.getValue()==null) {
                    new Alert(Alert.AlertType.WARNING, "All Fields Are Required.").show();
                }else {
                    if (new SupplierController().saveSupplier(supplier,user)) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Saved A Supplier.").showAndWait();
                        send.loadData();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again.").show();
                    }
                }
            }
        }
    }

    public void clearSupplierDataOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        txtSupplierId.setText(new SupplierController().getSupplierId());
        txtSupplierId.setDisable(true);
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtNIC.clear();
        txtArea.clear();
        radiobtnFemale.setSelected(false);
        radiobtnMale.setSelected(false);
    }

    public void setData(SendSupplierDataToTable send){
        this.send = send;
    }

}
