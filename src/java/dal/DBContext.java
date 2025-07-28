package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String user = "sa";
        String pass = "123456";
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=TrainTicketv2";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, user, pass);
    }
}
