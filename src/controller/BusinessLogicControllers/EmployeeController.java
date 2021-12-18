package controller.BusinessLogicControllers;

import db.DbConnection;
import model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeController implements  Employees{

    public boolean saveEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Employee VALUES (?,?,?,?,?,?,?,?)");
        pstm.setObject(1,employee.getEmployeeId());
        pstm.setObject(2,employee.getEmployeeName());
        pstm.setObject(3,employee.getEmployeeDOB());
        pstm.setObject(4,employee.getEmployeeAddress());
        pstm.setObject(5,employee.getEmployeeContact());
        pstm.setObject(6,employee.getEmployeeNIC());
        pstm.setObject(7,employee.getEmployeeGender());
        pstm.setObject(8,employee.getDesignation());

        return  pstm.executeUpdate()>0;
    }

    public boolean updateEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Employee SET name=?, DOB=?, address=?, contact=?, nic=?, gender=?, designation=? WHERE eId = ?");
        pstm.setObject(1,employee.getEmployeeName());
        pstm.setObject(2,employee.getEmployeeDOB());
        pstm.setObject(3,employee.getEmployeeAddress());
        pstm.setObject(4,employee.getEmployeeContact());
        pstm.setObject(5,employee.getEmployeeNIC());
        pstm.setObject(6,employee.getEmployeeGender());
        pstm.setObject(7,employee.getDesignation());
        pstm.setObject(8,employee.getEmployeeId());

        return  pstm.executeUpdate()>0;
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Employee WHERE eId='"+id+"'").executeUpdate()>0) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Employee> searchEmployee(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee WHERE CONCAT(name,eId) LIKE '%"+value+"%'");
        ResultSet rst = pstm.executeQuery();
        ArrayList<Employee> searchEmployees = new ArrayList<>();
        while (rst.next()){
            searchEmployees.add(new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            ));
        }
        return searchEmployees;
    }

    public ArrayList<Employee> viewEmployee() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee");
        ResultSet rst = pstm.executeQuery();
        ArrayList<Employee> employees = new ArrayList<>();
        while (rst.next()){
            employees.add(new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            ));
        }
        return employees;
    }

    public boolean  searchId(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee WHERE eId='" + id + "'");
        ResultSet rst = pstm.executeQuery();

        return rst.next();
    }

    public boolean  searchNIC(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee WHERE nic='" + id + "'");
        ResultSet rst = pstm.executeQuery();

        return rst.next();
    }

    public String getEmployeeId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT eId FROM Employee ORDER BY eId DESC LIMIT 1").executeQuery();
        if (rst.next()){
            //if data has in database ,split employeeId
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId+1;

            if (tempId <= 9){
                return "E00-000"+tempId;
            }else if (tempId <= 99) {
                return "E00-00" + tempId;
            }else if (tempId <= 999){
                return "E00-0" + tempId;
            }else {
                return "E00-"+tempId;
            }
        }else {
            //if no data in database
            return "E00-0001";
        }
    }

    public int calculateEmployees() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(eId) FROM Employee").executeQuery();
        while (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}
