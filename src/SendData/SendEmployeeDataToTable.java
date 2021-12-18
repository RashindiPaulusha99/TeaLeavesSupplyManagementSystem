package SendData;

import java.sql.SQLException;

public interface SendEmployeeDataToTable {
    public void loadData() throws SQLException, ClassNotFoundException;
}
