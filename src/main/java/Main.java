import Connector.Connector;
import DAO.DAOFactory;
import DAO.IAnimalDAO;
import DAO.IDAOFactory;
import Entity.Animal;

import java.util.*;

public class Main {
    private final static IDAOFactory factory = DAOFactory.getInstance();
    private final static IAnimalDAO animalDAO = factory.getAnimalDAO();

    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("Animal 1", 1, true, 1, new Date()));
        animals.add(new Animal("Animal 2", 2, false, 2, new Date()));
        animals.add(new Animal("Animal 3", 3, true, 3, new Date()));
        animals.add(new Animal("Animal 4", 4, false, 4, new Date()));

        for (Animal animal: animals) {
            animalDAO.addAnimal(animal);
        }

        printAllAnimals();

        printLine();
        animalDAO.getAnimal(4).ifPresent(System.out::println);
        printLine();

        animalDAO.updateAnimal(4, new Animal("Cat", 3, true, 5, new Date()));

        printLine();
        animalDAO.getAnimal(4).ifPresent(System.out::println);
        printLine();

        animalDAO.deleteAnimal(3);

        printAllAnimals();

        Connector.factory.close();
    }

    public static void printAllAnimals() {
        List<Animal> animals = animalDAO.getAllAnimals();
        printLine();
        for (Animal ani : animals) {
            System.out.println(ani);
        }
        printLine();
    }

    public static void printLine(){
        System.out.println("--------------------------------------------------------------");
    }
}
