import DAO.*;
import Entity.Animal;
import Entity.AnimalDetails;
import Entity.Aviary;
import Entity.Vet;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class Main {
    private static SessionFactory factory;
    private static AnimalRepository animalDAO;
    private static AnimalDetailsRepository animalDetailsDAO;
    private static AviaryRepository aviaryDAO;
    private static VetRepository vetDAO;

    Main() {
        factory = new Configuration().configure().buildSessionFactory();
        animalDetailsDAO = new AnimalDetailsDAO(factory);
        aviaryDAO = new AviaryDAO(factory);
        vetDAO = new VetDAO(factory);
        animalDAO = new AnimalDAO(factory, vetDAO, animalDetailsDAO, aviaryDAO);
    }

    public static void main(String[] args) {
        new Main().logic();
        factory.close();
    }

    void logic() {
        Vet vet = new Vet("Vet_1", "111", "111");
        AnimalDetails animalDetails = new AnimalDetails(3, true, 3, "3");
        Aviary aviary = new Aviary("cats", 15);

        Animal animal = new Animal("Cat_3", animalDetails, vet, aviary);
        animalDAO.add(animal);

        printAll(animalDAO.getAll());
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

