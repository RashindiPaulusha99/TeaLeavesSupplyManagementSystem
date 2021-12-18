package controller;

import controller.BusinessLogicControllers.TeaController;
import SendData.SendTeaDataToTable;
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
import model.Tea;
import view.tm.TeaTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeaFormController implements Initializable, SendTeaDataToTable {

    public TableView<TeaTM> tblTeaDetails;
    public TextField txtSearch;
    public Label lblTotalQty;
    public Label lblMaxQty;
    public Label lblMinQty;
    public Label lblMaxCode;
    public Label lblMinCode;

    int index = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tblTeaDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblTeaDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblTeaDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblTeaDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblTeaDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblTeaDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("btnEdit"));
        tblTeaDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        try {

            loadTeaDetailsForTable();

            setTeaStock();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblTeaDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            index = (int) newValue;
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

    public void setTeaStock() throws SQLException, ClassNotFoundException {
        lblTotalQty.setText(new TeaController().calculateTeaStock()+" KG");
        lblMaxCode.setText(new TeaController().calculateMaxTeaStock().get(0).getCode()+" :");
        lblMaxQty.setText(new TeaController().calculateMaxTeaStock().get(0).getQty()+" KG");
        lblMinCode.setText(new TeaController().calculateMinTeaStock().get(0).getCode()+" :");
        lblMinQty.setText(new TeaController().calculateMinTeaStock().get(0).getQty()+" KG");
    }

    public void openAddTeaForm(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AddTeaForm.fxml"));
        Parent load = loader.load();
        AddTeaFormController controller = loader.<AddTeaFormController>getController();
        controller.setData(this);
        controller.setId(new TeaController().getTeaId());
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    JFXButton buttonDelete;
    public void setDBtn(){
        buttonDelete = new JFXButton();
        buttonDelete.setStyle("-fx-border-color: red");
        Image img = new Image("assets\\icons8-delete-64.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(27);
        view.setFitWidth(36);
        buttonDelete.setGraphic(view);
    }

    JFXButton buttonUpdate;
    public void setUBtn(){
        buttonUpdate = new JFXButton();
        buttonUpdate.setStyle("-fx-border-color: blueviolet");
        Image img = new Image("assets\\icons8-edit-48.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(27);
        view.setFitWidth(36);
        buttonUpdate.setGraphic(view);
    }

    public void setOnActionForDelete(String Value){

        buttonDelete.setOnAction((event) -> {
            ButtonType yes= new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no= new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Tea Species?",yes,no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no)==yes){

                try {
                    if (new TeaController().deleteTea(Value)) {
                        new Alert(Alert.AlertType.CONFIRMATION,"Delete Successful.").show();
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Try Again.").showAndWait();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                try {

                    loadTeaDetailsForTable();

                    setTeaStock();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }

            }else{
            }
        });
    }

    public void setOnActionForUpdate(){

        buttonUpdate.setOnAction((event) -> {

            TeaTM tm = tblTeaDetails.getSelectionModel().getSelectedItem();

                if (index == -1) {
                    new Alert(Alert.AlertType.WARNING, "Please Select A Raw.").show();
                } else {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/EditTeaForm.fxml"));
                    Parent load = null;
                    try {
                        load = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EditTeaFormController controller = loader.<EditTeaFormController>getController();
                    controller.setDataToTextfields(tm);
                    controller.setData(this);
                    Scene scene = new Scene(load);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }
        });
    }

    public ObservableList<TeaTM> loadTeaDetailsForTable() throws SQLException, ClassNotFoundException {
        ArrayList<Tea> teas = new TeaController().viewTea();
        ObservableList<TeaTM> obList = FXCollections.observableArrayList();

        teas.forEach(e -> {
            setDBtn();
            setUBtn();
            obList.add(new TeaTM(e.getCode(),e.getType(),e.getName(),e.getUnitPrice(),e.getQty(),buttonUpdate,buttonDelete));
            setOnActionForDelete(e.getCode());
            setOnActionForUpdate();

        });
        tblTeaDetails.setItems(obList);
        return obList;
    }

    @Override
    public void loadData() throws SQLException, ClassNotFoundException {
        tblTeaDetails.getItems().setAll(loadTeaDetailsForTable());
        setTeaStock();
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        ArrayList<Tea> searchtea = new TeaController().searchTea(value);
        ObservableList<TeaTM> tableData = FXCollections.observableArrayList();
        for (Tea tm : searchtea) {
            setDBtn();
            setUBtn();
            tableData.add(new TeaTM(
                    tm.getCode(),
                    tm.getType(),
                    tm.getName(),
                    tm.getUnitPrice(),
                    tm.getQty(),
                    buttonUpdate,
                    buttonDelete
            ));
            setOnActionForDelete(tm.getCode());
            setOnActionForUpdate();
        }
        tblTeaDetails.getItems().setAll(tableData);
    }

    public void searchTeaDetailsOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }
}
