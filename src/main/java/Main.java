import java.sql.*;

public class Main {
    private static final String URL = "jdbc:mysql://192.168.12.2:3306/MyJoinsDB";
    private static final String LOGIN = "root";
    private static final String PASS = "myensql";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASS);
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * from persons");

            while(result.next()) {
                System.out.println(result.getString("name"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
