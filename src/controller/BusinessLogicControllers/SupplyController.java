package controller.BusinessLogicControllers;

import db.DbConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplyController {

    public String getSupplyIds() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT supplyId  FROM Supply ORDER BY supplyId DESC LIMIT 1");
        ResultSet rst = pstm.executeQuery();

        if (rst.next()){
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId+1;

            if (tempId <= 9){
                return "Sp00-000"+tempId;
            }else if (tempId <= 99){
                return "Sp00-00"+tempId;
            }else if (tempId <= 999){
                return "Sp00-0"+tempId;
            }else {
                return "Sp00-";
            }
        }else {
            return "Sp00-0001";
        }
    }

    public String getTicketIds() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT tId  FROM Ticket ORDER BY tId DESC LIMIT 1");
        ResultSet rst = pstm.executeQuery();

        if (rst.next()){
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId+1;

            if (tempId <= 9){
                return "R00-000"+tempId;
            }else if (tempId <= 99){
                return "R00-00"+tempId;
            }else if (tempId <= 999){
                return "R00-0"+tempId;
            }else {
                return "R00-";
            }
        }else {
            return "R00-0001";
        }
    }

    public List<String> searchEIds() throws SQLException, ClassNotFoundException {
        String driver = "Driver";
        String helper = "Helper";
        String desig = "Data Entry Operator";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT eId  FROM Employee WHERE designation='"+driver+"' OR designation='"+helper+"' OR designation='"+desig+"'");
        ResultSet rst = pstm.executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public List<String> searchEmployeeIds() throws SQLException, ClassNotFoundException {
        String helper = "Helper";
        String desig = "Data Entry Operator";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT eId  FROM Employee WHERE designation='"+helper+"' OR designation='"+desig+"'");
        ResultSet rst = pstm.executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public String returnDesignation(String  id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT designation FROM Employee WHERE eId='" + id + "'").executeQuery();
        String designation = null;
        if (rst.next()){
            designation =  rst.getString(1);
        }
        return designation;
    }

    public Employee searchEData(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee WHERE eId='" + id + "'").executeQuery();
        while (rst.next()){
            return new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            );
        }
        return null;
    }

    public List<String> searchSIds() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT supplierId  FROM Supplier");
        ResultSet rst = pstm.executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public Supplier searchSDate(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier WHERE supplierId='"+id+"'").executeQuery();
        while (rst.next()){
            return new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getInt(7)
            );
        }
        return null;
    }

    public List<String> searchTCodes() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT code  FROM Tea");
        ResultSet rst = pstm.executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public Tea searchTDate(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Tea WHERE code='"+id+"'").executeQuery();
        while (rst.next()){
            return new Tea(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBigDecimal(4),
                    rst.getInt(5)
            );
        }
        return null;
    }

    public ArrayList<String> SupplyIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT supplyId FROM Supply ").executeQuery();
        ArrayList<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public ArrayList<Supply> findSupplyDetail(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supply WHERE supplyId='"+id+"'");
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

    public ArrayList<SupplyDetail> getSupplyDetail(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT code,wholeWeight,sackqty,sackWeight,coarse,wet,boiled,reject,netWeight,cost FROM SupplyDetail WHERE supplyId='"+id+"'");
        ResultSet rst = pstm.executeQuery();
        ArrayList<SupplyDetail > data = new ArrayList<>();
        while (rst.next()){
            data.add(new SupplyDetail(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getDouble(7),
                    rst.getDouble(8),
                    rst.getDouble(9),
                    rst.getDouble(10)
            ));
        }
        return data;
    }

    public boolean acceptSupply(Supply supply,Service service)  {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstm = con.prepareStatement("INSERT INTO Supply VALUES (?,?,?,?,?,?,?)");
            pstm.setObject(1,supply.getSupplyId());
            pstm.setObject(2,supply.getSupplierId());
            pstm.setObject(3,supply.getSupplyDate());
            pstm.setObject(4,supply.getSupplyTime());
            pstm.setObject(5,supply.getWeight());
            pstm.setObject(6,supply.getTotalCost());
            pstm.setObject(7,supply.getTransportCharge());

            if(pstm.executeUpdate()>0){
                if (saveSupplyDetail(supply.getSupplyId(),supply.getTea())){
                    if (setDataToServiceTable(service)){
                        con.commit();
                        return true;
                    }else {
                        con.rollback();
                        return false;
                    }

                }else {
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;

    }

    private boolean saveSupplyDetail(String supplyId, ArrayList<SupplyDetail> tea) throws SQLException, ClassNotFoundException {

        for (SupplyDetail temp : tea) {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO SupplyDetail VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            pstm.setObject(1,supplyId);
            pstm.setObject(2,temp.getTeaCode());
            pstm.setObject(3,temp.getWholeWeight());
            pstm.setObject(4,temp.getSackQTY());
            pstm.setObject(5,temp.getSackWeight());
            pstm.setObject(6,temp.getCoarse());
            pstm.setObject(7,temp.getWet());
            pstm.setObject(8, temp.getNetWeight());
            pstm.setObject(9, temp.getCost());
            pstm.setObject(10,temp.getBoiled());
            pstm.setObject(11,temp.getReject());

            if (pstm.executeUpdate()>0){

                if (updateTeaStock(temp.getTeaCode(),temp.getNetWeight())){

                }else {
                    return false;
                }

            }else {
                return false;
            }
        }

        return true;
    }

    public boolean updateTeaStock(String teaCode, double netWeight) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Tea SET qty=(qty+'" + netWeight + "') WHERE code='" + teaCode + "'");
        return pstm.executeUpdate()>0;
    }

    public boolean setDataToServiceTable(Service s) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Service VALUES(?,?,?,?,?)");
        pstm.setObject(1,s.getEmployeeId());
        pstm.setObject(2,s.getSupplyId());
        pstm.setObject(3,s.getDescription());
        pstm.setObject(4,s.getDesignation());
        pstm.setObject(5,s.getDate());

        return pstm.executeUpdate()>0;
    }

    public boolean UpdateSupply(Supply supply) throws SQLException, ClassNotFoundException {

        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstm = con.prepareStatement("UPDATE Supply SET supplierId=?, supplyDate=?, supplyTime=?, weight=?,  totalCost=?, transportCharge=? WHERE supplyId=?");
            pstm.setObject(1,supply.getSupplierId());
            pstm.setObject(2,supply.getSupplyDate());
            pstm.setObject(3,supply.getSupplyTime());
            pstm.setObject(4,supply.getWeight());
            pstm.setObject(5,supply.getTotalCost());
            pstm.setObject(6,supply.getTransportCharge());
            pstm.setObject(7,supply.getSupplyId());

            if(pstm.executeUpdate()>0){
                if (updateSupplyDetail(supply.getSupplyId(),supply.getTea())){
                    con.commit();
                    return true;

                }else {
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;

    }

    public boolean updateSupplyDetail(String supplyId, ArrayList<SupplyDetail> tea) throws SQLException, ClassNotFoundException {

        for (SupplyDetail temp : tea) {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE SupplyDetail SET wholeWeight=?, sackqty=?, sackWeight=?,  coarse=?, wet=?, netWeight=?, cost=?, boiled=?, reject=? WHERE supplyId=? AND code=?");
            pstm.setObject(1,temp.getWholeWeight());
            pstm.setObject(2,temp.getSackQTY());
            pstm.setObject(3,temp.getSackWeight());
            pstm.setObject(4,temp.getCoarse());
            pstm.setObject(5,temp.getWet());
            pstm.setObject(6,temp.getNetWeight());
            pstm.setObject(7,temp.getCost());
            pstm.setObject(8,temp.getBoiled());
            pstm.setObject(9,temp.getReject());
            pstm.setObject(10,supplyId);
            pstm.setObject(11,temp.getTeaCode());

            if (pstm.executeUpdate()>0){
                if (updateTeaStock(temp.getTeaCode(),temp.getNetWeight())){

                }else {
                    return false;
                }

            }else {
                return false;
            }
        }

        return true;
    }

    public boolean deleteSupply(String value, ArrayList<SupplyDetail> supplyDetail) throws SQLException, ClassNotFoundException {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstm = con.prepareStatement("DELETE FROM Supply WHERE supplyId='"+value+"'");

            if(pstm.executeUpdate()>0){
                if (updateTeaStockAgain(supplyDetail.get(0).getTeaCode(),supplyDetail.get(0).getNetWeight())){
                    con.commit();
                    return true;
                }else {
                    con.rollback();
                    return false;
                }

            }else {
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public boolean updateTeaStockAgain(String teaCode, double netWeight) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("UPDATE Tea SET qty=(qty-'" + netWeight + "') WHERE code='" + teaCode + "'").executeUpdate()>0) {
            return true;
        }
        return false;
    }

    public boolean setTicketData(Ticket ticket, Issue issue){
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstm = con.prepareStatement("INSERT INTO Ticket VALUES (?,?,?,?,?,?)");
            pstm.setObject(1,ticket.getTicketId());
            pstm.setObject(2,ticket.getSupplierId());
            pstm.setObject(3,ticket.getTicketDate());
            pstm.setObject(4,ticket.getTicketTime());
            pstm.setObject(5,ticket.getWholeWeight());
            pstm.setObject(6,ticket.getNetWeight());

            if(pstm.executeUpdate()>0){
                if (setIssueData(ticket.getTicketId(),issue)){
                    con.commit();
                    return true;

                }else {
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public boolean setIssueData(String id,Issue i) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Issue VALUES(?,?,?,?,?)");
        preparedStatement.setObject(1,i.getEmployeeId());
        preparedStatement.setObject(2,id);
        preparedStatement.setObject(3,i.getDescription());
        preparedStatement.setObject(4,i.getDesignation());
        preparedStatement.setObject(5,i.getDate());

        return preparedStatement.executeUpdate()>0;
    }

}