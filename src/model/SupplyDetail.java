package model;

import com.jfoenix.controls.JFXButton;

public class SupplyDetail {
    private String teaCode;
    private double wholeWeight;
    private int sackQTY;
    private double sackWeight;
    private double coarse;
    private double wet;
    private double boiled;
    private double reject;
    private double netWeight;
    private double cost;

    public SupplyDetail(String text, double wholeWeight, int sackQty, double sackWeight, double coarse, double waterWeight, double boiled, double reject, double netWeight, double price, JFXButton button) {
    }

    public SupplyDetail(String teaCode, double wholeWeight, int sackQTY, double sackWeight, double coarse, double wet, double boiled, double reject, double netWeight,double cost) {
        this.setTeaCode(teaCode);
        this.setWholeWeight(wholeWeight);
        this.setSackQTY(sackQTY);
        this.setSackWeight(sackWeight);
        this.setCoarse(coarse);
        this.setWet(wet);
        this.setBoiled(boiled);
        this.setReject(reject);
        this.setNetWeight(netWeight);
        this.setCost(cost);
    }

    public String getTeaCode() {
        return teaCode;
    }

    public void setTeaCode(String teaCode) {
        this.teaCode = teaCode;
    }

    public double getWholeWeight() {
        return wholeWeight;
    }

    public void setWholeWeight(double wholeWeight) {
        this.wholeWeight = wholeWeight;
    }

    public int getSackQTY() {
        return sackQTY;
    }

    public void setSackQTY(int sackQTY) {
        this.sackQTY = sackQTY;
    }

    public double getSackWeight() {
        return sackWeight;
    }

    public void setSackWeight(double sackWeight) {
        this.sackWeight = sackWeight;
    }

    public double getCoarse() {
        return coarse;
    }

    public void setCoarse(double coarse) {
        this.coarse = coarse;
    }

    public double getWet() {
        return wet;
    }

    public void setWet(double wet) {
        this.wet = wet;
    }

    public double getBoiled() {
        return boiled;
    }

    public void setBoiled(double boiled) {
        this.boiled = boiled;
    }

    public double getReject() {
        return reject;
    }

    public void setReject(double reject) {
        this.reject = reject;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "SupplyDetail{" +
                "teaCode='" + teaCode + '\'' +
                ", wholeWeight=" + wholeWeight +
                ", sackQTY=" + sackQTY +
                ", sackWeight=" + sackWeight +
                ", coarse=" + coarse +
                ", wet=" + wet +
                ", boiled=" + boiled +
                ", reject=" + reject +
                ", netWeight=" + netWeight +
                ", cost=" + cost +
                '}';
    }
}
