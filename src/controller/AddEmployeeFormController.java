package controller;

import controller.BusinessLogicControllers.EmployeeController;
import SendData.SendEmployeeDataToTable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Employee;
import util.Validation;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddEmployeeFormController implements Initializable {

    public TextField txtEmployeeId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtAge;
    public JFXRadioButton radiobtnMale;
    public JFXRadioButton radiobtnFemale;
    public TextField txtContact;
    public TextField txtNIC;
    public TextField txtDesignation;
    public JFXButton btnSave;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    Pattern compile_EmployeeName = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_EmployeeAddress = Pattern.compile("^[A-z .,/0-9]{4,50}$");
    Pattern compile_EmployeeAge = Pattern.compile("^([12][0-9]{3}[-|.](0[1-9]|1[0-2])[-|.](0[1-9]|[12][0-9]|3[01]))$");
    Pattern compile_EmployeeContact = Pattern.compile("^[+94]?[0-9]{3}[-]?[0-9]{7}$");
    Pattern compile_EmployeeNIC = Pattern.compile("^[0-9]{9,12}[V-v]?$");
    Pattern compile_EmployeeDesignation = Pattern.compile("^[A-z ]{3,20}$");

    private SendEmployeeDataToTable send;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setDisable(true);

        setValidation();
    }

    private void setValidation() {
        map.put(txtName, compile_EmployeeName);
        map.put(txtAddress, compile_EmployeeAddress);
        map.put(txtAge, compile_EmployeeAge);
        map.put(txtContact, compile_EmployeeContact);
        map.put(txtNIC, compile_EmployeeNIC);
        map.put(txtDesignation, compile_EmployeeDesignation);
    }

    public void employee_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void genderMale_MouseClicked(MouseEvent mouseEvent) {
        if (radiobtnMale.isSelected()) {
            radiobtnFemale.setSelected(false);
        }
    }

    public void genderFemale_MouseClicked(MouseEvent mouseEvent) {
        if (radiobtnFemale.isSelected()) {
            radiobtnMale.setSelected(false);
        }
    }

    public void setId(String id){
        txtEmployeeId.setText(id);
        txtEmployeeId.setDisable(true);
    }

    public void saveEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String gender = null;

        if (radiobtnMale.isSelected()) {
            gender = "Male";
        } else if (radiobtnFemale.isSelected()) {
            gender = "Female";
        }

        Employee employee = new Employee(
                txtEmployeeId.getText(),
                txtName.getText(),
                txtAge.getText(),
                txtAddress.getText(),
                txtContact.getText(),
                txtNIC.getText(),
                gender,
                txtDesignation.getText()
        );

        Boolean b = false;
        if (radiobtnMale.isSelected() || radiobtnFemale.isSelected()) {
            b = true;
        } else {
            b = false;
        }

        if (new EmployeeController().searchId(txtEmployeeId.getText())) {
            new Alert(Alert.AlertType.WARNING, "This Employee Already Exists.").show();
        } else {

            if (new EmployeeController().searchNIC(txtNIC.getText())) {
                new Alert(Alert.AlertType.WARNING, "NIC Already Exists.").show();
            } else {
                if (txtEmployeeId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtAge.getText().isEmpty() || txtContact.getText().isEmpty() || txtNIC.getText().isEmpty() || txtDesignation.getText().isEmpty() || b == false) {
                    new Alert(Alert.AlertType.WARNING, "All Fields Are Required.").show();
                }else {
                    if (new EmployeeController().saveEmployee(employee)) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Saved A Employee.").showAndWait();
                        send.loadData();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again.").show();
                    }
                }
            }
        }
    }

    public void clearEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        txtEmployeeId.setText(new EmployeeController().getEmployeeId());
        txtEmployeeId.setDisable(true);
        txtName.clear();
        txtAge.clear();
        txtAddress.clear();
        txtContact.clear();
        txtNIC.clear();
        txtDesignation.clear();
        radiobtnFemale.setSelected(false);
        radiobtnMale.setSelected(false);
    }

    public void setData(SendEmployeeDataToTable send){
        this.send = send;
    }

}
