package DAO;

import Entity.Animal;
import Connector.Connector;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalDAO implements IAnimalDAO{
    @Override
    public void addAnimal(Animal animal){
        try (Session session = Connector.openSession()) {
            session.save(animal);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Animal> getAnimal(long id) {

        try (Session session = Connector.openSession()) {
            return Optional.of(session.get(Animal.class, id));

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void updateAnimal(long id, Animal animal) {
        try (Session session = Connector.openSession()) {
            session.beginTransaction();
            Animal oldAnimal = session.get(Animal.class, id);

            oldAnimal.copyFrom(animal);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAnimal(long id) {
        try (Session session = Connector.openSession()) {
            session.beginTransaction();
            Animal animal = session.get(Animal.class, id);
            session.delete(animal);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();

        try (Session session = Connector.openSession()) {
            animals = session.createQuery("SELECT a FROM Animal a", Animal.class).list();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return animals;
    }
}
