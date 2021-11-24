package DAO;

import Entity.Animal;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalDAO implements AnimalRepository {
    private final SessionFactory factory;
    private final VetRepository vetDAO;
    private final AnimalDetailsRepository animalDetailsDAO;
    private final AviaryRepository aviaryDAO;

    public AnimalDAO(SessionFactory factory, VetRepository vetDAO, AnimalDetailsRepository animalDetailsDAO, AviaryRepository aviaryDAO) {
        this.factory = factory;
        this.animalDetailsDAO = animalDetailsDAO;
        this.vetDAO = vetDAO;
        this.aviaryDAO = aviaryDAO;
    }

    @Override
    public Optional<Long> add(Animal animal) {
        Optional<Long> animalId = Optional.empty();
        Optional<Long> animalDetailsId = Optional.empty();

        try (Session session = factory.openSession()) {

            if (vetDAO.add(animal.getVet()).isEmpty()) {
                vetDAO.getId(animal.getVet()).flatMap(vetDAO::getById).ifPresent(animal::setVet);
            }

            if (aviaryDAO.add(animal.getAviary()).isEmpty()) {
                aviaryDAO.getId(animal.getAviary()).flatMap(aviaryDAO::getById).ifPresent(animal::setAviary);
            }

            animalDetailsId = animalDetailsDAO.add(animal.getDetails());

            animalId = Optional.of((Long) session.save(animal));

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (animalId.isEmpty()) {
                animalDetailsId.ifPresent(animalDetailsDAO::removeById);
            }
        }

        return animalId;
    }

    @Override
    public List<Animal> getAll() {
        List<Animal> list = new ArrayList<>();

        try (Session session = factory.openSession()) {

            list = session.createQuery("from Animal", Animal.class).list();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Optional<Animal> getById(long id) {
        Optional<Animal> animal = Optional.empty();
        try (Session session = factory.openSession()) {

            animal = session.createQuery("FROM Animal WHERE id = :id", Animal.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return animal;
    }

    @Override
    public void updateById(long id, Animal animal) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            session.createQuery("FROM Animal WHERE id = :id", Animal.class)
                    .setParameter("id", id)
                    .uniqueResultOptional()
                    .ifPresent(value -> value.copyFrom(animal));

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            session.createQuery("FROM Animal WHERE id = :id", Animal.class)
                    .setParameter("id", id)
                    .uniqueResultOptional()
                    .ifPresent(session::delete);

            session.getTransaction().commit();


        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
