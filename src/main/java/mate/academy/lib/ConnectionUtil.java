package mate.academy.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties DB_PROPERTIES = new Properties();
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_jdbc_intro?serverTimezone=UTC";

    static {
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "111111");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
