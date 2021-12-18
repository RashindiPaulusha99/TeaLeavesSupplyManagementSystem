package controller.BusinessLogicControllers;

import db.DbConnection;
import model.Payment;
import model.Supply;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentController {

    public String getPaymentIds() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT paymentId FROM Payment ORDER BY paymentId DESC LIMIT 1");
        ResultSet rst = pstm.executeQuery();

        if (rst.next()){
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId+1;

            if (tempId <= 9){
                return "P00-000"+tempId;
            }else if (tempId <= 99){
                return "P00-00"+tempId;
            }else if (tempId <= 999){
                return "P00-0"+tempId;
            }else {
                return "P00-";
            }
        }else {
            return "P00-0001";
        }
    }

    public ArrayList<Supply> searchSupplyDetail(String id, LocalDate startDay, LocalDate endDate) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supply WHERE supplierId='"+id+"' AND supplyDate BETWEEN '"+startDay+"' AND '"+endDate+"'");
        ResultSet rst = pstm.executeQuery();
        ArrayList<Supply> data = new ArrayList<>();
        while (rst.next()){
            data.add(new Supply(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getDouble(7)
            ));
        }
        return data;
    }

    public Double getTotalTransportCharge(String id, LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(transportCharge) FROM Supply WHERE supplierId='"+id+"' AND supplyDate BETWEEN '"+startDate+"' AND '"+endDate+"'").executeQuery();
        double transport = 0;
        while (rst.next()){
            transport=rst.getDouble(1);
        }
        return transport;
    }

    public Double getTotalCostForSupply(String id, LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(totalCost) FROM Supply WHERE supplierId='"+id+"' AND supplyDate BETWEEN '"+startDate+"' AND '"+endDate+"'").executeQuery();
        double cost = 0;
        while (rst.next()){
            cost=rst.getDouble(1);
        }
        return cost;
    }

    public Double getTotalWeightForSupply(String id, LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(weight) FROM Supply WHERE supplierId='"+id+"' AND supplyDate BETWEEN '"+startDate+"' AND '"+endDate+"'").executeQuery();
        double weight = 0;
        while (rst.next()){
            weight=rst.getDouble(1);
        }
        return weight;
    }

    public ArrayList<String> getSupplyIds(String id, LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT supplyId FROM Supply WHERE supplierId='"+id+"' AND supplyDate BETWEEN '"+startDate+"' AND '"+endDate+"'").executeQuery();
        ArrayList<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public boolean savePaymentDetail(Payment payment) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Payment VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pstm.setObject(1,payment.getPaymentId());
        pstm.setObject(2,payment.getSupplierId());
        pstm.setObject(3,payment.getPaymentDate());
        pstm.setObject(4,payment.getPaymentTime());
        pstm.setObject(5,payment.getTeaPacket());
        pstm.setObject(6,payment.getFertilizer());
        pstm.setObject(7,payment.getAdvance());
        pstm.setObject(8,payment.getDiscount());
        pstm.setObject(9,payment.getTotalPayment());
        pstm.setObject(10,payment.getTeaPacketQty());
        pstm.setObject(11,payment.getTeaPacketUnitPrice());
        pstm.setObject(12,payment.getFertilizerQty());
        pstm.setObject(13,payment.getFertilizerUnitPrice());
        pstm.setObject(14,payment.getTransport());
        pstm.setObject(15,payment.getCostForLeaves());

        return  pstm.executeUpdate()>0;
    }

    public boolean updatePaymentDetail(Payment payment) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Payment SET supplierId=?, paymentDate=?, paymentTime=?, teaPacket=?, fertilize=?, advance=?, discount=?, totalPayment=?, packetQty=?, packetPrice=?, fertilizeQty=?, fertilizePrice=?, transport=?, costForLeaves=? WHERE paymentId=?");
        pstm.setObject(1,payment.getSupplierId());
        pstm.setObject(2,payment.getPaymentDate());
        pstm.setObject(3,payment.getPaymentTime());
        pstm.setObject(4,payment.getTeaPacket());
        pstm.setObject(5,payment.getFertilizer());
        pstm.setObject(6,payment.getAdvance());
        pstm.setObject(7,payment.getDiscount());
        pstm.setObject(8,payment.getTotalPayment());
        pstm.setObject(9,payment.getTeaPacketQty());
        pstm.setObject(10,payment.getTeaPacketUnitPrice());
        pstm.setObject(11,payment.getFertilizerQty());
        pstm.setObject(12,payment.getFertilizerUnitPrice());
        pstm.setObject(13,payment.getTransport());
        pstm.setObject(14,payment.getCostForLeaves());
        pstm.setObject(15,payment.getPaymentId());

        return  pstm.executeUpdate()>0;
    }

    public ArrayList<Payment> getPaymentDetail(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment WHERE paymentId='"+id+"'");
        ResultSet rst = pstm.executeQuery();
        ArrayList<Payment> data = new ArrayList<>();
        while (rst.next()){
            data.add(new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getDouble(7),
                    rst.getDouble(8),
                    rst.getDouble(9),
                    rst.getInt(10),
                    rst.getDouble(11),
                    rst.getDouble(12),
                    rst.getDouble(13),
                    rst.getDouble(14),
                    rst.getDouble(15)
            ));
        }
        return data;
    }

    public boolean deletePayment(String  id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Payment WHERE paymentId='"+id+"'").executeUpdate()>0) {
            return true;
        }
        return false;
    }

    public List<String > PaymentIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT paymentId FROM Payment ").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public String  getSupplierId(String  id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT supplierId FROM Payment WHERE paymentId='"+id+"'").executeQuery();
        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    public String  getSupplierName(String  id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT name FROM Supplier WHERE supplierId='"+id+"'").executeQuery();
        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    public ArrayList<Supply> takeSupplyDetail(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supply WHERE supplierId='"+id+"'");
        ResultSet rst = pstm.executeQuery();
        ArrayList<Supply> data = new ArrayList<>();
        while (rst.next()){
            data.add(new Supply(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getDouble(7)
            ));
        }
        return data;
    }

}
