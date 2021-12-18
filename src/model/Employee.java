package model;

public class Employee {
    private String employeeId;
    private String employeeName;
    private String employeeDOB;
    private String employeeAddress;
    private String employeeContact;
    private String employeeNIC;
    private String employeeGender;
    private String designation;

    public Employee() {
    }

    public Employee(String employeeId, String employeeName, String employeeDOB, String employeeAddress, String employeeContact, String employeeNIC, String employeeGender, String designation) {
        this.setEmployeeId(employeeId);
        this.setEmployeeName(employeeName);
        this.setEmployeeDOB(employeeDOB);
        this.setEmployeeAddress(employeeAddress);
        this.setEmployeeContact(employeeContact);
        this.setEmployeeNIC(employeeNIC);
        this.setEmployeeGender(employeeGender);
        this.setDesignation(designation);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeDOB() {
        return employeeDOB;
    }

    public void setEmployeeDOB(String employeeDOB) {
        this.employeeDOB = employeeDOB;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeContact() {
        return employeeContact;
    }

    public void setEmployeeContact(String employeeContact) {
        this.employeeContact = employeeContact;
    }

    public String getEmployeeNIC() {
        return employeeNIC;
    }

    public void setEmployeeNIC(String employeeNIC) {
        this.employeeNIC = employeeNIC;
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeDOB=" + employeeDOB +
                ", employeeAddress='" + employeeAddress + '\'' +
                ", employeeContact='" + employeeContact + '\'' +
                ", employeeNIC='" + employeeNIC + '\'' +
                ", employeeGender='" + employeeGender + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
