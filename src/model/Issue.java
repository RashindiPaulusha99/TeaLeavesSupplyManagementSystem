package model;

public class Issue {
    private String employeeId;
    private String ticketId;
    private String description;
    private String designation;
    private String date;

    public Issue(String employeeId) {
        this.setEmployeeId(employeeId);
    }

    public Issue(String employeeId, String ticketId, String description, String designation, String date) {
        this.setEmployeeId(employeeId);
        this.setTicketId(ticketId);
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

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
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
        return "Issue{" +
                "employeeId='" + employeeId + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", description='" + description + '\'' +
                ", designation='" + designation + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
