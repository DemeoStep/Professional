import DAO.*;
import Entity.Car;
import Entity.Client;
import Entity.Mark;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static SessionFactory factory;
    private static CarRepository carDAO;
    private static MarkRepository markDAO;
    private static ClientRepository clientDAO;

    Main() {
        factory = new Configuration().configure().buildSessionFactory();
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

        for (Mark mark: markList) {
            markDAO.add(mark);
        }

        markList = markDAO.getAll();
        printAll(markList);

        carDAO.add(new Car("Lada", "Kalina", 7000));
        carDAO.add(new Car("Porsche", "911", 50000));
        carDAO.add(new Car("Toyota", "Camry", 40000));

        List<Car> carList = carDAO.getAll();
        printAll(carList);

        carDAO.getById(1).ifPresent(System.out::println);

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

