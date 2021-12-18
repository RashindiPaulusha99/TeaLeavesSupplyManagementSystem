package controller;

import controller.BusinessLogicControllers.SupplierController;
import SendData.SendSupplierDataToTable;
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
import model.Supplier;
import view.tm.SupplierTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable, SendSupplierDataToTable {

    public TextField txtSearch;
    public TableView<SupplierTM> tblSupplierDetails;
    public TextField txtAmountSuppliers;

    int index = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblSupplierDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        tblSupplierDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        tblSupplierDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        tblSupplierDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("supplierGender"));
        tblSupplierDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("supplierContact"));
        tblSupplierDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("supplierNIC"));
        tblSupplierDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("areaOfTheLand"));
        tblSupplierDetails.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {

            loadSupplierDataToTable();

            loadAmountOfSuppliers();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblSupplierDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Supplier?",yes,no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no)==yes){

                try {
                    if (new SupplierController().deleteSupplier(Value)) {
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

                    loadSupplierDataToTable();
                    loadAmountOfSuppliers();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }

            }else{
            }
        });
    }

    private ObservableList<SupplierTM> loadSupplierDataToTable() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers = new SupplierController().viewSupplier();
        ObservableList<SupplierTM> obList = FXCollections.observableArrayList();

        suppliers.forEach(e -> {
            setBtn();
            obList.add(new SupplierTM(e.getSupplierId(),e.getSupplierName(),e.getSupplierAddress(),e.getSupplierContact(),e.getSupplierNIC(),e.getSupplierGender(),e.getAreaOfTheLand(),button));
            setOnAction(e.getSupplierId());
        });

        tblSupplierDetails.setItems(obList);
        return obList;
    }

    public void loadAmountOfSuppliers() throws SQLException, ClassNotFoundException {
        txtAmountSuppliers.setText(String.valueOf(new SupplierController().calculateSuppliers()));
        txtAmountSuppliers.setDisable(true);
    }

    public void openAddSupplierForm(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String id = new SupplierController().getSupplierId();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AddSupplierForm.fxml"));
        Parent load = loader.load();
        AddSupplierFormController controller = loader.<AddSupplierFormController>getController();
        controller.setId(id);
        controller.setData(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void openEditSupplierForm(ActionEvent actionEvent) throws IOException {
        SupplierTM sm = tblSupplierDetails.getSelectionModel().getSelectedItem();

        if (index == -1){
            new Alert(Alert.AlertType.WARNING,"Please Select A Raw.").showAndWait();
        }else {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/EditSupplierForm.fxml"));
            Parent load = loader.load();
            EditSupplierFormController controller = loader.<EditSupplierFormController>getController();
            controller.setDataToTextfields(sm);
            controller.setData(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.show();
        }
    }

    public void searchSupplierData(ActionEvent event) throws SQLException, ClassNotFoundException {
        search(txtSearch.getText());
    }

    private void search(String value) throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> searchSupplier = new SupplierController().searchSupplier(value);
        ObservableList<SupplierTM> tableData = FXCollections.observableArrayList();
        for (Supplier sp : searchSupplier) {
            setBtn();
            tableData.add(new SupplierTM(
                    sp.getSupplierId(),
                    sp.getSupplierName(),
                    sp.getSupplierAddress(),
                    sp.getSupplierContact(),
                    sp.getSupplierNIC(),
                    sp.getSupplierGender(),
                    sp.getAreaOfTheLand(),
                    button
            ));
            setOnAction(sp.getSupplierId());
        }
        tblSupplierDetails.getItems().setAll(tableData);
    }

    @Override
    public void loadData() throws SQLException, ClassNotFoundException {
        tblSupplierDetails.getItems().setAll(loadSupplierDataToTable());
        txtAmountSuppliers.setText(String.valueOf(new SupplierController().calculateSuppliers()));
    }
}
