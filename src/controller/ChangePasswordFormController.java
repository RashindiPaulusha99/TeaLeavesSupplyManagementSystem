package controller;

import controller.BusinessLogicControllers.LoginController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import util.Validation;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ChangePasswordFormController {
    public TextField txtUsername;
    public PasswordField txtPassword;
    public PasswordField txtConfirmPassword;
    public AnchorPane paneUsername;
    public AnchorPane paneNewPassword;
    public AnchorPane paneConfirmPassword;
    public JFXButton btnReset;
    public Label lblCPassword;
    public Label lblUsername;

    LinkedHashMap<TextField,Pattern> map = new LinkedHashMap();

    Pattern compile_Password = Pattern.compile("^[A-z|!|@|#|$|%|&|0-9]{6,10}$");
    Pattern compile_ConfirmPassword = Pattern.compile("^[A-z|!|@|#|$|%|&|0-9]{6,10}$");

    public void initialize(){

        btnReset.setDisable(true);

        setValidation();
    }

    public void setValidation(){
        map.put(txtPassword,compile_Password);
        map.put(txtConfirmPassword,compile_ConfirmPassword);
    }

    public void resetPasswordOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String username = txtUsername.getText();
        String newPassword = txtPassword.getText();

        if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
            txtConfirmPassword.getParent().setStyle("-fx-border-color: limegreen");
            lblCPassword.setStyle("-fx-text-fill: limegreen");

            if (new LoginController().checkUsername(username)) {
                new LoginController().insertNewPassword(newPassword, username);
                new Alert(Alert.AlertType.CONFIRMATION, "Successful Reset").show();
            } else {
                lblUsername.setStyle("-fx-text-fill: red");
                new Alert(Alert.AlertType.WARNING, "Please Enter Correct Username.").show();
            }
        }else {
            txtConfirmPassword.getParent().setStyle("-fx-border-color: crimson");
            lblCPassword.setStyle("-fx-text-fill: red");
            new Alert(Alert.AlertType.WARNING, "Check Your Password.").show();
        }
    }

    public void confirmPassword_keyReleased(KeyEvent keyEvent) {
        Object response =  new Validation().Validate(map, btnReset);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }
}
