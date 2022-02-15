package View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection createConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/lojaesteves?useSSL=false";
        String user = "root";
        String pass = "1234";

        Connection con = DriverManager.getConnection(url, user, pass);
        return con;
    }
}
