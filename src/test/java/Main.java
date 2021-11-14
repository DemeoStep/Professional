import DAO.*;
import Entity.Car;
import Entity.Client;

import java.util.List;

public class Main {
    private final static IDAOFactory factory = DAOFactory.getInstance();
    private final static ICarDAO carDAO = factory.getCarDAO();
    private final static IClientDAO clientDAO = factory.getClientDAO();

    public static void main(String[] args) {

        printAll(carDAO.getAll());

        Car car1 = new Car();

        if (carDAO.getById(1).isPresent()) {
            car1 = carDAO.getById(1).get();
            System.out.println(car1);
        }

        car1.setPrice(40000);

        carDAO.updateCar(car1);

        if (carDAO.getById(car1.getId()).isPresent()) {
            System.out.println(carDAO.getById(car1.getId()).get());
        }

        Car car2 = new Car("Lada", "Калина", 9000);

        carDAO.add(car2);

        printAll(carDAO.getAll());

        if(carDAO.getCarId(car2).isPresent()) {
            carDAO.removeById(carDAO.getCarId(car2).get());
        }

        printAll(carDAO.getAll());

        carDAO.add(new Car("BMW", "X5", 20000));
        carDAO.add(new Car("BMW", "i4", 30000));
        printAll(carDAO.getAll());

        carDAO.removeByModel("X5");
        printAll(carDAO.getAll());

        carDAO.removeByMark("BMW");
        printAll(carDAO.getAll());

        printAll(clientDAO.getAllClients());

        clientDAO.addClient(new Client("Алексей", 40, "066-771-82-71"));

        printAll(clientDAO.getAllClients());

        if (clientDAO.getClient(4).isPresent()) {
            System.out.println(clientDAO.getClient(4).get());
        }

        System.out.println(clientDAO.updateClient(1, new Client("Петр", 20, "099-555-55-22")));

        printAll(clientDAO.getAllClients());

        System.out.println(clientDAO.deleteClient(4));

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
