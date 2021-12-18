package view.tm;

public class PaymentTM {
    private String paymentId;
    private String supplierId;
    private String paymentDate;
    private String paymentTime;
    private double teaPacket;
    private double fertilizer;
    private double advance;
    private double discount ;
    private double totalPayment;
    private int teaPacketQty;
    private double teaPacketUnitPrice;
    private double fertilizerQty;
    private double fertilizerUnitPrice;
    private double transport ;
    private double costForLeaves ;

    public PaymentTM() {
    }

    public PaymentTM(String paymentId, String supplierId, String paymentDate, String paymentTime, double teaPacket, double fertilizer, double advance, double discount, double totalPayment, int teaPacketQty, double teaPacketUnitPrice, double fertilizerQty, double fertilizerUnitPrice, double transport, double costForLeaves) {
        this.paymentId = paymentId;
        this.supplierId = supplierId;
        this.paymentDate = paymentDate;
        this.paymentTime = paymentTime;
        this.teaPacket = teaPacket;
        this.fertilizer = fertilizer;
        this.advance = advance;
        this.discount = discount;
        this.totalPayment = totalPayment;
        this.teaPacketQty = teaPacketQty;
        this.teaPacketUnitPrice = teaPacketUnitPrice;
        this.fertilizerQty = fertilizerQty;
        this.fertilizerUnitPrice = fertilizerUnitPrice;
        this.transport = transport;
        this.costForLeaves = costForLeaves;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getTeaPacketQty() {
        return teaPacketQty;
    }

    public void setTeaPacketQty(int teaPacketQty) {
        this.teaPacketQty = teaPacketQty;
    }

    public double getTeaPacketUnitPrice() {
        return teaPacketUnitPrice;
    }

    public void setTeaPacketUnitPrice(double teaPacketUnitPrice) {
        this.teaPacketUnitPrice = teaPacketUnitPrice;
    }

    public double getTeaPacket() {
        return teaPacket;
    }

    public void setTeaPacket(double teaPacket) {
        this.teaPacket = teaPacket;
    }

    public double getFertilizerQty() {
        return fertilizerQty;
    }

    public void setFertilizerQty(double fertilizerQty) {
        this.fertilizerQty = fertilizerQty;
    }

    public double getFertilizerUnitPrice() {
        return fertilizerUnitPrice;
    }

    public void setFertilizerUnitPrice(double fertilizerUnitPrice) {
        this.fertilizerUnitPrice = fertilizerUnitPrice;
    }

    public double getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(double fertilizer) {
        this.fertilizer = fertilizer;
    }

    public double getAdvance() {
        return advance;
    }

    public void setAdvance(double advance) {
        this.advance = advance;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTransport() {
        return transport;
    }

    public void setTransport(double transport) {
        this.transport = transport;
    }

    public double getCostForLeaves() {
        return costForLeaves;
    }

    public void setCostForLeaves(double costForLeaves) {
        this.costForLeaves = costForLeaves;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }


    @Override
    public String toString() {
        return "PaymentTM{" +
                "paymentId='" + paymentId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentTime='" + paymentTime + '\'' +
                ", teaPacketQty=" + teaPacketQty +
                ", teaPacketUnitPrice=" + teaPacketUnitPrice +
                ", teaPacket=" + teaPacket +
                ", fertilizerQty=" + fertilizerQty +
                ", fertilizerUnitPrice=" + fertilizerUnitPrice +
                ", fertilizer=" + fertilizer +
                ", advance=" + advance +
                ", discount=" + discount +
                ", transport=" + transport +
                ", costForLeaves=" + costForLeaves +
                ", totalPayment=" + totalPayment +
                '}';
    }
}
