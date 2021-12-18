package model;

public class Service {
    private String employeeId;
    private String supplyId;
    private String description;
    private String designation;
    private String date;

    public Service() {
    }

    public Service(String employeeId, String supplyId, String description, String designation, String date) {
        this.setEmployeeId(employeeId);
        this.setSupplyId(supplyId);
        this.setDescription(description);
        this.setDesignation(designation);
        this.setDate(date);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Service{" +
                "employeeId='" + employeeId + '\'' +
                ", supplyId='" + supplyId + '\'' +
                ", description='" + description + '\'' +
                ", designation='" + designation + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
