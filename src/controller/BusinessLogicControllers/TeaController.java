package controller.BusinessLogicControllers;

import db.DbConnection;
import model.Tea;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeaController implements Teas {
    @Override
    public boolean saveTea(Tea tea) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Tea VALUES (?,?,?,?,?)");
        pstm.setObject(1,tea.getCode());
        pstm.setObject(2,tea.getType());
        pstm.setObject(3,tea.getName());
        pstm.setObject(4,tea.getUnitPrice());
        pstm.setObject(5,tea.getQty());

        return  pstm.executeUpdate()>0;
    }

    @Override
    public boolean updateTea(Tea tea) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Tea SET type=?, name=?, unitPrice=? WHERE code = ?");
        pstm.setObject(1,tea.getType());
        pstm.setObject(2,tea.getName());
        pstm.setObject(3,tea.getUnitPrice());
        pstm.setObject(4,tea.getCode());

        return  pstm.executeUpdate()>0;
    }

    @Override
    public boolean deleteTea(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Tea WHERE code='"+id+"'").executeUpdate()>0) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Tea> searchTea(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Tea WHERE CONCAT(code,name) LIKE '%"+value+"%'");
        ResultSet rst = pstm.executeQuery();
        ArrayList<Tea> searchTea = new ArrayList<>();
        while (rst.next()){
            searchTea.add(new Tea(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBigDecimal(4),
                    rst.getInt(5)
            ));
        }
        return searchTea;
    }

    @Override
    public ArrayList<Tea> viewTea() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Tea");
        ResultSet rst = pstm.executeQuery();
        ArrayList<Tea> tea = new ArrayList<>();
        while (rst.next()){
            tea.add(new Tea(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBigDecimal(4),
                    rst.getInt(5)
            ));
        }
        return tea;
    }

    public boolean  searchId(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Tea WHERE code='" + id + "'");
        ResultSet rst = pstm.executeQuery();

        return rst.next();
    }

    public String getTeaId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT code FROM Tea ORDER BY code DESC LIMIT 1").executeQuery();
        if (rst.next()){
            //if data has in database ,split teaId
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId+1;

            if (tempId <= 9){
                return "T00-000"+tempId;
            }else if (tempId <= 99) {
                return "T00-00" + tempId;
            }else if (tempId <= 999){
                return "T00-0" + tempId;
            }else {
                return "T00-"+tempId;
            }
        }else {
            //if no data in database
            return "T00-0001";
        }
    }

    public String calculateTeaStock() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(qty) FROM Tea").executeQuery();
        double weight = 0;
        while (rst.next()){
            weight = rst.getDouble(1);
            return rst.getString(1);
        }
        return null;
    }

    public ArrayList<Tea> calculateMaxTeaStock() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Tea ORDER BY qty DESC LIMIT 1").executeQuery();
        ArrayList<Tea> teas = new ArrayList<>();
        while (rst.next()){
            teas.add(new Tea(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBigDecimal(4),
                    rst.getInt(5)
            ));
        }
        return teas;
    }

    public ArrayList<Tea> calculateMinTeaStock() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Tea ORDER BY qty ASC LIMIT 1 ").executeQuery();
        ArrayList<Tea> teas = new ArrayList<>();
        while (rst.next()){
            teas.add(new Tea(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getBigDecimal(4),
                    rst.getInt(5)
            ));
        }
        return teas;
    }
}
