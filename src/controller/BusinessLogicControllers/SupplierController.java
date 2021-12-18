package controller.BusinessLogicControllers;

import db.DbConnection;
import model.Supplier;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierController implements Suppliers{

    @Override
    public boolean saveSupplier(Supplier supplier, User user) {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstm = con.prepareStatement("INSERT INTO Supplier VALUES (?,?,?,?,?,?,?)");
            pstm.setObject(1,supplier.getSupplierId());
            pstm.setObject(2,supplier.getSupplierName());
            pstm.setObject(3,supplier.getSupplierAddress());
            pstm.setObject(4,supplier.getSupplierContact());
            pstm.setObject(5,supplier.getSupplierNIC());
            pstm.setObject(6,supplier.getSupplierGender());
            pstm.setObject(7,supplier.getAreaOfTheLand());

            if(pstm.executeUpdate()>0) {
                if (saveUser(user)) {
                    con.commit();
                    return true;
                } else {
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

    public boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO User VALUES(?,?,?)");
        pstm.setObject(1,user.getSupplierId());
        pstm.setObject(2,user.geteId());
        pstm.setObject(3,user.getName());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean updateSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Supplier SET name=?, address=?, contact=?, nic=?, gender=?, areaOfTheLand=? WHERE supplierId = ?");
        pstm.setObject(1,supplier.getSupplierName());
        pstm.setObject(2,supplier.getSupplierAddress());
        pstm.setObject(3,supplier.getSupplierContact());
        pstm.setObject(4,supplier.getSupplierNIC());
        pstm.setObject(5,supplier.getSupplierGender());
        pstm.setObject(6,supplier.getAreaOfTheLand());
        pstm.setObject(7,supplier.getSupplierId());

        return  pstm.executeUpdate()>0;
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Supplier WHERE supplierId='"+id+"'").executeUpdate()>0) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Supplier> searchSupplier(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier WHERE CONCAT(name,supplierId) LIKE '%"+value+"%'");
        ResultSet rst = pstm.executeQuery();
        ArrayList<Supplier> searchSupplier = new ArrayList<>();
        while (rst.next()){
            searchSupplier.add(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getInt(7)
            ));
        }
        return searchSupplier;
    }

    @Override
    public ArrayList<Supplier> viewSupplier() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier");
        ResultSet rst = pstm.executeQuery();
        ArrayList<Supplier> suppliers = new ArrayList<>();
        while (rst.next()){
            suppliers.add(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getInt(7)
            ));
        }
        return suppliers;
    }

    public boolean  searchId(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier WHERE supplierId='" + id + "'");
        ResultSet rst = pstm.executeQuery();

        return rst.next();
    }

    public boolean  searchNIC(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier WHERE nic='" + id + "'");
        ResultSet rst = pstm.executeQuery();

        return rst.next();
    }

    public List<String> searchIds() throws SQLException, ClassNotFoundException {
        String desig = "Data Entry Operator";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT eId  FROM Employee WHERE designation='"+desig+"'");
        ResultSet rst = pstm.executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public String getName(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT name FROM Employee WHERE eId='" + id + "'").executeQuery();
        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    public String getSupplierId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT supplierId FROM Supplier ORDER BY supplierId DESC LIMIT 1").executeQuery();
        if (rst.next()){
            //if data has in database ,split supplierId
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId+1;

            if (tempId <= 9){
                return "S00-000"+tempId;
            }else if (tempId <= 99) {
                return "S00-00" + tempId;
            }else if (tempId <= 999){
                return "S00-0" + tempId;
            }else {
                return "S00-"+tempId;
            }
        }else {
            //if no data in database
            return "S00-0001";
        }
    }

    public int calculateSuppliers() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(supplierId) FROM Supplier").executeQuery();
        while (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}
