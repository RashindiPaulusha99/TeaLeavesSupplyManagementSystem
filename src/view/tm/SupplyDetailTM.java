package view.tm;

import javafx.scene.control.Button;

public class SupplyDetailTM {
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
    private Button btn;

    public SupplyDetailTM() {
    }

    public SupplyDetailTM(String teaCode, double wholeWeight, int sackQTY, double sackWeight, double coarse, double wet, double boiled, double reject, double netWeight, double cost, Button btn) {
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
        this.setBtn(btn);
    }

    public SupplyDetailTM(String teaCode, double wholeWeight, int sackQTY, double sackWeight, double coarse, double wet, double boiled, double reject, double netWeight, double cost) {
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "SupplyDetailTM{" +
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
                ", btn=" + btn +
                '}';
    }
}
