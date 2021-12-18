package controller;

import controller.BusinessLogicControllers.TeaController;
import SendData.SendTeaDataToTable;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Tea;
import util.Validation;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddTeaFormController implements Initializable {

    public TextField txtTeaId;
    public TextField txtName;
    public ComboBox<String> cmbTeaTypes;
    public TextField txtUnitPrice;
    public JFXButton btnSave;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    Pattern compile_TeaName = Pattern.compile("^[VP|vp]{2}[0-9]{2,4}$");
    Pattern compile_UnitPrice = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");

    private SendTeaDataToTable send;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setDisable(true);

        ObservableList<String > types = FXCollections.observableArrayList(
                "Camellia sinensis sinensis",
                "Camellia sinensis assamica"
        );
        cmbTeaTypes.setItems(types);

        setValidation();
    }

    private void setValidation() {
        map.put(txtName, compile_TeaName);
        map.put(txtUnitPrice, compile_UnitPrice);
    }

    public void tea_KeyReleased(KeyEvent keyEvent) {
        Object response = new Validation().Validate(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void setId(String id){
        txtTeaId.setText(id);
        txtTeaId.setDisable(true);
    }

    public void saveTeaOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        Tea tea = new Tea(
                txtTeaId.getText(),
                String.valueOf(cmbTeaTypes.getValue()),
                txtName.getText(),
                new BigDecimal(txtUnitPrice.getText()),
                0
        );

        String id = txtTeaId.getText();

        if (new TeaController().searchId(id)){
            new Alert(Alert.AlertType.WARNING,"Already Exists.").show();
        }else {

            if (txtTeaId.getText().isEmpty() || txtName.getText().isEmpty() || txtUnitPrice.getText().isEmpty() || cmbTeaTypes.getValue()==null) {
                new Alert(Alert.AlertType.WARNING, "All Fields Are Required.").show();
            }else {

                if (new TeaController().saveTea(tea)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved Tea species.").showAndWait();
                    send.loadData();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again.").show();
                }
            }
        }
    }

    public void clearTeaOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        txtTeaId.setText(new TeaController().getTeaId());
        txtTeaId.setDisable(true);
        txtName.clear();
        txtUnitPrice.clear();
        cmbTeaTypes.hide();
    }

    public void setData(SendTeaDataToTable send){
        this.send = send;
    }

}
