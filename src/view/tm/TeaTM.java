package view.tm;

import javafx.scene.control.Button;

import java.math.BigDecimal;

public class TeaTM {
    private String code;
    private String type;
    private String name;
    private BigDecimal unitPrice;
    private int qty;
    private Button btnEdit;
    private Button btnDelete;

    public TeaTM() {
    }

    public TeaTM(String code, String type, String name, BigDecimal unitPrice, int qty, Button btnEdit, Button btnDelete) {
        this.setCode(code);
        this.setType(type);
        this.setName(name);
        this.setUnitPrice(unitPrice);
        this.setQty(qty);
        this.setBtnEdit(btnEdit);
        this.setBtnDelete(btnDelete);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Button getBtnEdit() {
        return btnEdit;
    }

    public void setBtnEdit(Button btnEdit) {
        this.btnEdit = btnEdit;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "TeaTM{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", btnEdit=" + btnEdit +
                ", btnDelete=" + btnDelete +
                '}';
    }
}
