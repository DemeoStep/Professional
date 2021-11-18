import DAO.*;
import Entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static CarRepository carDAO;
    private static MarkRepository markDAO;
    private static ClientRepository clientDAO;

    Main() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        markDAO = new MarkDAO(factory);
        carDAO = new CarDAO(factory, markDAO);
        clientDAO = new ClientDAO(factory);
    }

    public static void main(String[] args) {
        new Main().logic();
    }

    void logic() {
        List<Mark> markList = new ArrayList<>();

        markList.add(new Mark("Audi"));
        markList.add(new Mark("Porsche"));
        markList.add(new Mark("BMW"));
        markList.add(new Mark("Lada"));

        for (Mark mark : markList) {
            markDAO.add(mark);
        }

        printLine();
        System.out.println("Mark.add / Mark.getAll");

        printAll(markDAO.getAll());

        carDAO.add(new Car("Lada", "Kalina", 7000));
        carDAO.add(new Car("Porsche", "911", 50000));
        carDAO.add(new Car("Toyota", "Camry", 40000));

        printLine();
        System.out.println("Car.add / Car.getAll");

        printAll(carDAO.getAll());

        printLine();
        System.out.println("Car.getById (1)");
        carDAO.getById(1).ifPresent(System.out::println);

        carDAO.updateCar(1, new Car("Ferrari", "Roma", 280000));

        printAll(carDAO.getAll());

        printLine();
        System.out.println("Car.removeById (3)");
        carDAO.removeById(3);

        carDAO.add(new Car("Lada", "Kalina", 7000));
        carDAO.add(new Car("Lada", "Priora", 9000));
        carDAO.add(new Car("Lada", "Vesta", 10000));

        printAll(carDAO.getAll());

        carDAO.removeByMark("Lada");

        printLine();
        System.out.println("Car.removeByMark (\"Lada\")");
        printAll(carDAO.getAll());

        carDAO.removeByModel("911");

        printLine();
        System.out.println("Car.removeByModel (\"911\")");
        printAll(carDAO.getAll());

        printLine();
        System.out.println("Client.add / Client.getAll");

        clientDAO.addClient(new Client("Alex", 23, "050-555-55-55"));
        clientDAO.addClient(new Client("Max", 25, "050-222-22-22"));

        printAll(clientDAO.getAllClients());

        printLine();
        System.out.println("Client.getClient");
        printLine();
        clientDAO.getClient(1).ifPresent(System.out::println);
        printLine();

        printLine();
        System.out.println("Client.deleteClient");
        clientDAO.deleteClient(1);

        printAll(clientDAO.getAllClients());

        printLine();
        System.out.println("Client.updateClient");
        clientDAO.updateClient(2, new Client("MaXXX", 40, "050-333-33-33"));

        printAll(clientDAO.getAllClients());
    }

    private static <E> void printAll(List<E> list) {
        printLine();
        for (E item : list) {
            System.out.println(item);
        }
        printLine();
    }

    private static void printLine() {
        System.out.println("--------------------------------------------------------");
    }
}

