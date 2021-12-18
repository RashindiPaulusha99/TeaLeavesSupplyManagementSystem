package view.tm;

import model.SupplyDetail;

import java.util.ArrayList;

public class SupplyTM {
    private String supplyId;
    private String supplierId;
    private String supplyDate;
    private String supplyTime;
    private double weight;
    private double totalCost;
    private double transportCharge;
    private ArrayList<SupplyDetail> tea;

    public SupplyTM() {
    }

    public SupplyTM(String supplyId, String supplierId, String supplyDate, String supplyTime,double weight,double totalCost, double transportCharge, ArrayList<SupplyDetail> tea) {
        this.setSupplyId(supplyId);
        this.setSupplierId(supplierId);
        this.setSupplyDate(supplyDate);
        this.setSupplyTime(supplyTime);
        this.setWeight(weight);
        this.setTotalCost(totalCost);
        this.setTransportCharge(transportCharge);
        this.setTea(tea);
    }

    public SupplyTM(String supplyId, String supplierId, String supplyDate, String supplyTime, double weight, double totalCost) {
    }

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(String supplyDate) {
        this.supplyDate = supplyDate;
    }

    public String getSupplyTime() {
        return supplyTime;
    }

    public void setSupplyTime(String supplyTime) {
        this.supplyTime = supplyTime;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTransportCharge() {
        return transportCharge;
    }

    public void setTransportCharge(double transportCharge) {
        this.transportCharge = transportCharge;
    }

    public ArrayList<SupplyDetail> getTea() {
        return tea;
    }

    public void setTea(ArrayList<SupplyDetail> tea) {
        this.tea = tea;
    }

    @Override
    public String toString() {
        return "SupplyTM{" +
                "supplyId='" + supplyId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplyDate='" + supplyDate + '\'' +
                ", supplyTime='" + supplyTime + '\'' +
                ", weight='" + weight + '\'' +
                ", totalCost='" + totalCost + '\'' +
                ", transportCharge=" + transportCharge +
                ", tea=" + tea +
                '}';
    }
}
