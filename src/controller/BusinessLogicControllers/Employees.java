package controller.BusinessLogicControllers;

import model.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Employees {

    public boolean saveEmployee(Employee employee) throws SQLException, ClassNotFoundException;
    public boolean updateEmployee(Employee employee) throws SQLException, ClassNotFoundException;
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Employee> searchEmployee(String value) throws SQLException, ClassNotFoundException;
    public ArrayList<Employee> viewEmployee() throws SQLException, ClassNotFoundException;
}
