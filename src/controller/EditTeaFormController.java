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
import view.tm.TeaTM;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditTeaFormController implements Initializable {

    public TextField txtTeaId;
    public ComboBox<String> cmbType;
    public TextField txtUnitPrice;
    public TextField txtName;
    public JFXButton btnUpdate;

    private SendTeaDataToTable send;

    LinkedHashMap<TextField, Pattern> mapName = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> mapPrice = new LinkedHashMap();

    Pattern compile_TeaName = Pattern.compile("^[VP|vp]{2}[0-9]{2,4}$");
    Pattern compile_UnitPrice = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String > types = FXCollections.observableArrayList(
                "Camellia sinensis sinensis",
                "Camellia sinensis assamica"
        );
        cmbType.setItems(types);

        setValidation();

    }

    private void setValidation() {
        mapName.put(txtName, compile_TeaName);
        mapPrice.put(txtUnitPrice, compile_UnitPrice);
    }

    public void name_KeYType(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapName, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void price_KeyType(KeyEvent keyEvent) {
        Object response = new Validation().Validate(mapPrice, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errortextfield = (TextField) response;
                errortextfield.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void updateDetailsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Tea tea = new Tea(
                txtTeaId.getText(),
                String.valueOf(cmbType.getValue()),
                txtName.getText(),
                new BigDecimal(txtUnitPrice.getText()),
                0
        );

        if (new TeaController().updateTea(tea)){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated A Tea Species.").showAndWait();
            send.loadData();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again.").show();
        }
    }

    public void setDataToTextfields(TeaTM tm){
        txtTeaId.setText(String.valueOf(tm.getCode()));
        txtTeaId.setDisable(true);
        txtName.setText(String.valueOf(tm.getName()));
        cmbType.setValue(tm.getType());
        txtUnitPrice.setText(String.valueOf(tm.getUnitPrice()));
    }

    public void setData(SendTeaDataToTable send){

        this.send = send;
    }

}
