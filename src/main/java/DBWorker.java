import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBWorker {
    private static final String URL = "jdbc:mysql://192.168.12.2:3306/";
    private static final String LOGIN = "root";
    private static final String PASS = "myensql";

    static Connection open(String schema) {
        if(schema == null) {
            return null;
        }

        try {
            return DriverManager.getConnection(URL + schema, LOGIN, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
