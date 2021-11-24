import DAO.*;
import Entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
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

        List<Vet> veterinaries = new ArrayList<>();

        veterinaries.add(new Vet("Vet_1", "111", "111"));
        veterinaries.add(new Vet("Vet_2", "222", "222"));
        veterinaries.add(new Vet("Vet_3", "333", "333"));

        List<AnimalDetails> details = new ArrayList<>();

        details.add(new AnimalDetails(1, true, 1, "1"));
        details.add(new AnimalDetails(2, true, 2, "2"));
        details.add(new AnimalDetails(3, true, 3, "3"));

        List<Aviary> aviaries = new ArrayList<>();

        aviaries.add(new Aviary("cats", 20));
        aviaries.add(new Aviary("dogs", 10));

        List<Animal> animals = new ArrayList<>();

        animals.add(new Animal("Cat_1", details.get(0), veterinaries.get(0), aviaries.get(0)));
        animals.add(new Animal("Cat_2", details.get(1), veterinaries.get(1), aviaries.get(0)));
        animals.add(new Animal("Cat_3", details.get(2), veterinaries.get(2), aviaries.get(0)));

        animals.add(new Animal("Dog_1", details.get(0), veterinaries.get(0), aviaries.get(1)));
        animals.add(new Animal("Dog_2", details.get(1), veterinaries.get(1), aviaries.get(1)));
        animals.add(new Animal("Dog_3", details.get(2), veterinaries.get(2), aviaries.get(1)));

        for (Animal animal: animals) {
            animalDAO.add(animal);
        }

        aviaryDAO.updateById(1, new Aviary("catzzz", 30));
        vetDAO.updateById(1, new Vet("Vasiliy", "+385", "Yo!"));

        aviaryDAO.removeById(2);
        vetDAO.removeById(2);

        printAll(aviaryDAO.getAll());
        printAll(vetDAO.getAll());

        animalDAO.removeById(2);
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

