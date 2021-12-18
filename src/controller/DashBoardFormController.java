package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {

    public Label lblDate;
    public Label lblTime;
    public StackPane userContext;
    public Label lblUser;
    public Label lblName;
    public JFXButton btnDashBoard;
    public JFXButton btnSuppliers;
    public JFXButton btnTea;
    public JFXButton btnSupply;
    public JFXButton btnPayment;
    public Label lblUName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnDashBoard.setStyle("-fx-background-color: lightcoral");

        loadDateAndTime();
    }

    private void loadDateAndTime() {
        /*load date*/
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        /*load time*/
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                    LocalTime currentTime = LocalTime.now();
                    lblTime.setText(currentTime.getHour()+" : "+currentTime.getMinute()+" : "+currentTime.getSecond());
        }),
            new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public  void  setRoleAndName(String role, String Uname,String name){
        lblUser.setText(role);
        lblUName.setText(Uname);
        lblName.setText(name);
    }

    public void logOutToLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage window = (Stage) userContext.getScene().getWindow();
        window.setScene(scene);
    }

    public void openSupplierForm(ActionEvent actionEvent) throws IOException {
        btnSuppliers.setStyle("-fx-background-color: lightcoral");
        btnDashBoard.setDisable(true);
        btnDashBoard.setStyle("");
        btnTea.setStyle("");
        btnSupply.setStyle("");
        btnPayment.setStyle("");
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/SupplierForm.fxml"));
        AnchorPane pane = fxmlLoader.load();
        userContext.getChildren().setAll(pane);
    }

    public void openTeaForm(ActionEvent actionEvent) throws IOException {
        btnTea.setStyle("-fx-background-color: lightcoral");
        btnDashBoard.setDisable(true);
        btnDashBoard.setStyle("");
        btnSuppliers.setStyle("");
        btnSupply.setStyle("");
        btnPayment.setStyle("");
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/TeaForm.fxml"));
        AnchorPane pane = fxmlLoader.load();
        userContext.getChildren().setAll(pane);
    }

    public void openSupplyForm(ActionEvent actionEvent) throws IOException {
        btnSupply.setStyle("-fx-background-color: lightcoral");
        btnDashBoard.setDisable(true);
        btnDashBoard.setStyle("");
        btnTea.setStyle("");
        btnSuppliers.setStyle("");
        btnPayment.setStyle("");
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/SupplyForm.fxml"));
        AnchorPane pane = fxmlLoader.load();
        userContext.getChildren().setAll(pane);
    }

    public void openPaymentForm(ActionEvent actionEvent) throws IOException {
        btnPayment.setStyle("-fx-background-color: lightcoral");
        btnDashBoard.setDisable(true);
        btnDashBoard.setStyle("");
        btnTea.setStyle("");
        btnSupply.setStyle("");
        btnSuppliers.setStyle("");
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/PaymentForm.fxml"));
        AnchorPane pane = fxmlLoader.load();
        userContext.getChildren().setAll(pane);
    }
}
