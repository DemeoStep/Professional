import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class add_task {

    static void start(Connection connection) throws SQLException {
        System.out.println();
        Main.printLine();
        System.out.println("Дополнительное задание");
        Main.printLine();

        Statement statement = connection.createStatement();

        statement.execute("TRUNCATE TABLE persons;");

        statement.execute(
                "INSERT INTO persons (name, phone) VALUES " +
                "('Vasya', '099-999-99-99')," +
                "('Petya','088-888-88-88')," +
                "('Tanya', '077-777-77-77')," +
                "('Borya','066-666-66-66')"
        );

        selectALL(statement);

        Main.printLine();
        int ok = statement.executeUpdate("UPDATE persons SET phone = '011-111-11-11' WHERE id = 3;");
        System.out.println("(UPDATE persons SET phone = '011-111-11-11' WHERE id = 3)" + " - " + (ok > 0 ? "ok" : "error!"));

        ok = statement.executeUpdate("DELETE FROM persons WHERE id = 2;");
        System.out.println("(DELETE FROM persons WHERE id = 2)" + " - " + (ok > 0 ? "ok" : "error!"));

        Main.printLine();
        System.out.println("(SELECT * FROM persons WHERE id = 3)");

        ResultSet result = statement.executeQuery("SELECT * FROM persons WHERE id = 3;");

        while (result.next()) {
            System.out.println(result.getInt("id") + " \t| " + result.getString("name") + " \t| " + result.getString("phone"));
        }

        Main.printLine();
        selectALL(statement);
    }

    private static void selectALL(Statement statement) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT * FROM persons;");
        while (result.next()) {
            System.out.println(result.getInt("id") + " \t| " + result.getString("name") + " \t| " + result.getString("phone"));
        }
    }

}
