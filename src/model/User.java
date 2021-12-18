package model;

public class User {
    private String supplierId;
    private String eId;
    private String name;

    public User() {
    }

    public User(String supplierId, String eId, String name) {
        this.setSupplierId(supplierId);
        this.seteId(eId);
        this.setName(name);
    }


    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "supplierId='" + supplierId + '\'' +
                ", eId='" + eId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
