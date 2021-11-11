import java.sql.*;

public class task2_4 {

    static void start(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery("SELECT name, phone, address FROM persons\n" +
                "JOIN person_info pi ON persons.id = pi.person;");

        Main.printLine();
        System.out.println("1) Получите контактные данные сотрудников (номера телефонов, место жительства).");
        Main.printLine();

        while (result.next()) {
            System.out.println(result.getString("name") + " \t| " + result.getString("phone") + " \t| " + result.getString("address"));
        }

        Main.printLine();
        System.out.println("2) Получите информацию о дате рождения всех холостых сотрудников и их номера.");
        Main.printLine();

        result = statement.executeQuery("SELECT name, phone, birthday, status FROM persons\n" +
                "JOIN person_info pi ON persons.id = pi.person\n" +
                "WHERE status = 'Холост';");

        while (result.next()) {
            System.out.println(result.getString("name") + " \t| " + result.getString("phone") + " \t| " + result.getDate("birthday") + " \t| " + result.getString("status"));
        }

        Main.printLine();
        System.out.println("3) Получите информацию обо всех менеджерах компании: дату рождения и номер телефона.");
        Main.printLine();

        result = statement.executeQuery("SELECT name, phone, birthday, position FROM persons\n" +
                "JOIN person_info pi ON persons.id = pi.person\n" +
                "JOIN positions p ON persons.id = p.person\n" +
                "WHERE position = 'Менеджер';");

        while (result.next()) {
            System.out.println(result.getString("name") + " \t| " + result.getString("phone") + " \t| " + result.getDate("birthday") + " \t| " + result.getString("position"));
        }
    }

}
