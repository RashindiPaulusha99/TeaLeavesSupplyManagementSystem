package model;

public class Supplier {
    private String supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierContact;
    private String supplierNIC;
    private String supplierGender;
    private int areaOfTheLand;

    public Supplier() {
    }

    public Supplier(String supplierId, String supplierName, String supplierAddress, String supplierContact, String supplierNIC, String supplierGender, int areaOfTheLand) {
        this.setSupplierId(supplierId);
        this.setSupplierName(supplierName);
        this.setSupplierAddress(supplierAddress);
        this.setSupplierContact(supplierContact);
        this.setSupplierNIC(supplierNIC);
        this.setSupplierGender(supplierGender);
        this.setAreaOfTheLand(areaOfTheLand);
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierNIC() {
        return supplierNIC;
    }

    public void setSupplierNIC(String supplierNIC) {
        this.supplierNIC = supplierNIC;
    }

    public String getSupplierGender() {
        return supplierGender;
    }

    public void setSupplierGender(String supplierGender) {
        this.supplierGender = supplierGender;
    }

    public int getAreaOfTheLand() {
        return areaOfTheLand;
    }

    public void setAreaOfTheLand(int areaOfTheLand) {
        this.areaOfTheLand = areaOfTheLand;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierAddress='" + supplierAddress + '\'' +
                ", supplierContact='" + supplierContact + '\'' +
                ", supplierNIC='" + supplierNIC + '\'' +
                ", supplierGender='" + supplierGender + '\'' +
                ", areaOfTheLand='" + areaOfTheLand + '\'' +
                '}';
    }
}
