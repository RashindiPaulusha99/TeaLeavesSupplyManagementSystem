package SendData;

import java.sql.SQLException;

public interface SendPaymentDataToTable {
    public void loadData() throws SQLException, ClassNotFoundException;
}
