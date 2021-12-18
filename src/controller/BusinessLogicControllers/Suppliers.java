package controller.BusinessLogicControllers;

import model.Supplier;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Suppliers {

    public boolean saveSupplier(Supplier supplier, User user) throws SQLException, ClassNotFoundException;
    public boolean updateSupplier(Supplier supplier) throws SQLException, ClassNotFoundException;
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Supplier> searchSupplier(String value) throws SQLException, ClassNotFoundException;
    public ArrayList<Supplier> viewSupplier() throws SQLException, ClassNotFoundException;
}
