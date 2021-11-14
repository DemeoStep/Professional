import DAO.*;
import Entity.Car;

public class Main {
    private final static IDAOFactory factory = DAOFactory.getInstance();
    private final static ICarDAO carDAO = factory.getCarDAO();
    private final static IClientDAO clientDAO = factory.getClientDAO();

    public static void main(String[] args) {

        carDAO.add(new Car("Audi", "A5", 40000));
        carDAO.add(new Car("Porsche", "Panamera", 40000));
        carDAO.add(new Car("Porsche", "Cayman", 40000));

        printAll();

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

        printAll();

        if(carDAO.getCarId(car2).isPresent()) {
            carDAO.removeById(carDAO.getCarId(car2).get());
        }

        printAll();

        carDAO.add(new Car("BMW", "X5", 20000));
        carDAO.add(new Car("BMW", "i4", 30000));
        printAll();

        carDAO.removeByModel("X5");
        printAll();

        carDAO.removeByMark("BMW");
        printAll();

    }

    private static void printAll() {
        printLine();
        for (Car car : carDAO.getAll()) {
            System.out.println(car);
        }
        printLine();
    }

    private static void printLine() {
        System.out.println("--------------------------------------------------------");
    }
}
