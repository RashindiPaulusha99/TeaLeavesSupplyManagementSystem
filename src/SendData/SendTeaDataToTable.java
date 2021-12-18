package SendData;

import java.sql.SQLException;

public interface SendTeaDataToTable {
    public void loadData() throws SQLException, ClassNotFoundException;
}
