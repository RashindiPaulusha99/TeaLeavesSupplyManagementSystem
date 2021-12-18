package controller;

import controller.BusinessLogicControllers.LoginController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Register;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    public AnchorPane loginContext;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public Label lblErrorMassage;
    public JFXButton btnLogin;

    public void openDashBoardFormOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String password= txtPassword.getText();
        String userName= txtUsername.getText();

        Register register =  new LoginController().checkCorrectLoginData(userName, password);

        if (new LoginController().checkCorrectDataOfLoginForm(userName, password)){

            if (register.getRole().equalsIgnoreCase("Admin")){

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/DashBoardForAdmin.fxml"));
                Parent load = loader.load();
                DashBoardForAdminController controller = loader.<DashBoardForAdminController>getController();
                controller.setRoleAndName("Admin",register.getUserName(),register.getName());
                Stage window = (Stage) loginContext.getScene().getWindow();
                window.setScene(new Scene(load));

            }else if (register.getRole().equalsIgnoreCase("User")) {

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/DashBoardForm.fxml"));
                Parent load = loader.load();
                DashBoardFormController controller = loader.<DashBoardFormController>getController();
                controller.setRoleAndName("User",register.getUserName(),register.getName());
                Stage window = (Stage) loginContext.getScene().getWindow();
                window.setScene(new Scene(load));
            }

        }else {
            lblErrorMassage.setText("Please Enter Correct Username Or Password");
        }
    }

    public void openRegisterFormOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/RegisterForm.fxml"))));
        stage.show();
    }

    public void backToWelcomeFormOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) loginContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/WelcomeForm.fxml"))));
    }

    public void openChangePasswordFormOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ChangePasswordForm.fxml"))));
        stage.show();
    }
}
