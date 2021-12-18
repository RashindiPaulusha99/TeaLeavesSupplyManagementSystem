package controller.BusinessLogicControllers;

import model.Tea;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Teas {

    public boolean saveTea(Tea tea) throws SQLException, ClassNotFoundException;
    public boolean updateTea(Tea tea) throws SQLException, ClassNotFoundException;
    public boolean deleteTea(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Tea> searchTea(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Tea> viewTea() throws SQLException, ClassNotFoundException;
}
