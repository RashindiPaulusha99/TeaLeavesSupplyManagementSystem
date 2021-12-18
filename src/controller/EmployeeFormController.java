package controller;

import controller.BusinessLogicControllers.EmployeeController;
import SendData.SendEmployeeDataToTable;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;
import model.Employee;
import view.tm.EmployeeTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable , SendEmployeeDataToTable {

    public TableView<EmployeeTM> tblEmployee;
    public TextField txtSearch;
    public TableColumn colRemove;
    public TextField txtAmountEmployee;

    int index = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("employeeId"));
            tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("employeeName"));
            tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
            tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("employeeDOB"));
            tblEmployee.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("employeeGender"));
            tblEmployee.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("employeeContact"));
            tblEmployee.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("employeeNIC"));
            tblEmployee.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("designation"));
            tblEmployee.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("btn"));

            loadEmployeeDataToTable();

            loadAmountEmployee();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblEmployee.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            index= (int) newValue;
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {

                    search(newValue);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
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

    public void setOnAction(String Value){

        button.setOnAction((event) -> {
            ButtonType yes= new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no= new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Employee?",yes,no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no)==yes){

                try {
                    if (new EmployeeController().deleteEmployee(Value)) {
                        new Alert(Alert.AlertType.CONFIRMATION,"Delete Successful.").show();
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Try Again.").showAndWait();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }

                try {

                    loadEmployeeDataToTable();

                    loadAmountEmployee();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }

            }else{
            }
        });
    }

    private ObservableList<EmployeeTM> loadEmployeeDataToTable() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees = new EmployeeController().viewEmployee();
        ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();

        employees.forEach(e -> {
            setBtn();
            obList.add(new EmployeeTM(e.getEmployeeId(),e.getEmployeeName(),e.getEmployeeDOB(),e.getEmployeeAddress(),e.getEmployeeContact(),e.getEmployeeNIC(),e.getEmployeeGender(),e.getDesignation(),button));
            setOnAction(e.getEmployeeId());
        });
        tblEmployee.setItems(obList);
        return obList;
    }

    public void loadAmountEmployee() throws SQLException, ClassNotFoundException {
        txtAmountEmployee.setText(String.valueOf(new EmployeeController().calculateEmployees()));
        txtAmountEmployee.setDisable(true);
    }

    @Override
    public void loadData() throws SQLException, ClassNotFoundException {
        tblEmployee.getItems().setAll(loadEmployeeDataToTable());
        txtAmountEmployee.setText(String.valueOf(new EmployeeController().calculateEmployees()));
    }

    public void openAddEmployeeForm(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String id = new EmployeeController().getEmployeeId();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AddEmployeeForm.fxml"));
        Parent load = loader.load();
        AddEmployeeFormController controller = loader.<AddEmployeeFormController>getController();
        controller.setData(this);
        controller.setId(id);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void openEditEmployeeForm(ActionEvent actionEvent) throws IOException {
        EmployeeTM em = tblEmployee.getSelectionModel().getSelectedItem();

        if (index == -1){
            new Alert(Alert.AlertType.WARNING,"Please Select A Raw.").showAndWait();
        }else {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/EditEmployeeForm.fxml"));
            Parent load = loader.load();
            EditEmployeeFormController controller = loader.<EditEmployeeFormController>getController();
            controller.setDataToTextfields(em);
            controller.setData(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.show();
        }
    }

    public void searchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        ArrayList<Employee> searchEmployees = new EmployeeController().searchEmployee(value);
        ObservableList<EmployeeTM> tableData = FXCollections.observableArrayList();
        for (Employee emp : searchEmployees) {
            setBtn();
            tableData.add(new EmployeeTM(
                    emp.getEmployeeId(),
                    emp.getEmployeeName(),
                    emp.getEmployeeDOB(),
                    emp.getEmployeeAddress(),
                    emp.getEmployeeContact(),
                    emp.getEmployeeNIC(),
                    emp.getEmployeeGender(),
                    emp.getDesignation(),
                    button
            ));
            setOnAction(emp.getEmployeeId());
        }
        tblEmployee.getItems().setAll(tableData);
    }

}
