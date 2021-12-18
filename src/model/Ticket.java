package model;

public class Ticket {
    private String ticketId;
    private String supplierId;
    private String ticketDate;
    private String ticketTime;
    private double wholeWeight;
    private double netWeight;

    public Ticket() {
    }

    public Ticket(String ticketId, String supplierId, String ticketDate, String ticketTime, double wholeWeight, double netWeight) {
        this.setTicketId(ticketId);
        this.setSupplierId(supplierId);
        this.setTicketDate(ticketDate);
        this.setTicketTime(ticketTime);
        this.setWholeWeight(wholeWeight);
        this.setNetWeight(netWeight);
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public double getWholeWeight() {
        return wholeWeight;
    }

    public void setWholeWeight(double wholeWeight) {
        this.wholeWeight = wholeWeight;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", ticketDate='" + ticketDate + '\'' +
                ", ticketTime='" + ticketTime + '\'' +
                ", wholeWeight=" + wholeWeight +
                ", netWeight=" + netWeight +
                '}';
    }
}
