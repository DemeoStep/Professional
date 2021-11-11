import java.sql.*;

public class Main {
    public static void main(String[] args) {

        var connection = DBWorker.open("MyJoinsDB");

        try {
            if (connection != null) {
                task2_4.start(connection);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBWorker.close(connection);
        }

        connection = DBWorker.open("AddTaskDB");

        try {
            if (connection != null) {
                add_task.start(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBWorker.close(connection);
        }

    }

    static void printLine() {
        System.out.println("----------------------------------------------------------------------------");
    }
}
