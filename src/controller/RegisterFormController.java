package controller;

import controller.BusinessLogicControllers.LoginController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Register;
import util.Validation;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterFormController implements Initializable {

    public AnchorPane registerContext;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public PasswordField txtConfirmPassword;
    public TextField txtUserId;
    public JFXButton btnRegister;
    public TextField txtName;

    LinkedHashMap<TextField,Pattern> map = new LinkedHashMap();

    Pattern compile_UserId = Pattern.compile("^[U-u]{1}(0){2}[-][0-9]{4}$");
    Pattern compile_UserName = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_Name = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_Password = Pattern.compile("^[A-z|!|@|#|$|%|&|0-9]{6,10}$");
    Pattern compile_ConfirmPassword = Pattern.compile("^[A-z|!|@|#|$|%|&|0-9]{6,10}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnRegister.setDisable(true);

        setValidation();
    }

    public void setValidation(){
        map.put(txtUserId,compile_UserId);
        map.put(txtUsername,compile_UserName);
        map.put(txtName,compile_Name);
        map.put(txtPassword,compile_Password);
        map.put(txtConfirmPassword,compile_ConfirmPassword);
    }

    public void user_KeyReleased(KeyEvent keyEvent) {
        Object response =  new Validation().Validate(map, btnRegister);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    public void registerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String role = null;

        if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
            txtConfirmPassword.getParent().setStyle("-fx-border-color: limegreen");
            if (new LoginController().searchId(txtUserId.getText())){
                new Alert(Alert.AlertType.WARNING,"UserId Already Exists.").show();
            }else {
                if (txtUsername.getText().equalsIgnoreCase("Manager")) {
                    role = "Admin";
                } else if (txtUsername.getText().equalsIgnoreCase("Data Entry Operator")) {
                    role = "User";
                }

                Register register = new Register(
                        txtUserId.getText(),
                        txtPassword.getText(),
                        txtUsername.getText(),
                        txtName.getText(),
                        role
                );
                if (txtUsername.getText().equalsIgnoreCase("Manager") || txtUsername.getText().equalsIgnoreCase("Data Entry Operator")) {

                    if (new LoginController().registerData(register)) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Registration Successful.").show();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again.").show();
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Invalid Username.").show();
                }
            }
        }else {
            txtConfirmPassword.getParent().setStyle("-fx-border-color: crimson");
            new Alert(Alert.AlertType.WARNING, "Check Your Password.").show();
        }
    }
}
