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
import view.tm.EmployeeTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditEmployeeFormController  implements Initializable {
    public TextField txtEmployeeId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtAge;
    public JFXRadioButton radiobtnMale;
    public JFXRadioButton radiobtnFemale;
    public TextField txtContact;
    public TextField txtNIC;
    public TextField txtDesignation;
    public JFXButton btnUpdate;

    private SendEmployeeDataToTable send;

    LinkedHashMap<TextField, Pattern> mapName = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapAddress = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapAge = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapContact = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapNIC = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapDesignation = new LinkedHashMap();

    Pattern compile_EmployeeName = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_EmployeeAddress = Pattern.compile("^[A-z .,/0-9]{4,50}$");
    Pattern compile_EmployeeAge = Pattern.compile("^([12][0-9]{3}[-|.](0[1-9]|1[0-2])[-|.](0[1-9]|[12][0-9]|3[01]))$");
    Pattern compile_EmployeeContact = Pattern.compile("^[+94]?[0-9]{3}[-]?[0-9]{7}$");
    Pattern compile_EmployeeNIC = Pattern.compile("^[0-9]{9,12}[V-v]?$");
    Pattern compile_EmployeeDesignation = Pattern.compile("^[A-z ]{3,20}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValidation();
    }

    private void setValidation() {
        mapName.put(txtName, compile_EmployeeName);
        mapAddress.put(txtAddress, compile_EmployeeAddress);
        mapAge.put(txtAge, compile_EmployeeAge);
        mapContact.put(txtContact, compile_EmployeeContact);
        mapNIC.put(txtNIC, compile_EmployeeNIC);
        mapDesignation.put(txtDesignation, compile_EmployeeDesignation);
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

    public void updateEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String gender = null;

        if (radiobtnMale.isSelected()){
            gender = "Male";
        }else if (radiobtnFemale.isSelected()){
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

        if (new EmployeeController().updateEmployee(employee)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated A Employee.").showAndWait();
            send.loadData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again.").show();
        }

    }

    public void setDataToTextfields(EmployeeTM tm){
        txtEmployeeId.setText(String.valueOf(tm.getEmployeeId()));
        txtEmployeeId.setDisable(true);
        txtName.setText(String.valueOf(tm.getEmployeeName()));
        txtAddress.setText(String.valueOf(tm.getEmployeeAddress()));
        txtAge.setText(String.valueOf(tm.getEmployeeDOB()));
        txtContact.setText(String.valueOf(tm.getEmployeeContact()));
        txtNIC.setText(String.valueOf(tm.getEmployeeNIC()));
        if (tm.getEmployeeGender().equals("Male")){
            radiobtnMale.setSelected(true);
        }else if(tm.getEmployeeGender().equals("Female")){
            radiobtnFemale.setSelected(true);
        }
        txtDesignation.setText(String.valueOf(tm.getDesignation()));
    }

    public void setData(SendEmployeeDataToTable send){
        this.send = send;
    }

    public void name_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapName, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void address_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapAddress, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void dob_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapAge, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void contact_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapContact, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void nic_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapNIC, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void designation_KeyTyped(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapDesignation, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }
}
