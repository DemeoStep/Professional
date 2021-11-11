import java.sql.*;

public class Main {
    private static final String URL = "jdbc:mysql://192.168.12.2:3306/MyJoinsDB";
    private static final String LOGIN = "root";
    private static final String PASS = "myensql";

    public static void main(String[] args) {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASS);

            task2_4.start(connection);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    static void printLine() {
        System.out.println("----------------------------------------------------------------------------");
    }
}
