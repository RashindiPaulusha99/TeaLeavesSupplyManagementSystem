package model;

import java.math.BigDecimal;

public class Tea {
    private String code;
    private String type;
    private String name;
    private BigDecimal unitPrice;
    private int qty;

    public Tea() {
    }

    public Tea(String code, String type, String name, BigDecimal unitPrice, int qty) {
        this.setCode(code);
        this.setType(type);
        this.setName(name);
        this.setUnitPrice(unitPrice);
        this.setQty(qty);
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

    @Override
    public String toString() {
        return "Tea{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                '}';
    }
}
