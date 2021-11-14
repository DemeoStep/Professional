package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class Connector {
    private static final String URL = "jdbc:mysql://192.168.12.2:3306/carsshop";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "myensql";

    private static Optional<Connection> connect() {
        try {
            return Optional.of(DriverManager.getConnection(URL, LOGIN, PASSWORD));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static Connection getConnection() {
        Optional<Connection> optional = connect();

        if (optional.isEmpty()) {
            throw new RuntimeException("DB connection error");
        }

        return optional.get();
    }

    public static void closeConnections(Connection connection, Statement statement) {
        try {
            if(statement != null) {
                statement.close();
            }

            if(!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
