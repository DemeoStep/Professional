package DAO;

import Entity.Animal;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class AnimalDAO implements AnimalRepository {
    private SessionFactory factory;
    private VetRepository vetDAO;
    private AnimalDetailsRepository animalDetailsDAO;
    private AviaryRepository aviaryDAO;

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

            if(vetDAO.add(animal.getVet()).isEmpty()) {
                vetDAO.getId(animal.getVet()).flatMap(id -> vetDAO.getById(id)).ifPresent(animal::setVet);
            }

            if(aviaryDAO.add(animal.getAviary()).isEmpty()) {
                aviaryDAO.getId(animal.getAviary()).flatMap(id -> aviaryDAO.getById(id)).ifPresent(animal::setAviary);
            }

            animalDetailsId = animalDetailsDAO.add(animal.getDetails());

            session.beginTransaction();
            animalId = Optional.of((Long) session.save(animal));

            session.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if(animalId.isEmpty()) {
                animalDetailsId.ifPresent(id -> animalDetailsDAO.remove(id));
            }
        }

        return animalId;
    }

    @Override
    public List<Animal> getAll() {
        Session session = factory.openSession();

        List<Animal> list = session.createQuery("from Animal", Animal.class).list();

        session.close();

        return list;
    }

    @Override
    public Animal getById(long id) {

        return factory.openSession().get(Animal.class, id);
    }

    @Override
    public void updateById(long id, Animal animal) {

        if (factory.openSession().get(Animal.class, id) != null) {
            Session session = factory.openSession();

            session.beginTransaction();

            animal.setId(id);

            session.update(animal);
            session.flush();
            session.getTransaction().commit();

            session.close();
        }
    }

    @Override
    public void removeById(long id) {
        Animal animal = factory.openSession().get(Animal.class, id);

        if (animal != null) {
            Session session = factory.openSession();
            session.beginTransaction();

            session.delete(animal);
            session.flush();
            session.getTransaction().commit();

            session.close();
        }
    }
}
