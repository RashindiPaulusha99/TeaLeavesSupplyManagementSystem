package controller.BusinessLogicControllers;

import db.DbConnection;
import model.Register;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    public boolean registerData(Register data) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Register VALUES (?,?,?,?,?)");
        pstm.setObject(1,data.getId());
        pstm.setObject(2,data.getPassword());
        pstm.setObject(3,data.getUserName());
        pstm.setObject(4,data.getName());
        pstm.setObject(5,data.getRole());

        return pstm.executeUpdate()>0;
    }

    public Register checkCorrectLoginData(String UserName, String Password) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Register WHERE userName='"+UserName+"' AND password='"+Password+"'").executeQuery();
        if (rst.next()){
            return new Register(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public boolean checkCorrectDataOfLoginForm(String UserName, String Password) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Register WHERE userName='"+UserName+"' AND password='"+Password+"'").executeQuery();
        if (rst.next()){
            return true;
        }
        return false;
    }

    public boolean checkUsername(String UserName) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Register WHERE userName='"+UserName+"'").executeQuery();
        if (rst.next()){
            return true;
        }
        return false;
    }

    public boolean insertNewPassword(String Password,String Username) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Register SET password='" + Password + "' WHERE userName='"+Username+"'");
        return pstm.executeUpdate()>0;
    }

    public boolean  searchId(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Register WHERE id='" + id + "'");
        ResultSet rst = pstm.executeQuery();

        return rst.next();
    }
}
