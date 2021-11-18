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

        for (Mark mark : markList) {
            markDAO.add(mark);
        }

        carDAO.add(new Car("Lada", "Kalina", 7000));
        carDAO.add(new Car("Porsche", "911", 50000));
        carDAO.add(new Car("Toyota", "Camry", 40000));

        printAll(carDAO.getAll());

        carDAO.getById(1).ifPresent(System.out::println);

        carDAO.updateCar(1, new Car("BMW", "X5", 28000));

        printAll(carDAO.getAll());

        carDAO.removeById(3);

        carDAO.add(new Car("Lada", "Kalina", 7000));
        carDAO.add(new Car("Lada", "Priora", 9000));
        carDAO.add(new Car("Lada", "Vesta", 10000));

        printAll(carDAO.getAll());

        carDAO.removeByMark("Lada");

        printAll(carDAO.getAll());

        carDAO.removeByModel("911");

        printAll(carDAO.getAll());

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

