package controller.BusinessLogicControllers;

import db.DbConnection;
import model.Payment;
import model.Supply;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReportsController {

    public ArrayList<Supply> loadSupplyDataForReports(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supply WHERE supplyDate ='"+date+"'");
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

    public int countSupply(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(supplyId) FROM Supply WHERE supplyDate='"+date+"'");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            return rst.getInt(1);
        }
        return 0;
    }

    public int countPayment(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(paymentId) FROM Payment WHERE paymentDate='"+date+"'");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            return rst.getInt(1);
        }
        return 0;
    }

    public int countMoAnPayment(LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(paymentId) FROM Payment WHERE paymentDate BETWEEN '"+startDate+"' AND '"+endDate+"'");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            return rst.getInt(1);
        }
        return 0;
    }

    public double findWeight(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(weight) FROM Supply WHERE supplyDate='"+date+"'");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            return rst.getDouble(1);
        }
        return 0;
    }

    public ArrayList<Supply> loadSupplyDataForMoAnReports(LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supply WHERE supplyDate BETWEEN '"+startDate+"' AND '"+endDate+"'");
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

    public int countSupplyForMoAn(LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(supplyId) FROM Supply WHERE supplyDate BETWEEN '"+startDate+"' AND '"+endDate+"'");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            return rst.getInt(1);
        }
        return 0;
    }

    public double findWeightForMoAn(LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(weight) FROM Supply WHERE supplyDate BETWEEN '"+startDate+"' AND '"+endDate+"'");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            return rst.getDouble(1);
        }
        return 0;
    }

    public ArrayList<Payment> getPaymentDetails(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment WHERE paymentDate='"+date+"'");
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
    public ArrayList<Payment> getMoAnPaymentDetails(LocalDate startDate, LocalDate endDate) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Payment WHERE paymentDate BETWEEN '"+startDate+"' AND '"+endDate+"'");
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
}
