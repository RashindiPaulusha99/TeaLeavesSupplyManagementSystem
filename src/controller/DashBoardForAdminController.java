package controller;

import com.jfoenix.controls.JFXButton;
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

public class DashBoardForAdminController implements Initializable {

    public StackPane adminContext;
    public Label lblDate;
    public Label lblTime;
    public Label lblRole;
    public Label lblUsername;
    public JFXButton btnDashBoard;
    public JFXButton btnEmployee;
    public JFXButton btnSupplyReports;
    public JFXButton btnPaymentReports;
    public Label lblName;

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
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO,event -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour()+" : "+currentTime.getMinute()+" : "+currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public  void  setRoleAndName(String role, String Uname,String name){
        lblRole.setText(role);
        lblUsername.setText(Uname);
        lblName.setText(name);
    }

    public void logOutToLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage window = (Stage) adminContext.getScene().getWindow();
        window.setScene(scene);
    }

    public void openEmployeeFormOnAction(ActionEvent actionEvent) throws IOException {
        btnEmployee.setStyle("-fx-background-color: lightcoral");
        btnDashBoard.setDisable(true);
        btnDashBoard.setStyle("");
        btnSupplyReports.setStyle("");
        btnPaymentReports.setStyle("");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/EmployeeForm.fxml"));
        AnchorPane pane = loader.load();
        adminContext.getChildren().setAll(pane);
    }

    public void openSupplyReports(ActionEvent actionEvent) throws IOException {
        btnSupplyReports.setStyle("-fx-background-color: lightcoral");
        btnDashBoard.setDisable(true);
        btnDashBoard.setStyle("");
        btnEmployee.setStyle("");
        btnPaymentReports.setStyle("");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/SupplyReportsForm.fxml"));
        AnchorPane pane = loader.load();
        adminContext.getChildren().setAll(pane);
    }

    public void openPaymentReports(ActionEvent actionEvent) throws IOException {
        btnPaymentReports.setStyle("-fx-background-color: lightcoral");
        btnDashBoard.setDisable(true);
        btnDashBoard.setStyle("");
        btnSupplyReports.setStyle("");
        btnEmployee.setStyle("");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/PaymentsReportsForm.fxml"));
        AnchorPane pane = loader.load();
        adminContext.getChildren().setAll(pane);
    }
}
